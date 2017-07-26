package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.text.TextUtils;

import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IUserHomeFragmentView;

/**
 * Created by xns on 2017/7/8.
 * 用户主页的Presenter
 */

public class UserHomeFragmentPresenter extends BasePresenter<IUserHomeFragmentView, HttpManager>{
    private static final String TAG = "UserHomeFragmentPresenter";
    private Context mContext;
    private String newPwd;

    private HttpSubscriber pwdObserver = new HttpSubscriber(new OnResultCallBack() {

        @Override
        public void onSuccess(Object o) {
            showSuccess();
            // Save new pwd
            AppHelper.getInstance().setCurrentPW(newPwd);
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "userObserver code:" + code + ",errorMsg:" + errorMsg);
            showError(code, errorMsg);
        }
    });

    private void showSuccess() {
        showErrorToast(R.string.text_change_pwd_success);
        view().clear();
    }

    private void showError(int code, String errorMsg) {
        // code：-1 成功，0 身份验证失败，1信息不完成，2密码错误，3 两次密码不一致
        switch (code) {
            case 0:
            case 1:
            case 2:
            case 3:
                view().showErrorToast(errorMsg);
                break;
           default:
               showErrorToast(R.string.attention_data_refresh_error);
               break;
        }
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
        pwdObserver.unSubscribe();
        unbindView();
    }

    /**
     * Checkout data about change password.
     * @param oldPW
     * @param newPW
     * @param confirmPW
     */
    public void changePW(String oldPW, String newPW, String confirmPW) {
        if (checkoutData(oldPW, newPW, confirmPW)) {
            model.changePwdNoCache(
                    pwdObserver,
                    AppHelper.getInstance().getCurrentToken(),
                    oldPW,
                    newPW,
                    confirmPW,
                    AppHelper.getInstance().getCurrentUserName()
            );
            newPwd = newPW;
        }
    }

    private boolean checkoutData(String oldPW, String newPW, String confirmPW) {
        if (TextUtils.isEmpty(oldPW)) {
            view().showErrorToast(R.string.text_old_pwd_empty);
            return false;
        } else if (TextUtils.isEmpty(newPW)) {
            view().showErrorToast(R.string.text_new_pwd_empty);
            return false;
        } else if (TextUtils.isEmpty(confirmPW)) {
            view().showErrorToast(R.string.text_repwd_empty);
            return false;
        }
        return true;
    }
}
