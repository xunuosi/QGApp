package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.HomeAdapter;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.data.bean.LocalDataBean;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.ArticleEntity;
import sinolight.cn.qgapp.data.http.entity.BannerEntity;
import sinolight.cn.qgapp.data.http.entity.NewBookEntity;
import sinolight.cn.qgapp.data.http.entity.RecommendEntity;
import sinolight.cn.qgapp.data.http.entity.StandardEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.HomeDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.SearchActivity;
import sinolight.cn.qgapp.views.fragment.HomeFragment;
import sinolight.cn.qgapp.views.view.IHomeFragmentView;

/**
 * Created by xns on 2017/7/5.
 * HomeFragment Presenter
 */

public class HomeFragmentPresenter extends BasePresenter<IHomeFragmentView, HttpManager>
        implements UMShareListener {
    private static final String TAG = "HomeFragmentPresenter";
    private static final int TYPE_STANDARD = 0;
    private static final int TYPE_NEW_BOOKS = 1;
    private static final int TYPE_HOT_ARTICLE = 2;

    private Context mContext;
    private List<HomeData> homeDatas;
    private HomeAdapter mHomeAdapter;

    private int[] storeImgArr = {
            R.drawable.store_icon_knowledge,
            R.drawable.store_icon_res,
            R.drawable.store_icon_bk,
            R.drawable.store_icon_standard,
            R.drawable.store_icon_ebk,
            R.drawable.store_icon_master
    };

    private AppContants.HomeStore.Type[] homeStoreArr = {
            AppContants.HomeStore.Type.TYPE_DB_KNOWLEDGE,
            AppContants.HomeStore.Type.TYPE_DB_RES,
            AppContants.HomeStore.Type.TYPE_DB_BAIKE,
            AppContants.HomeStore.Type.TYPE_DB_STANDARD,
            AppContants.HomeStore.Type.TYPE_EBOOK,
            AppContants.HomeStore.Type.TYPE_MASTER
    };

    private int[] storeStrArr = {
            R.string.text_knowledge_store,
            R.string.text_resource_store,
            R.string.text_baike_store,
            R.string.text_standard_store,
            R.string.text_eBook_store,
            R.string.text_master_store
    };

    private int[] titleImgArr = {
            R.drawable.title_icon_standard,
            R.drawable.title_icon_newbooks,
            R.drawable.title_icon_hot_article
    };

    private int[] titleStrArr = {
            R.string.text_new_standard,
            R.string.text_newbook_coming,
            R.string.text_hot_article
    };

    private List<BannerEntity> mHomeBannerDatas;
    private List<BannerEntity> mHotPicsDatas;
    private List<RecommendEntity> mRecoDatas;
    private List<NewBookEntity> mNewBooks;
    private List<ArticleEntity> mArticles;
    private List<StandardEntity> mStandardDatas;
    private List<LocalDataBean> mStoreDatas;
    private List<LocalDataBean> mTitleDatas;
    // 访问多个接口的标志位
    private boolean mHomeBannerFlag;
    private boolean mHotPicsFlag;
    private boolean mStandardFlag;
    private boolean mRecoFlag;
    private boolean mNewBooksFlag;
    private boolean mArticlesFlag;

    private HttpSubscriber homeBannerObserver = new HttpSubscriber(new OnResultCallBack<List<BannerEntity>>() {

        @Override
        public void onSuccess(List<BannerEntity> bannerEntities) {
            mHomeBannerDatas = bannerEntities;
            mHomeBannerFlag = true;
            createHomeListView();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "homeBannerObserver code:" + code + ",errorMsg:" + errorMsg);
            mHomeBannerFlag = true;
            createHomeListView();
            showError(code, errorMsg);
        }
    });

    private HttpSubscriber hotPicsObserver = new HttpSubscriber(new OnResultCallBack<List<BannerEntity>>() {

        @Override
        public void onSuccess(List<BannerEntity> bannerEntities) {
            mHotPicsDatas = bannerEntities;
            mHotPicsFlag = true;
            createHomeListView();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "hotPicsObserver code:" + code + ",errorMsg:" + errorMsg);
            mHotPicsFlag = true;
            createHomeListView();
            showError(code, errorMsg);
        }
    });

    private HttpSubscriber standardObserver = new HttpSubscriber(new OnResultCallBack<List<StandardEntity>>() {

        @Override
        public void onSuccess(List<StandardEntity> bannerEntities) {
            mStandardDatas = bannerEntities;
            mStandardFlag = true;
            createHomeListView();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "standardObserver code:" + code + ",errorMsg:" + errorMsg);
            mStandardFlag = true;
            createHomeListView();
            showError(code, errorMsg);
        }
    });

    private HttpSubscriber recWordsObserver = new HttpSubscriber(new OnResultCallBack<List<RecommendEntity>>() {

        @Override
        public void onSuccess(List<RecommendEntity> bannerEntities) {
            mRecoDatas = bannerEntities;
            mRecoFlag = true;
            createHomeListView();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "recWordsObserver code:" + code + ",errorMsg:" + errorMsg);
            mRecoFlag = true;
            createHomeListView();
            showError(code, errorMsg);
        }
    });

    private HttpSubscriber newBooksObserver = new HttpSubscriber(new OnResultCallBack<List<NewBookEntity>>() {

        @Override
        public void onSuccess(List<NewBookEntity> bannerEntities) {
            mNewBooks = bannerEntities;
            mNewBooksFlag = true;
            createHomeListView();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "newBooksObserver code:" + code + ",errorMsg:" + errorMsg);
            mNewBooksFlag = true;
            createHomeListView();
            showError(code, errorMsg);
        }
    });

    private HttpSubscriber articleObserver = new HttpSubscriber(new OnResultCallBack<List<ArticleEntity>>() {

        @Override
        public void onSuccess(List<ArticleEntity> bannerEntities) {
            mArticles = bannerEntities;
            mArticlesFlag = true;
            createHomeListView();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "articleObserver code:" + code + ",errorMsg:" + errorMsg);
            mArticlesFlag = true;
            createHomeListView();
            showError(code, errorMsg);
        }
    });

    private void showError(int code, String errorMsg) {
        if (code != AppContants.SUCCESS_CODE) {
            view().showMsgByStr(errorMsg);
        }
    }

    /**
     * 加载Item分类标题的方法
     * @param titleType
     * @param adapterType
     * @param span
     */
    private void loadTitle(int titleType, int adapterType, boolean span) {
        switch (titleType) {
            case TYPE_STANDARD:
                insertHomeData(mTitleDatas.get(TYPE_STANDARD), adapterType, span);
                insertStandard();
                break;
            case TYPE_NEW_BOOKS:
                insertHomeData(mTitleDatas.get(TYPE_NEW_BOOKS), adapterType, span);
                insertNewBooks();
                break;
            case TYPE_HOT_ARTICLE:
                insertHomeData(mTitleDatas.get(TYPE_HOT_ARTICLE), adapterType, span);
                insertArticles();
                break;
        }
    }

    /**
     * 获取热门图集
     */
    private void loadHotPicsData() {
        model.getHotPicsWithCache(
                hotPicsObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
    }

    /**
     * 获取热门文章
     */
    private void loadArticleData() {
        model.getNewsArticleWithCache(
                articleObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
    }

    /**
     * 获取新书数据
     */
    private void loadNewBooksData() {
        model.getNewsBooksWithCache(
                newBooksObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
    }

    private void loadStandardData() {
        model.getNewestStdDataWithCache(
                standardObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
    }

    /**
     * 获取推荐词条
     */
    private void loadRecoWordsData() {
        model.getRecommendWordsWithCache(
                recWordsObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
    }

    /**
     * 转换单个HomeData的方法
     * @param bean
     * @param type
     * @param span
     */
    private void insertHomeData(LocalDataBean bean, int type, boolean span) {
        homeDatas.add(HomeDataMapper.transformLocalData(bean, type, span));
    }

    @Inject
    public HomeFragmentPresenter(Context context) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
    }

    /**
     * 创建首页列表View的方法
     */
    private void createHomeListView() {
        if (checkData()) {
            // Load Home Banner
            insertHomeBanner();
        }
    }

    private void insertHomeBanner() {
        if (mHomeBannerDatas == null) {
            mHomeBannerDatas = new ArrayList<>();
        }
        transformHomeData(mHomeBannerDatas, HomeAdapter.TYPE_BANNER, true);

        insertStore();
    }

    private void insertStore() {
        transformHomeData(mStoreDatas, HomeAdapter.TYPE_STORE, false);

        insertHotPics();
    }

    private void insertHotPics() {
        if (mHotPicsDatas == null) {
            mHotPicsDatas = new ArrayList<>();
        } else {
            transformHomeData(mHotPicsDatas, HomeAdapter.TYPE_HOT_PICS, true);
        }
        loadTitle(TYPE_STANDARD, HomeAdapter.TYPE_COMMON_TITLE, true);
    }



    private void insertStandard() {
        if (mStandardDatas == null) {
            mStandardDatas = new ArrayList<>();
        } else {
            transformHomeData(mStandardDatas, HomeAdapter.TYPE_STANDARD, true);
        }

        insertRecoDics();
    }

    private void insertRecoDics() {
        if (mRecoDatas == null) {
            mRecoDatas = new ArrayList<>();
        } else {
            transformHomeData(mRecoDatas, HomeAdapter.TYPE_BANNER_WORDS, true);
        }
        loadTitle(TYPE_NEW_BOOKS, HomeAdapter.TYPE_COMMON_TITLE, true);
    }

    private void insertNewBooks() {
        if (mNewBooks == null) {
            mNewBooks = new ArrayList<>();
        } else {
            transformHomeData(mNewBooks, HomeAdapter.TYPE_NEW_BOOKS, true);
        }
        loadTitle(TYPE_HOT_ARTICLE, HomeAdapter.TYPE_COMMON_TITLE, true);
    }

    private void insertArticles() {
        if (mArticles == null) {
            mArticles = new ArrayList<>();
        } else {
            transformHomeData(mArticles, HomeAdapter.TYPE_ARTICLE, true);
        }

        // 数据加载完毕
        closeLoading();
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        unbindView();
        homeBannerObserver.unSubscribe();
        hotPicsObserver.unSubscribe();
        standardObserver.unSubscribe();
        recWordsObserver.unSubscribe();
        newBooksObserver.unSubscribe();
        articleObserver.unSubscribe();
        // 清理内存数据
        HomeDataMapper.reset();
        mHomeBannerDatas = null;
        mStoreDatas = null;
        mHotPicsDatas = null;
        mTitleDatas = null;
        mStandardDatas = null;
        mRecoDatas = null;
        mNewBooks = null;
        mArticles = null;
    }

    /**
     * 初始化数据
     */
    public void initData() {
        resetState();

        loadData();
    }

    private void loadData() {
        model.getHomeBannerWithCache(homeBannerObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
        loadLocalData();
        loadHotPicsData();
        loadStandardData();
        loadRecoWordsData();
        loadNewBooksData();
        loadArticleData();
    }

    @Override
    protected void resetState() {
        super.resetState();
        // 清理内存首页数据
        HomeDataMapper.reset();
        homeDatas = new ArrayList<>();

        mHomeBannerFlag = false;
        mHotPicsFlag = false;
        mStandardFlag = false;
        mRecoFlag = false;
        mNewBooksFlag = false;
        mArticlesFlag = false;
    }

    private void loadLocalData() {
        mStoreDatas = new ArrayList<>();
        for (int i=0;i<storeImgArr.length;i++) {
            LocalDataBean bean = new LocalDataBean();
            bean.setText(storeStrArr[i]);
            bean.setResId(storeImgArr[i]);
            bean.setHomeStoreType(homeStoreArr[i]);
            mStoreDatas.add(bean);
        }

        // 初始化首页标题资源
        mTitleDatas = new ArrayList<>();
        for (int i=0;i<titleImgArr.length;i++) {
            LocalDataBean bean = new LocalDataBean();
            bean.setText(titleStrArr[i]);
            bean.setResId(titleImgArr[i]);
            mTitleDatas.add(bean);
        }
    }

    /**
     * 将数据封装成HomeData
     * @param list
     * @param type
     * @param isSpan
     */
    private void transformHomeData(List list, int type, boolean isSpan) {
        switch (type) {
            case HomeAdapter.TYPE_BANNER:
                homeDatas.addAll(HomeDataMapper.transformBannerEntitys(list, type, isSpan));
                break;
            case HomeAdapter.TYPE_STORE:
                homeDatas.addAll(HomeDataMapper.transformStoreDatas(list, type, isSpan));
                break;
            case HomeAdapter.TYPE_HOT_PICS:
                homeDatas.addAll(HomeDataMapper.transformBannerEntitys(list, type, isSpan));
                break;
            case HomeAdapter.TYPE_STANDARD:
                homeDatas.addAll(HomeDataMapper.transformStandardDatas(list, type, isSpan));
                break;
            case HomeAdapter.TYPE_BANNER_WORDS:
                homeDatas.addAll(HomeDataMapper.transformBannerEntitys(list, type, isSpan));
                break;
            case HomeAdapter.TYPE_NEW_BOOKS:
                homeDatas.addAll(HomeDataMapper.transformNewBookEntitys(list, type, isSpan));
                break;
            case HomeAdapter.TYPE_ARTICLE:
                homeDatas.addAll(HomeDataMapper.transformArticleDatas(list, type, isSpan));
                break;
        }
    }

    private void closeLoading() {
        if (checkData() && view() != null) {
            if (mHomeAdapter == null) {
                mHomeAdapter = new HomeAdapter(mContext, homeDatas);
                view().showView(mHomeAdapter);
            } else {
                mHomeAdapter.setData(homeDatas);
            }
            view().showLoading(false);
        }
    }

    /**
     * 验证是否获取完数据
     * @return
     */
    private boolean checkData() {
        return (mHomeBannerFlag &&
                mHotPicsFlag &&
                mStandardFlag &&
                mRecoFlag &&
                mNewBooksFlag &&
                mArticlesFlag
        );
    }

    public void gotoActivity() {
        Intent callIntent = SearchActivity.getCallIntent(mContext);
        view().gotoActivity(callIntent);
    }

    public void shareApp() {
//        //新建ShareBoardConfig
//        ShareBoardConfig config = new ShareBoardConfig();
//        // config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);//设置位置
//        config.setTitleText(mContext.getString(R.string.app_name));
//        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
//        config.setCancelButtonVisibility(true);

        UMWeb web = new UMWeb("http://www.chlip.com.cn");
        web.setTitle(mContext.getString(R.string.app_name));//标题
        web.setThumb(new UMImage(mContext,R.mipmap.qg_app_icon));  //缩略图
        web.setDescription(mContext.getString(R.string.text_book_resource_holder));//描述

        new ShareAction(((HomeFragment)view()).getActivity())
                .withMedia(web)
                .setDisplayList(
                        SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.QQ,
                        SHARE_MEDIA.QZONE,
                        SHARE_MEDIA.SINA)
                .setCallback(this)
                .open();

    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
