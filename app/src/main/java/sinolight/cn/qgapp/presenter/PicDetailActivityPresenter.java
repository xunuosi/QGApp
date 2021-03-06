package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IResPicDetailView;

/**
 * Created by xns on 2017/6/29.
 * MaterialList Presenter
 */

public class PicDetailActivityPresenter extends BasePresenter<IResPicDetailView, HttpManager> {
    private static final String TAG = "PicDetailActivityPresenter";
    private Context mContext;

    private ResImgEntity mData;
    private String picID;

    private HttpSubscriber<ResImgEntity> mPicObserver = new HttpSubscriber<>(
            new OnResultCallBack<ResImgEntity>() {

                @Override
                public void onSuccess(ResImgEntity resImgEntity) {
                    if (resImgEntity != null) {
                        mData = resImgEntity;
                        showSuccess();
                    } else {
                        showError(0, null);
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mPicObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError(code, errorMsg);
                }
            });

    private void showSuccess() {
        closeRefreshing();
        view().showView(mData);
    }

    private void showError(int code, String msg) {
        closeRefreshing();
        view().showStrToast(msg);
    }

    public PicDetailActivityPresenter(Context context, IResPicDetailView view) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
        bindView(view);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        mPicObserver.unSubscribe();

        KDBResDataMapper.reset();
        unbindView();
    }

    private void closeRefreshing() {
        view().showRefreshing(false);
    }

    private void init2Show() {

        resetState();
        // load video set data
        getData();
    }

    private void getData() {
        model.getResPicDetailNoCache(
                mPicObserver,
                AppHelper.getInstance().getCurrentToken(),
                picID
        );

    }

    public void checkIntent(Intent intent) {
        if (intent != null) {
            picID = intent.getStringExtra(AppContants.Resource.RES_ID);
            view().showRefreshing(true);
        }
        init2Show();
    }


    /**
     * 重置状态
     */
    @Override
    protected void resetState() {
        super.resetState();
        mData = null;
    }
}
