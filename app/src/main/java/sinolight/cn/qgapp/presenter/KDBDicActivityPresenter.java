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
        view().showErrorToast(msgId);
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
        mDicObserver.unSubscribe();
        mCollectObserver.unSubscribe();
        unbindView();
    }

    public void init2show(Intent intent) {
        if (intent != null) {
            view().showRefreshing(true);
            resId = intent.getStringExtra(AppContants.Resource.RES_ID);
            // Load data
            getData();
        }
    }

    private void getData() {
        model.getKDBEntryInfoNoCache(
                mDicObserver,
                AppHelper.getInstance().getCurrentToken(),
                resId
        );
    }


    private HttpSubscriber<Object> mCollectObserver = new HttpSubscriber<>(
            new OnResultCallBack<Object>() {

                @Override
                public void onSuccess(Object o) {
                    showErrorToast(R.string.text_collect_success);
                    view().showRefreshing(false);
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mCollectObserver code:" + code + ",errorMsg:" + errorMsg);
                    checkoutCollectState(code, errorMsg);
                }
            });

    private void checkoutCollectState(int code, String errorMsg) {
        if (code == AppContants.SUCCESS_CODE) {
            getData();
            view().showStrToast(errorMsg);
        } else {
            showError();
        }
    }

    public void collectRes(AppContants.DataBase.Res resType) {
        model.collectResNoCache(
                mCollectObserver,
                AppHelper.getInstance().getCurrentToken(),
                resType.getType(),
                resId,
                getAction()
        );
    }

    private int getAction() {
        if (dicData.isfavor()) {
            return AppContants.Collect.ACTION_UNCOLLECT;
        } else {
            return AppContants.Collect.ACTION_COLLECT;
        }
    }

}
