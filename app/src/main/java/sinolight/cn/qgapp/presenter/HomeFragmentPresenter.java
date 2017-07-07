package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.HomeAdapter;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.data.bean.LocalDataBean;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.BannerEntity;
import sinolight.cn.qgapp.data.http.entity.NewBookEntity;
import sinolight.cn.qgapp.data.http.entity.RecommendEntity;
import sinolight.cn.qgapp.data.http.entity.StandardEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.HomeDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IHomeFragmentView;

/**
 * Created by xns on 2017/7/5.
 * HomeFragment Presenter
 */

public class HomeFragmentPresenter extends BasePresenter<IHomeFragmentView, HttpManager> {
    private static final String TAG = "HomeFragmentPresenter";
    private static final int TYPE_STANDARD = 0;
    private static final int TYPE_NEW_BOOKS = 1;
    private static final int TYPE_HOT_ARTICLE = 2;

    private String token;
    private Context mContext;
    private List<HomeData> homeDatas;
    private HomeAdapter mHomeAdapter;

    private int[] storeImgArr = {
            R.drawable.holder_circle_image,
            R.drawable.holder_circle_image,
            R.drawable.store_icon_bk,
            R.drawable.store_icon_standard,
            R.drawable.store_icon_ebk,
            R.drawable.store_icon_master
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
    private List<StandardEntity> mStandardDatas;
    private List<LocalDataBean> mStoreDatas;
    private List<LocalDataBean> mTitleDatas;

    private HttpSubscriber homeBannerObserver = new HttpSubscriber(new OnResultCallBack<List<BannerEntity>>() {

        @Override
        public void onSuccess(List<BannerEntity> bannerEntities) {
            mHomeBannerDatas = bannerEntities;
            transformHomeData(mHomeBannerDatas, HomeAdapter.TYPE_BANNER, true);
            // 初始化本地Item数据
            initLocalData();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "code:" + code + ",errorMsg:" + errorMsg);
            mHomeBannerDatas = new ArrayList<>();
            transformHomeData(mHomeBannerDatas, HomeAdapter.TYPE_BANNER, true);
            // 初始化本地Item数据
            initLocalData();
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
        }
    });

