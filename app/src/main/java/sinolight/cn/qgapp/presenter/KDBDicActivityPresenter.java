package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;


import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DicInfoEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IKDBDicDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity Presenter
 */

public class KDBDicActivityPresenter extends BasePresenter<IKDBDicDetailActivityView, HttpManager> {
    private static final String TAG = "KDBDicActivityPresenter";

    private Context mContext;
    private String resId;
    private DicInfoEntity dicData;

    private HttpSubscriber<DicInfoEntity> mDicObserver = new HttpSubscriber<>(
            new OnResultCallBack<DicInfoEntity>() {

                @Override
                public void onSuccess(DicInfoEntity dicInfoEntity) {
                    if (dicInfoEntity != null) {
                        dicData = dicInfoEntity;
                        showView();
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mDBResTypeObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
                }
            });

    private void showView() {
        view().init2Show(dicData);
    }

    private void showError() {
        view().showRefreshing(false);
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showErrorToast(int msgId) {

    }

    public KDBDicActivityPresenter(Context context, IKDBDicDetailActivityView view) {
        mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {

    }

    public void init2show(Intent intent) {
        if (intent != null) {
            view().showRefreshing(true);
            resId = intent.getStringExtra(AppContants.Resource.RES_ID);
            // Load data
            model.getKDBEntryInfoNoCache(
                    mDicObserver,
                    AppHelper.getInstance().getCurrentToken(),
                    resId
            );
        }
    }
}
