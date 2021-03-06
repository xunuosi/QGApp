package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.adapter.VideoAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResTypeEntity;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.entity.ResWordEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.holder.TreeChildHolder;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IMaterialListActivityView;
import sinolight.cn.qgapp.views.view.IVideoListActivityView;

/**
 * Created by xns on 2017/6/29.
 * MaterialList Presenter
 */

public class MaterialListActivityPresenter extends BasePresenter<IMaterialListActivityView, HttpManager> {
    private static final String TAG = "MaterialListActivityPresenter";
    private static final int TYPE_COOk = 2;
    private Context mContext;
    // TreeMenu
    private List<DBResTypeEntity> mTreeTypeList;
    private List<TreeNode> mTreeNodes;
    private List<TreeNode> mRoot;
    private List<TreeNode> mTrees;
    private final TreeNode root = TreeNode.root();

    private AppContants.DataBase.Res resType = AppContants.DataBase.Res.RES_COOK;
    private AppContants.DataBase.Type dbType = AppContants.DataBase.Type.DB_COOK;
    // 获取资源列表
    private int page = 1;
    private static final int SIZE = 10;
    // 获取当前资源列表的总数
    private int count = 0;
    // 判断当前操作是否为加载更多数据
    private boolean action_more = false;
    private boolean action_search = false;
    // VideoParentID
    private String videoSetID;

    private List<KDBResData> mDatas = new ArrayList<>();
    private List<ResWordEntity> materialDatas;
    private CommonTitleAdapter mAdapter;

    private HttpSubscriber<List<DBResTypeEntity>> mDBResTypeObserver = new HttpSubscriber<>(
            new OnResultCallBack<List<DBResTypeEntity>>() {

                @Override
                public void onSuccess(List<DBResTypeEntity> dbResTypeEntities) {
                    mTreeTypeList = dbResTypeEntities;
                    // Create new thread to load TreeMenu
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
                    showError(code, errorMsg);
                }
            });

    private HttpSubscriber<PageEntity<List<ResWordEntity>>> mMaterialObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<ResWordEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<ResWordEntity>> pageEntity) {
                    if (pageEntity != null) {
                        count = pageEntity.getCount();
                        materialDatas = pageEntity.getData();
                        transformKDBResData(AppContants.DataBase.Res.RES_COOK);
                    } else {
                        showError(0, null);
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mVideoObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError(code, errorMsg);
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
     * clear data display empty
     */
    private void clearData() {
        if (mAdapter != null) {
            mAdapter.setData(mDatas);
        }
    }

    private void showError(int code, String msg) {
        view().showRefreshing(false);
        if (action_search) {
            showErrorToast(R.string.attention_data_is_empty);
        } else {
            showErrorToast(R.string.attention_data_refresh_error);
        }
        // if data is not obtained,you need clear data show empty.
        clearData();
    }

    private void transformKDBResData(AppContants.DataBase.Res resType) {
        List<KDBResData> list = new ArrayList<>();
        switch (resType) {
            case RES_COOK:
                list = KDBResDataMapper.transformResWord2Material(materialDatas, CommonTitleAdapter.TYPE_MATERIAL, false);
                break;
        }
        // Load More Action
        if (action_more) {
            mDatas.addAll(list);
        } else {// Refresh Action
            mDatas = list;
        }

        showWithData();
    }

    private void showWithData() {
        if (mAdapter == null) {
            mAdapter = new CommonTitleAdapter(mContext, mDatas);
        } else {
            mAdapter.setData(mDatas);
        }
        view().showListView(mAdapter);
        if (action_more) {
            view().showLoadMoreing(false);
        } else {
            closeRefreshing();
        }

    }

    public MaterialListActivityPresenter(IMaterialListActivityView view, Context context) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        mDBResTypeObserver.unSubscribe();
        mMaterialObserver.unSubscribe();

        KDBResDataMapper.reset();
        unbindView();
    }

    /**
     * 带参数的请求数据方法
     *
     * @param key
     */
    public void loadDataWithPara(@Nullable String key, @Nullable String themeType, boolean isMore, boolean isSearch) {
        action_more = isMore;
        action_search = isSearch;
        // If not load more data, need clear data.
        if (!isMore) {
            mDatas.clear();
        }
        switch (this.resType) {
            case RES_COOK:
                // 请求资源数据
                model.getKDBWordListNoCache(
                        mMaterialObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        null,
                        key,
                        TYPE_COOk,
                        themeType,
                        page,
                        SIZE
                );
                break;
        }

    }


    private void closeRefreshing() {
        if (checkData()) {
            view().showRefreshing(false);
        }
    }

    private void showErrorToast(int resId) {
        view().showToast(resId);
        view().showRefreshing(false);
    }

    /**
     * 验证是否获取完数据
     *
     * @return
     */
    private boolean checkData() {
        return mDatas != null;
    }

    public void refreshView() {
        init2Show();
    }

    public void loadMore(@Nullable String key, @Nullable String themeType) {
        if (mDatas != null && mDatas.size() < count) {
            // 有更多数据可以加载
            page++;
            // Action load more data
            loadDataWithPara(key, themeType, true, false);
        } else if (mDatas != null && mDatas.size() >= count) {
            // 无更多数据加载
            view().hasMoreData(false);
        } else {
            view().hasMoreData(false);
        }
    }

    public void init2Show() {
        resetState();
        // load video set data
        getData();
    }

    private void getData() {
        model.getKDBWordListNoCache(
                mMaterialObserver,
                AppHelper.getInstance().getCurrentToken(),
                null,
                null,
                TYPE_COOk,
                null,
                page,
                SIZE
        );
    }

    public void searchData(String key, String themeType) {
        resetState();
        this.action_search = true;
        this.loadDataWithPara(key, themeType, false, true);
    }

    /**
     * 重置状态
     */
    @Override
    protected void resetState() {
        super.resetState();
        page = 1;
        action_more = false;
        action_search = false;
        mDatas.clear();
        count = 0;
    }

    /**
     * Load TreeMenu data
     */
    public void loadTreeMenu() {
        model.getKDBResTypeNoCache(
                mDBResTypeObserver,
                AppHelper.getInstance().getCurrentToken(),
                dbType.getType());
    }

    public TreeNode popTreeMenu() {
        if (mTreeNodes != null && mTreeNodes.size() != 0) {
            root.addChildren(mTreeNodes);
            return root;
        } else {
            return null;
        }
    }
}
