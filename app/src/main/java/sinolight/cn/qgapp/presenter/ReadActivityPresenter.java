package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.ReaderEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IReadActivityView;

/**
 * Created by xns on 2017/6/29.
 * VideoInfo Presenter
 */

public class ReadActivityPresenter extends BasePresenter<IReadActivityView, HttpManager> {
    private static final String TAG = "ReadActivityPresenter";
    private Context mContext;

    // VideoParentID
    private String readID;
    private AppContants.Read.Type readResType;
    private String chapteredID;
    private ReaderEntity readData;
    private String footerName;

    private HttpSubscriber<ReaderEntity> mReadObserver = new HttpSubscriber<>(
            new OnResultCallBack<ReaderEntity>() {

                @Override
                public void onSuccess(ReaderEntity readerEntity) {
                    if (readerEntity != null) {
                        readData = readerEntity;
                        showSuccess();
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mReadObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
                }
            });

    private void showSuccess() {
        showWithData();
    }

    private void showError() {
        view().showRefreshing(false);
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showWithData() {
        view().showData(readData);
        view().showFooter(footerName);
        this.closeRefreshing();
    }

    public ReadActivityPresenter(IReadActivityView view, Context context) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        if (mReadObserver != null) {
            mReadObserver.unSubscribe();
        }

        mCollectObserver.unSubscribe();

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
        model.doReadNoCache(
                mReadObserver,
                AppHelper.getInstance().getCurrentToken(),
                readResType.getType(),
                readID,
                chapteredID
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
            footerName = intent.getStringExtra(AppContants.Read.READ_NAME);
            readID = intent.getStringExtra(AppContants.Read.READ_ID);
            chapteredID = intent.getStringExtra(AppContants.Read.CHAPTERED_ID);
            readResType = (AppContants.Read.Type) intent.getSerializableExtra(AppContants.Read.READ_RES_TYPE);
            view().showRefreshing(true);
            // load data
            this.getData();
        }
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
            view().setCollectState(true);
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
                readID
        );
    }

}
