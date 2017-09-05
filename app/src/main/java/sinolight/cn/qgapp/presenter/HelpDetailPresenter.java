package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.HelpDataEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IHelpDetailView;

/**
 * Created by xns on 2017/7/8.
 * 知识库界面的Presenter
 */

public class HelpDetailPresenter extends BasePresenter<IHelpDetailView, HttpManager> {
    private static final String TAG = "HelpDetailPresenter";
    private Context mContext;
    private HelpDataEntity mData;
    private String id;

    private HttpSubscriber<HelpDataEntity> mHelpObserver = new HttpSubscriber<>(new OnResultCallBack<HelpDataEntity>() {

        @Override
        public void onSuccess(HelpDataEntity dataEntity) {
            if (dataEntity != null) {
                mData = dataEntity;
                showSuccess();
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "mHelpObserver code:" + code + ",errorMsg:" + errorMsg);
            showError(code, errorMsg);
        }
    });

    private void showError(int code, String errorMsg) {
        view().showRefreshing(false);
        if (code == AppContants.SUCCESS_CODE) {
            view().showToastByStr(errorMsg);
        } else {
            view().showToastByRes(R.string.error_internet);
        }
    }

    private void showSuccess() {
        view().showRefreshing(false);
        view().showView(mData);
    }

    public HelpDetailPresenter(Context context, IHelpDetailView view) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
        bindView(view);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        mHelpObserver.unSubscribe();
        unbindView();
    }

    private void getData() {
        model.getHelpDetailNoCache(
                mHelpObserver,
                AppHelper.getInstance().getCurrentToken(),
                id
        );
    }

    public void checkout(Intent intent) {
        if (intent != null) {
            id = intent.getStringExtra(AppContants.Help.HELP_ID);
            if (id != null) {
                view().showRefreshing(true);
                getData();
            }
        }
    }
}
