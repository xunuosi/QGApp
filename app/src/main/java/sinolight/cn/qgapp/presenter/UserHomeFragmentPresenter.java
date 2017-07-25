package sinolight.cn.qgapp.presenter;

import android.content.Context;

import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.UserEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.LoginActivity;
import sinolight.cn.qgapp.views.view.IUserFragmentView;
import sinolight.cn.qgapp.views.view.IUserHomeFragmentView;

/**
 * Created by xns on 2017/7/8.
 * 用户主页的Presenter
 */

public class UserHomeFragmentPresenter extends BasePresenter<IUserHomeFragmentView, HttpManager>{
    private static final String TAG = "UserHomeFragmentPresenter";
    private Context mContext;
    private UserEntity userData;

    private HttpSubscriber userObserver = new HttpSubscriber(new OnResultCallBack<UserEntity>() {


        @Override
        public void onSuccess(UserEntity userEntity) {
            if (userEntity != null) {
                userData = userEntity;
                showSuccess();
            }
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "userObserver code:" + code + ",errorMsg:" + errorMsg);
            showError();
        }
    });

    private void showSuccess() {
        view().init2Show(userData);
    }

    private void showError() {
        showErrorToast(R.string.attention_data_refresh_error);
        // Go to LoginActivity repeat to login

    }

    private void showErrorToast(int msgId) {
        view().showErrorToast(msgId);
    }

    public UserHomeFragmentPresenter(Context context) {
        this.mContext = context;
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        userObserver.unSubscribe();
        unbindView();
    }

    public void init2Show() {
        model.getUserInfoWithCache(
                userObserver,
                AppHelper.getInstance().getCurrentToken(),
                AppHelper.getInstance().getCurrentUserName(),
                false);
    }
}
