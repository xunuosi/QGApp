package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResTypeEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.entity.ResArticleEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.holder.TreeChildHolder;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IDBResArticleFragmentView;

/**
 * Created by xns on 2017/7/8.
 * 资源库文章标签界面的Presenter
 */

public class DBResArticlePresenter extends BasePresenter<IDBResArticleFragmentView, HttpManager>{
    private static final String TAG = "DBResArticlePresenter";
    private static final int TYPE_ARTICLE = 0;
    private Context mContext;

    private List<DBResTypeEntity> mTreeTypeList;
    private List<TreeNode> mTreeNodes;
    private List<TreeNode> mRoot;
    private List<TreeNode> mTrees;
    private final TreeNode root = TreeNode.root();

    // 获取资源列表
    private int page = 1;
    private static final int SIZE = 10;
    // 获取当前资源列表的总数
    private int count = 0;
    // 判断当前操作是否为加载更多数据
    private boolean action_more = false;

    private List<KDBResData> mDatas = new ArrayList<>();
    private List<ResArticleEntity> articleDatas;
    private KDBResAdapter mAdapter;

    private HttpSubscriber<PageEntity<List<ResArticleEntity>>> mArticleObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<ResArticleEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<ResArticleEntity>> PageEntity) {
                    if (PageEntity != null) {
                        count = PageEntity.getCount();
                        articleDatas = PageEntity.getData();
                        transformKDBResData();
                    } else {
                        view().showRefreshing(false);
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mArticleObserver code:" + code + ",errorMsg:" + errorMsg);
                    showErrorToast(R.string.attention_data_refresh_error);
                    view().showRefreshing(false);
                }
            });

    private void transformKDBResData() {
        List<KDBResData> list = new ArrayList<>();
        list = KDBResDataMapper.transformArticleDatas(articleDatas, 0, false);
        // Load More Action
        if (action_more) {
            mDatas.addAll(list);
        } else {// Refresh Action
            mDatas = list;
        }

        showSuccess();
    }

    private void showSuccess() {
        if (mAdapter == null) {
            mAdapter = new KDBResAdapter(mContext, mDatas);
        } else {
            mAdapter.setData(mDatas);
        }
        view().init2Show(mAdapter);

        if (action_more) {
            view().showLoadMoreing(false);
        } else {
            view().showRefreshing(false);
        }
    }



    private HttpSubscriber<List<DBResTypeEntity>> mDBResTypeObserver = new HttpSubscriber<>(
            new OnResultCallBack<List<DBResTypeEntity>>() {

                @Override
                public void onSuccess(List<DBResTypeEntity> dbResTypeEntities) {
                    mTreeTypeList = dbResTypeEntities;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            disposeData();
                        }
                    }).start();
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mDBResTypeObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
                }
            });

    /**
     * 处理数据的方法用于TreeMenu的建立
     */
    private void disposeData() {
        mTrees = new ArrayList<>();
        for (DBResTypeEntity bean : mTreeTypeList) {
            if (bean.getPid().equals(AppContants.DataBase.TREE_PID)) {
                TreeNode p = new TreeNode(new TreeParentHolder.IconTreeItem(
                        bean.getId(),
                        bean.getPid(),
                        bean.getName(),
                        bean.isHaschild())).setViewHolder(new TreeParentHolder(mContext));
                mTrees.add(p);
            } else {
                TreeNode c = new TreeNode(new TreeParentHolder.IconTreeItem(
                        bean.getId(),
                        bean.getPid(),
                        bean.getName(),
                        bean.isHaschild())).setViewHolder(new TreeChildHolder(mContext));
                mTrees.add(c);
            }
        }

        mRoot = createTree(AppContants.DataBase.TREE_PID, mTrees);
        TreeNode treeNode = mRoot.get(0);
        mTreeNodes = treeNode.getChildren();

    }

    public List<TreeNode> createTree(String pid, List<TreeNode> treeRes) {
        List<TreeNode> temp = new ArrayList<>();
        for (TreeNode treeRe : treeRes) {
            if (pid.equals(getPid(treeRe))) {
                List<TreeNode> child = this.createTree(getId(treeRe), treeRes);
                treeRe.addChildren(child);
                temp.add(treeRe);
            }
        }
        return temp;
    }

    private String getPid(TreeNode node) {
        TreeParentHolder.IconTreeItem value = (TreeParentHolder.IconTreeItem) node.getValue();
        return value.pid;
    }

    private String getId(TreeNode node) {
        TreeParentHolder.IconTreeItem value = (TreeParentHolder.IconTreeItem) node.getValue();
        return value.id;
    }

    /**
     * Return treeMenu root
     * @return
     */
    public TreeNode popTreeMenu() {
        if (mTreeNodes != null && mTreeNodes.size() != 0) {
            root.addChildren(mTreeNodes);
            return root;
        } else {
            showErrorToast(R.string.attention_data_refresh_error);
            return null;
        }
    }

    private void showError() {
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showErrorToast(int msgId) {
        view().showErrorToast(msgId);
    }

    public DBResArticlePresenter(Context context) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        mDBResTypeObserver.unSubscribe();
        unbindView();
    }

    public void init2Show() {
        if (mTreeNodes == null) {
            // Create treeMenu
            model.getKDBResTypeWithCache(
                    mDBResTypeObserver,
                    AppHelper.getInstance().getCurrentToken(),
                    null,
                    false
            );
        }

        initArticleData();
    }

    private void initArticleData() {
        mDatas.clear();
        model.getKDBIndustryAnalysisListNoCache(
                mArticleObserver,
                AppHelper.getInstance().getCurrentToken(),
                null,
                null,
                null,
                TYPE_ARTICLE,
                page,
                SIZE
        );
    }

    public void refreshView() {
        // 恢复页初始值
        page = 1;
        // Action is refresh data
        action_more = false;
        // Setting LoadMore is true
        view().hasMoreData(true);
        initArticleData();
    }

    /**
     * 带参数的请求数据方法
     * @param key
     * @param themeType
     * @param isMore:是否为加载更多的操作
     */
    public void loadDataWithPara(@Nullable String key, @Nullable String themeType, boolean isMore) {
        action_more = isMore;
        model.getKDBIndustryAnalysisListNoCache(
                mArticleObserver,
                AppHelper.getInstance().getCurrentToken(),
                null,
                themeType,
                key,
                TYPE_ARTICLE,
                page,
                SIZE
        );
    }

    public void loadMore(String key, String themeType) {
        if (mDatas!= null && mDatas.size() < count) {
            // 有更多数据可以加载
            page++;
            // Action load more data
            loadDataWithPara(key, themeType, true);
        } else if (mDatas != null && mDatas.size() >= count) {
            // 无更多数据加载
            view().hasMoreData(false);
        } else {
            view().hasMoreData(false);
        }
    }
}
