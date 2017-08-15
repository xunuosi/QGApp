package sinolight.cn.qgapp.presenter;

import android.content.Context;

import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.AboutEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IAboutActivityView;

/**
 * Created by xns on 2017/7/8.
 * 知识库界面的Presenter
 */

public class AboutActivityPresenter extends BasePresenter<IAboutActivityView, HttpManager> {
    private static final String TAG = "AboutActivityPresenter";
    private Context mContext;
    private AboutEntity mData;

    private HttpSubscriber<List<AboutEntity>> mAboutObserver = new HttpSubscriber<>(new OnResultCallBack<List<AboutEntity>>() {

        @Override
        public void onSuccess(List<AboutEntity> list) {
            if (list != null) {
                mData = list.get(0);
                showSuccess();
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "mAboutObserver code:" + code + ",errorMsg:" + errorMsg);
            showError(code, errorMsg);
        }
    });

    public AboutActivityPresenter(Context context, IAboutActivityView view) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    private void showSuccess() {
        view().showView(mData);
    }

    private void showError(int code, String msg) {
        if (code != AppContants.SUCCESS_CODE) {
            view().showToast(R.string.attention_data_refresh_error);
        } else {
            view().showToast(R.string.text_my_database_is_empty);
        }

    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        mAboutObserver.unSubscribe();
        unbindView();
    }

    public void init2Show() {
        resetState();
        getData();
    }

    private void getData() {
        model.getAboutWithCache(
                mAboutObserver,
                AppHelper.getInstance().getCurrentToken(),
                false
        );
    }

    @Override
    protected void resetState() {
        super.resetState();
    }

}
