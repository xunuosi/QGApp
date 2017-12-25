package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.github.zagum.expandicon.ExpandIconView;
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
import sinolight.cn.qgapp.data.http.entity.EBookEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.holder.TreeChildHolder;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IEBookActivityView;


/**
 * Created by xns on 2017/6/29.
 * EBookActivity Presenter
 */

public class EBookActivityPresenter extends BasePresenter<IEBookActivityView, HttpManager> {
    private static final String TAG = "EBookActivityPresenter";

    private Context mContext;
    private AppContants.EBook.SortType sortType = AppContants.EBook.SortType.SORT_COMPREHENSIVE;
    private AppContants.EBook.SortOrder sortOrder = AppContants.EBook.SortOrder.SORT_REVERSE;

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
    private boolean action_search = false;

    private List<KDBResData> mDatas = new ArrayList<>();
    private List<EBookEntity> eBookDatas;
    private KDBResAdapter mAdapter;


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

    private HttpSubscriber<PageEntity<List<EBookEntity>>> mEBookObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<EBookEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<EBookEntity>> PageEntity) {
                    if (PageEntity != null) {
                        count = PageEntity.getCount();
                        eBookDatas = PageEntity.getData();
                        transformKDBResData();
                    } else {
                        view().showRefreshing(false);
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mEBookObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError(code, errorMsg);
                }
            });


    /**
     * clear data display empty
     */
    private void clearData() {
        if (mAdapter != null) {
            mAdapter.setData(mDatas);
        }
    }

    private void showError(int code, String errorMsg) {
        view().showRefreshing(false);
        if (action_search) {
            showErrorToast(R.string.attention_data_is_empty);
        } else {
            showErrorToast(R.string.attention_data_refresh_error);
        }
        // if data is not obtained,you need clear data show empty.
        clearData();
    }

    private void transformKDBResData() {
        List<KDBResData> list = new ArrayList<>();
        list = KDBResDataMapper.transformEBookDatas(eBookDatas, KDBResAdapter.TYPE_EBOOK, false);

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
            mAdapter = new KDBResAdapter(mContext, mDatas);
            view().showListView(mAdapter);
        } else {
            mAdapter.setData(mDatas);
        }
        if (action_more) {
            view().showLoadMoreing(false);
        } else {
            closeRefreshing();
        }

    }

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
//        TreeNode treeNode = mRoot.get(0);
//        mTreeNodes = treeNode.getChildren();
        mTreeNodes = mRoot;
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

    public EBookActivityPresenter(IEBookActivityView view, Context context) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        if (mDBResTypeObserver != null) {
            mDBResTypeObserver.unSubscribe();
        }
        if (mEBookObserver != null) {
            mEBookObserver.unSubscribe();
        }

        KDBResDataMapper.reset();
        unbindView();
    }


    /**
     * 带参数的请求数据方法
     *
     * @param key
     * @param themeType
     */
    public void loadDataWithPara(@Nullable String key, @Nullable String themeType, boolean isMore, boolean isSearch) {
        action_more = isMore;
        action_search = isSearch;
        // If not load more data, need clear data.
        if (!isMore) {
            mDatas.clear();
        }
        // 请求资源数据
        model.getEBookListNoCache(
                mEBookObserver,
                AppHelper.getInstance().getCurrentToken(),
                key,
                sortType.getType(),
                sortOrder.getType(),
                themeType,
                page,
                SIZE
        );
    }

    public TreeNode popTreeMenu() {
        if (mTreeNodes != null && mTreeNodes.size() != 0) {
            root.addChildren(mTreeNodes);
            return root;
        } else {
            return null;
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
        resetState();

        getTypeData();
        getData();
    }

    /**
     * 获取分类数据
     */
    private void getTypeData() {
        // 获取全部分类数据
        model.getKDBResTypeNoCache(
                mDBResTypeObserver,
                AppHelper.getInstance().getCurrentToken(),
                null);
    }

    private void getData() {
        // 请求资源数据
        model.getEBookListNoCache(
                mEBookObserver,
                AppHelper.getInstance().getCurrentToken(),
                null,
                sortType.getType(),
                sortOrder.getType(),
                null,
                page,
                SIZE
        );
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
        // Setting LoadMore is true
        view().hasMoreData(true);
    }

    public void setDataType(AppContants.EBook.SortType type, boolean isChangeSort) {
        sortType = type;
        if (isChangeSort) {
            if (AppContants.EBook.SortOrder.SORT_POSITIVE.getType().equals(sortOrder.getType())) {
                sortOrder = AppContants.EBook.SortOrder.SORT_REVERSE;
                view().changeSortView(getActionTabItemPosi(type), ExpandIconView.MORE);
            } else {
                sortOrder = AppContants.EBook.SortOrder.SORT_POSITIVE;
                view().changeSortView(getActionTabItemPosi(type), ExpandIconView.LESS);
            }
        } else {
            sortOrder = AppContants.EBook.SortOrder.SORT_REVERSE;
            view().changeSortView(getActionTabItemPosi(type), ExpandIconView.MORE);
        }
        // Update Data
        this.loadDataWithPara(null, null, false, false);
    }

    private int getActionTabItemPosi(AppContants.EBook.SortType type) {
        int position = 0;
        switch (type) {
            case SORT_COMPREHENSIVE:
                position = 0;
                break;
            case SORT_NEWGOODS:
                position = 1;
                break;
            case SORT_PRICE:
                position = 2;
                break;
        }
        return position;
    }
}
