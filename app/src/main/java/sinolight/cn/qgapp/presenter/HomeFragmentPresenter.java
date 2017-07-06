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
    private Context mContext;
    private List<HomeData> homeDatas;
    private HomeAdapter mHomeAdapter;

    private List<BannerEntity> mHomeBannerDatas;
    private List<BannerEntity> mHotPicsDatas;
    private List<LocalDataBean> mStoreDatas;

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
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "hotPicsObserver code:" + code + ",errorMsg:" + errorMsg);
            mHotPicsDatas = new ArrayList<>();
            transformHomeData(mHotPicsDatas, HomeAdapter.TYPE_HOT_PICS, true);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
        }
    });

    @Inject
    public HomeFragmentPresenter(Context context) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        unbindView();
        homeBannerObserver.unSubscribe();
        hotPicsObserver.unSubscribe();
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

        LocalDataBean bean1 = new LocalDataBean();
        bean1.setText(R.string.text_knowledge_store);
        bean1.setResId(R.drawable.holder_circle_image);
        mStoreDatas.add(bean1);

        LocalDataBean bean2 = new LocalDataBean();
        bean2.setText(R.string.text_resource_store);
        bean2.setResId(R.drawable.holder_circle_image);
        mStoreDatas.add(bean2);

        LocalDataBean bean3 = new LocalDataBean();
        bean3.setText(R.string.text_baike_store);
        bean3.setResId(R.drawable.holder_circle_image);
        mStoreDatas.add(bean3);

        LocalDataBean bean4 = new LocalDataBean();
        bean4.setText(R.string.text_standard_store);
        bean4.setResId(R.drawable.holder_circle_image);
        mStoreDatas.add(bean4);

        LocalDataBean bean5 = new LocalDataBean();
        bean5.setText(R.string.text_eBook_store);
        bean5.setResId(R.drawable.holder_circle_image);
        mStoreDatas.add(bean5);

        LocalDataBean bean6 = new LocalDataBean();
        bean6.setText(R.string.text_master_store);
        bean6.setResId(R.drawable.holder_circle_image);
        mStoreDatas.add(bean6);

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
                homeDatas.addAll(HomeDataMapper.transformBannerDatas(list, type, isSpan));
                break;
            case HomeAdapter.TYPE_STORE:
                homeDatas.addAll(HomeDataMapper.transformLocalDatas(list, type, isSpan));
                break;
            case HomeAdapter.TYPE_HOT_PICS:
                homeDatas.addAll(HomeDataMapper.transformBannerDatas(list, type, isSpan));
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
                mHotPicsDatas != null);
    }
}
