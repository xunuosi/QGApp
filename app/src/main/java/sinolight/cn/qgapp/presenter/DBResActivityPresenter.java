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
import sinolight.cn.qgapp.adapter.HomeAdapter;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.BookEntity;
import sinolight.cn.qgapp.data.http.entity.DBResTypeEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.entity.ResArticleEntity;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.data.http.entity.ResStandardEntity;
import sinolight.cn.qgapp.data.http.entity.ResWordEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.holder.TreeChildHolder;
import sinolight.cn.qgapp.views.view.IDBResActivityView;

import static sinolight.cn.qgapp.AppContants.DataBase.Res.RES_DIC;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity Presenter
 */

public class DBResActivityPresenter extends BasePresenter<IDBResActivityView, HttpManager> {
    private static final String TAG = "DBResActivityPresenter";
    private static final int TYPE_ARTICLE = 0;
    private static final int TYPE_INDUSTRY = 1;
    private static final int TYPE_RECO_DIC = 0;
    private static final int TYPE_ALL_DIC = 1;

    private int dicType = TYPE_RECO_DIC;
    private Context mContext;
    private String dbType;
    private String dbId;
    private AppContants.DataBase.Res resType;
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

    private List<KDBResData> mDatas;
    private List<BookEntity> bookDatas;
    private List<ResStandardEntity> standDatas;
    private List<ResArticleEntity> articleDatas;
    private List<ResImgEntity> imgDatas;
    private List<ResWordEntity> wordDatas;
    private KDBResAdapter mAdapter;


