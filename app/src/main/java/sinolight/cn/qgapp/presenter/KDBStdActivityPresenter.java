package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.StdInfoEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.ChapterActivity;
import sinolight.cn.qgapp.views.view.IKDBStdDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity Presenter
 */

public class KDBStdActivityPresenter extends BasePresenter<IKDBStdDetailActivityView, HttpManager> {
    private static final String TAG = "KDBBookActivityPresenter";

    private Context mContext;
    private String resId;
    private StdInfoEntity stdData;

    private HttpSubscriber<StdInfoEntity> mStdObserver = new HttpSubscriber<>(
            new OnResultCallBack<StdInfoEntity>() {

                @Override
                public void onSuccess(StdInfoEntity stdInfoEntity) {
                    if (stdInfoEntity != null) {
                        stdData = stdInfoEntity;
                        showView();
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mStdObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
                }
            });

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
            view().setCollectState(true);
            view().showStrToast(errorMsg);
        } else {
            showError();
        }
    }

    private void showView() {
        view().showRefreshing(false);
        view().init2Show(stdData);
    }

    private void showError() {
        view().showRefreshing(false);
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showErrorToast(int msgId) {
        view().showErrorToast(msgId);
    }

    public KDBStdActivityPresenter(Context context, IKDBStdDetailActivityView view) {
        mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        view().showRefreshing(false);
        mStdObserver.unSubscribe();
        mCollectObserver.unSubscribe();
        unbindView();
    }

    public void init2show(Intent intent) {
        if (intent != null) {
            view().showRefreshing(true);
            resId = intent.getStringExtra(AppContants.Resource.RES_ID);

            // Load data
            model.getKDBStdInfoNoCache(
                    mStdObserver,
                    AppHelper.getInstance().getCurrentToken(),
                    resId
            );
        }
    }

    public Intent gotoActivity() {
        Intent callIntent = ChapterActivity.getCallIntent(mContext);
        callIntent.putExtra(AppContants.Read.READ_ID, stdData.getId());
        callIntent.putExtra(AppContants.Read.READ_NAME, stdData.getName());
        callIntent.putExtra(AppContants.Read.READ_RES_TYPE, AppContants.Read.Type.TYPE_STAND);
        return callIntent;
    }

    public void collectRes(AppContants.DataBase.Res resType) {
        model.collectResNoCache(
                mCollectObserver,
                AppHelper.getInstance().getCurrentToken(),
                resType.getType(),
                resId
        );
    }
}
