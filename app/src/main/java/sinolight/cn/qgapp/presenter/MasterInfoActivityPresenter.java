package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.data.http.entity.MasterEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IMasterInfoActivityView;

/**
 * Created by xns on 2017/6/29.
 * VideoInfo Presenter
 */

public class MasterInfoActivityPresenter extends BasePresenter<IMasterInfoActivityView, HttpManager> {
    private static final String TAG = "MasterInfoActivityPresenter";
    private Context mContext;

    private String masterID;
    private MasterEntity masterData;

    private HttpSubscriber<MasterEntity> masterObserver = new HttpSubscriber<>(
            new OnResultCallBack<MasterEntity>() {

                @Override
                public void onSuccess(MasterEntity masterEntity) {
                    if (masterEntity != null) {
                        masterData = masterEntity;
                        showSuccess();
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mVideoObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
                }
            });

    private void showSuccess() {
        view().showRefreshing(false);
        showWithData();
    }

    private void showError() {
        view().showRefreshing(false);
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showWithData() {
        view().init2show(masterData);
    }

    public MasterInfoActivityPresenter(IMasterInfoActivityView view, Context context) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        if (masterObserver != null) {
            masterObserver.unSubscribe();
        }

        KDBResDataMapper.reset();
        unbindView();
    }

    private void closeRefreshing() {
        view().showRefreshing(false);
    }

    private void showErrorToast(int resId) {
        view().showToast(resId);
        view().showRefreshing(false);
    }

    public void init2Show() {
        resetState();
        // load video set data
        getData();
    }

    private void getData() {
        model.getMasterInfoNoCache(
                masterObserver,
                AppHelper.getInstance().getCurrentToken(),
                masterID
        );
    }

    /**
     * 重置状态
     */
    @Override
    protected void resetState() {
        super.resetState();
    }

    public void checkoutIntent(Intent intent) {
        if (intent == null) {
            view().showRefreshing(false);
        } else {
            masterID = intent.getStringExtra(AppContants.Master.MASTER_ID);
            view().showRefreshing(true);
            // load data
            this.getData();
        }
    }

}