    private HttpSubscriber hotPicsObserver = new HttpSubscriber(new OnResultCallBack<List<BannerEntity>>() {

        @Override
        public void onSuccess(List<BannerEntity> bannerEntities) {
            mHotPicsDatas = bannerEntities;
            transformHomeData(mHotPicsDatas, HomeAdapter.TYPE_HOT_PICS, true);
            loadTitle(TYPE_STANDARD, HomeAdapter.TYPE_COMMON_TITLE, true);
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "hotPicsObserver code:" + code + ",errorMsg:" + errorMsg);
            mHotPicsDatas = new ArrayList<>();
            transformHomeData(mHotPicsDatas, HomeAdapter.TYPE_HOT_PICS, true);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            loadTitle(TYPE_STANDARD, HomeAdapter.TYPE_COMMON_TITLE, true);
        }
    });

    private HttpSubscriber standardObserver = new HttpSubscriber(new OnResultCallBack<List<StandardEntity>>() {

        @Override
        public void onSuccess(List<StandardEntity> bannerEntities) {
            mStandardDatas = bannerEntities;
            transformHomeData(mStandardDatas, HomeAdapter.TYPE_STANDARD, true);
            loadRecoWordsData();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "hotPicsObserver code:" + code + ",errorMsg:" + errorMsg);
            mStandardDatas = new ArrayList<>();
            transformHomeData(mStandardDatas, HomeAdapter.TYPE_STANDARD, true);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            loadRecoWordsData();
        }
    });

    private HttpSubscriber recWordsObserver = new HttpSubscriber(new OnResultCallBack<List<RecommendEntity>>() {

        @Override
        public void onSuccess(List<RecommendEntity> bannerEntities) {
            mRecoDatas = bannerEntities;
            transformHomeData(mRecoDatas, HomeAdapter.TYPE_BANNER_WORDS, true);
            loadTitle(TYPE_NEW_BOOKS, HomeAdapter.TYPE_COMMON_TITLE, true);
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "hotPicsObserver code:" + code + ",errorMsg:" + errorMsg);
            mRecoDatas = new ArrayList<>();
            transformHomeData(mRecoDatas, HomeAdapter.TYPE_BANNER_WORDS, true);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            loadTitle(TYPE_NEW_BOOKS, HomeAdapter.TYPE_COMMON_TITLE, true);
        }
    });

    private HttpSubscriber newBooksObserver = new HttpSubscriber(new OnResultCallBack<List<NewBookEntity>>() {

        @Override
        public void onSuccess(List<NewBookEntity> bannerEntities) {
            mNewBooks = bannerEntities;
            transformHomeData(mNewBooks, HomeAdapter.TYPE_NEW_BOOKS, true);
            loadTitle(TYPE_HOT_ARTICLE, HomeAdapter.TYPE_COMMON_TITLE, true);
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "hotPicsObserver code:" + code + ",errorMsg:" + errorMsg);
            mNewBooks = new ArrayList<>();
            transformHomeData(mNewBooks, HomeAdapter.TYPE_NEW_BOOKS, true);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            loadTitle(TYPE_HOT_ARTICLE, HomeAdapter.TYPE_COMMON_TITLE, true);
        }
    });

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
                loadStandardData();
                break;
            case TYPE_NEW_BOOKS:
                insertHomeData(mTitleDatas.get(TYPE_NEW_BOOKS), adapterType, span);
                loadNewBooksData();
                break;
            case TYPE_HOT_ARTICLE:
                insertHomeData(mTitleDatas.get(TYPE_HOT_ARTICLE), adapterType, span);
                break;
        }
    }

    /**
     * 获取新书数据
     */
    private void loadNewBooksData() {
        model.getNewsBooksWithCache(newBooksObserver, token, false);
    }

    private void loadStandardData() {
        model.getNewestStdDataWithCache(standardObserver, token, false);
    }

    /**
     * 获取推荐词条
     */
    private void loadRecoWordsData() {
        model.getRecommendWordsWithCache(recWordsObserver, token, false);
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
        token = AppHelper.getInstance().getCurrentToken();
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
        // 清理内存数据
        HomeDataMapper.reset();
        mStoreDatas = null;
    }

    /**
     * 初始化数据
     */
    public void initData() {
        // 清理内存首页数据
        HomeDataMapper.reset();
        homeDatas = new ArrayList<>();

        model.getHomeBannerWithCache(homeBannerObserver,
                AppHelper.getInstance().getCurrentToken(),
                false);
    }

    private void initLocalData() {
        mStoreDatas = new ArrayList<>();
        for (int i=0;i<storeImgArr.length;i++) {
            LocalDataBean bean = new LocalDataBean();
            bean.setText(storeStrArr[i]);
            bean.setResId(storeImgArr[i]);
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

        transformHomeData(mStoreDatas, HomeAdapter.TYPE_STORE, false);

        // 获取热门图集
        model.getHotPicsWithCache(hotPicsObserver, AppHelper.getInstance().getCurrentToken(), false);
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
                homeDatas.addAll(HomeDataMapper.transformLocalDatas(list, type, isSpan));
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
        }
        // 数据加载完毕
        closeLoading();
    }

    private void closeLoading() {
        if (checkData()) {
            L.d(TAG, "isLoaded" + ",homeDatas:" + homeDatas.toString());
            mHomeAdapter = new HomeAdapter(mContext, homeDatas);
            view().showLoading(false);
            view().showView(mHomeAdapter);
        }
    }

    /**
     * 验证是否获取完数据
     * @return
     */
    private boolean checkData() {
        return (mHomeBannerDatas != null &&
                mStoreDatas != null &&
                mHotPicsDatas != null &&
                mTitleDatas !=null);
    }
}
