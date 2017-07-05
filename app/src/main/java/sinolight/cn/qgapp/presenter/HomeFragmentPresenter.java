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
    private List<LocalDataBean> mStoreDatas;
    private HttpSubscriber homeBannerObserver = new HttpSubscriber(new OnResultCallBack<List<BannerEntity>>() {

        @Override
        public void onSuccess(List<BannerEntity> bannerEntities) {
            mHomeBannerDatas = bannerEntities;
            transformHomeData(mHomeBannerDatas, HomeAdapter.TYPE_BANNER, true);
            closeLoading();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "code:" + code + ",errorMsg:" + errorMsg);
            mHomeBannerDatas = new ArrayList<>();
            transformHomeData(mHomeBannerDatas, HomeAdapter.TYPE_BANNER, true);
            closeLoading();
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
        // 初始化本地Item数据
        initLocalData();
    }

    private void initLocalData() {
        mStoreDatas = new ArrayList<>();
        for (int i = 0;i<6;i++) {
            LocalDataBean bean = new LocalDataBean();
            bean.setText(R.string.text_knowledge_store);
            bean.setText(R.drawable.holder_circle_image);
            mStoreDatas.add(bean);
        }
        transformHomeData(mStoreDatas, HomeAdapter.TYPE_STORE, false);
        closeLoading();
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
        }
    }

    private void closeLoading() {
        if (checkData()) {
            L.d(TAG, "isLoaded" + ",homeDatas:" + homeDatas.toString());
            mHomeAdapter = new HomeAdapter(mContext, homeDatas);
//            view().showLoading(false);
            view().showView(mHomeAdapter);
        }
    }

    /**
     * 验证是否获取完数据
     * @return
     */
    private boolean checkData() {
        return (mHomeBannerDatas != null && mStoreDatas != null);
    }
}