    private HttpSubscriber<List<DBResTypeEntity>> mDBResTypeObserver = new HttpSubscriber<>(
            new OnResultCallBack<List<DBResTypeEntity>>() {

        @Override
        public void onSuccess(List<DBResTypeEntity> dbResTypeEntities) {
            mTreeTypeList = dbResTypeEntities;
            disposeData();

        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "mDBResTypeObserver code:" + code + ",errorMsg:" + errorMsg);
            showErrorToast(R.string.attention_data_refresh_error);
            view().showRefreshing(false);
        }
    });

    private HttpSubscriber<PageEntity<List<BookEntity>>> mBookObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<BookEntity>>>() {

        @Override
        public void onSuccess(PageEntity<List<BookEntity>> PageEntity) {
            if (PageEntity != null) {
                count = PageEntity.getCount();
                bookDatas = PageEntity.getData();
                transformKDBResData(AppContants.DataBase.Res.RES_BOOK);
            } else {
                view().showRefreshing(false);
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "mBookObserver code:" + code + ",errorMsg:" + errorMsg);
            showErrorToast(R.string.attention_data_refresh_error);
            view().showRefreshing(false);
        }
    });

    private HttpSubscriber<PageEntity<List<ResStandardEntity>>> mStandObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<ResStandardEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<ResStandardEntity>> PageEntity) {
                    if (PageEntity != null) {
                        count = PageEntity.getCount();
                        standDatas = PageEntity.getData();
                        transformKDBResData(AppContants.DataBase.Res.RES_IMG);
                    } else {
                        view().showRefreshing(false);
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mStandObserver code:" + code + ",errorMsg:" + errorMsg);
                    showErrorToast(R.string.attention_data_refresh_error);
                    view().showRefreshing(false);
                }
            });

    private HttpSubscriber<PageEntity<List<ResArticleEntity>>> mArticleObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<ResArticleEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<ResArticleEntity>> PageEntity) {
                    if (PageEntity != null) {
                        count = PageEntity.getCount();
                        articleDatas = PageEntity.getData();
                        transformKDBResData(AppContants.DataBase.Res.RES_ARTICLE);
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

    private HttpSubscriber<PageEntity<List<ResImgEntity>>> mImgObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<ResImgEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<ResImgEntity>> PageEntity) {
                    if (PageEntity != null) {
                        count = PageEntity.getCount();
                        imgDatas = PageEntity.getData();
                        transformKDBResData(AppContants.DataBase.Res.RES_ARTICLE);
                    } else {
                        view().showRefreshing(false);
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mImgObserver code:" + code + ",errorMsg:" + errorMsg);
                    showErrorToast(R.string.attention_data_refresh_error);
                    view().showRefreshing(false);
                }
            });

    private HttpSubscriber<PageEntity<List<ResWordEntity>>> mWordObserver = new HttpSubscriber<>(
            new OnResultCallBack<PageEntity<List<ResWordEntity>>>() {

                @Override
                public void onSuccess(PageEntity<List<ResWordEntity>> PageEntity) {
                    if (PageEntity != null) {
                        count = PageEntity.getCount();
                        wordDatas = PageEntity.getData();
                        transformKDBResData(RES_DIC);
                    } else {
                        view().showRefreshing(false);
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mWordObserver code:" + code + ",errorMsg:" + errorMsg);
                    showErrorToast(R.string.attention_data_refresh_error);
                    view().showRefreshing(false);
                    // if data is not obtained,you need clear data show empty.
                    clearData();
                }
            });

    /**
     * clear data display empty
     */
    private void clearData() {
        if (mAdapter != null) {
            mAdapter.setData(new ArrayList<KDBResData>());
        }
        if (resType.equals(RES_DIC)) {
            count = 0;
            view().showFooterView(true, String.valueOf(count));
        }
    }

    private void transformKDBResData(AppContants.DataBase.Res resType) {
        List<KDBResData> list = new ArrayList<>();
        switch (resType) {
            case RES_BOOK:
                list = KDBResDataMapper.transformBookDatas(bookDatas, KDBResAdapter.TYPE_BOOK, false);
                break;
            case RES_STANDARD:
                list = KDBResDataMapper.transformStandDatas(standDatas, KDBResAdapter.TYPE_STANDARD, false);
                break;
            case RES_ARTICLE:
                list = KDBResDataMapper.transformArticleDatas(articleDatas, 0, false);
                break;
            case RES_IMG:
                list = KDBResDataMapper.transformImgDatas(imgDatas, KDBResAdapter.TYPE_IMG, false);
                break;
            case RES_DIC:
                list = KDBResDataMapper.transformDicDatas(wordDatas, KDBResAdapter.TYPE_WORD, false);
                view().showFooterView(true, String.valueOf(count));
                break;
            case RES_INDUSTRY:
                list = KDBResDataMapper.transformArticleDatas(articleDatas, 0, false);
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
        TreeNode treeNode = mRoot.get(0);
        mTreeNodes = treeNode.getChildren();

        closeRefreshing();
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

    public DBResActivityPresenter(IDBResActivityView view, Context context) {
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
        if (mBookObserver != null) {
            mBookObserver.unSubscribe();
        }
        if (mStandObserver != null) {
            mStandObserver.unSubscribe();
        }
        if (mArticleObserver != null) {
            mArticleObserver.unSubscribe();
        }
        if (mImgObserver != null) {
            mImgObserver.unSubscribe();
        }
        if (mWordObserver != null) {
            mWordObserver.unSubscribe();
        }
        unbindView();
    }

    public void show(Intent intent) {
        if (intent != null) {
            resType = (AppContants.DataBase.Res) intent.getSerializableExtra(AppContants.DataBase.KEY_RES_TYPE);
            dbType = intent.getStringExtra(AppContants.DataBase.KEY_TYPE);
            dbId = intent.getStringExtra(AppContants.DataBase.KEY_ID);
            initData2Show();
        }
    }

    private void initData2Show() {
        switch (resType) {
            case RES_BOOK:
                view().initShow(mContext.getString(R.string.text_book));
                // 请求资源数据
                model.getKDBBookListNoCache(
                        mBookObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        null,
                        null,
                        page,
                        SIZE
                );
                break;
            case RES_STANDARD:
                view().initShow(mContext.getString(R.string.text_standard));
                // 请求资源数据
                model.getKDBStdListNoCache(
                        mStandObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        null,
                        null,
                        page,
                        SIZE
                );
                break;
            case RES_ARTICLE:
                view().initShow(mContext.getString(R.string.text_article));
                model.getKDBIndustryAnalysisListNoCache(
                        mArticleObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        null,
                        null,
                        TYPE_ARTICLE,
                        page,
                        SIZE
                );
                break;
            case RES_IMG:
                view().initShow(mContext.getString(R.string.text_img));
                model.getKDBdoPicListNoCache(
                        mImgObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        null,
                        null,
                        page,
                        SIZE
                );
                break;
            case RES_DIC:
                view().initShow(mContext.getString(R.string.text_dictionary));
                view().showTab(true);
                view().showFooterView(true, String.valueOf(count));
                model.getKDBWordListNoCache(
                        mWordObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        dicType,
                        null,
                        page,
                        SIZE
                );
                break;
            case RES_INDUSTRY:
                view().initShow(mContext.getString(R.string.text_analysis));
                model.getKDBIndustryAnalysisListNoCache(
                        mArticleObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        null,
                        null,
                        TYPE_INDUSTRY,
                        page,
                        SIZE
                );
                break;
        }
        // 请求分类数据
        model.getKDBResTypeWithCache(
                mDBResTypeObserver,
                AppHelper.getInstance().getCurrentToken(),
                dbType,
                false);

    }

    /**
     * 带参数的请求数据方法
     * @param key
     * @param themeType
     */
    public void loadDataWithPara(@Nullable String key,@Nullable String themeType,boolean isMore) {
        action_more = isMore;
        switch (resType) {
            case RES_BOOK:
                // 请求资源数据
                model.getKDBBookListNoCache(
                        mBookObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        themeType,
                        key,
                        page,
                        SIZE
                );
                break;
            case RES_STANDARD:
                // 请求资源数据
                model.getKDBStdListNoCache(
                        mStandObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        themeType,
                        key,
                        page,
                        SIZE
                );
                break;
            case RES_ARTICLE:
                model.getKDBIndustryAnalysisListNoCache(
                        mArticleObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        themeType,
                        key,
                        TYPE_ARTICLE,
                        page,
                        SIZE
                );
                break;
            case RES_IMG:
                // 请求资源数据
                model.getKDBdoPicListNoCache(
                        mImgObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        themeType,
                        key,
                        page,
                        SIZE
                );
                break;
            case RES_DIC:
                // 请求资源数据
                model.getKDBWordListNoCache(
                        mWordObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        dicType,
                        key,
                        page,
                        SIZE
                );
                break;
            case RES_INDUSTRY:
                model.getKDBIndustryAnalysisListNoCache(
                        mArticleObserver,
                        AppHelper.getInstance().getCurrentToken(),
                        dbId,
                        themeType,
                        key,
                        TYPE_INDUSTRY,
                        page,
                        SIZE
                );
                break;
        }

    }

    public void popTreeMenu() {
        // if Res dictionary don`t PopMenu
        if (resType.equals(RES_DIC)) {
            return;
        }
        if (mTreeNodes != null && mTreeNodes.size() != 0) {
            root.addChildren(mTreeNodes);
            view().popTreeMenu(root);
        } else {
            showErrorToast(R.string.attention_data_refresh_error);
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
     * @return
     */
    private boolean checkData() {
        return (mTreeNodes != null &&
                mDatas !=null
        );
    }

    public void refreshView() {
        // 恢复页初始值
        page = 1;
        // Action is refresh data
        action_more = false;
        // Setting LoadMore is true
        view().hasMoreData(true);
        initData2Show();
    }

    public void loadMore(@Nullable String key,@Nullable String themeType) {
        if (mDatas!=null && mDatas.size() < count) {
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

    /**
     * Word Res tab change
     *
     * @param position
     */
    public void tabWordShow(int position, @Nullable String key) {
        switch (position) {
            case 0:
                dicType = TYPE_RECO_DIC;
                loadDataWithPara(key, null, false);
                break;
            case 1:
                dicType = TYPE_ALL_DIC;
                loadDataWithPara(key, null, false);
                break;
        }

    }
}
