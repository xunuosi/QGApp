package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;


import java.math.BigInteger;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.TokenEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.utils.RSA;
import sinolight.cn.qgapp.views.activity.HomeActivity;
import sinolight.cn.qgapp.views.view.ILoginActivityView;

/**
 * Created by xns on 2017/6/29.
 * LoginActivity Presenter
 */

public class LoginActivityPresenter extends BasePresenter<ILoginActivityView, DaoSession> {
    private static final String TAG = "LoginActivityPresenter";
    private Context mContext;
    private String userName;
    private String pwd;
    private boolean isLogined;
    private HttpSubscriber loginObserver = new HttpSubscriber(new OnResultCallBack<TokenEntity>() {

        @Override
        public void onSuccess(TokenEntity tokenEntity) {
            String token = tokenEntity.getToken();

            AppHelper.getInstance().setCurrentUserName(userName);
            AppHelper.getInstance().setCurrentToken(token);
            if (!isLogined) {
                String rsa = null;
                try {
                    rsa = RSA.encryptBASE64(pwd.getBytes());
                    AppHelper.getInstance().setCurrentPW(rsa);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            showSuccess();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "code:" + code + ",errorMsg:" + errorMsg);
            view().showLoading(false);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            if (userName != null) {
                view().initShow(userName);
            }
        }
    });

    private void showSuccess() {
        view().showLoading(false);
        view().showToastMsg(R.string.text_login_success);
        // Go to HomeActivity
        Intent callIntent = HomeActivity.getCallIntent(mContext);
        callIntent.putExtra(AppContants.Account.IS_LOGINED, true);
        view().gotoActivity(callIntent);
    }

    public LoginActivityPresenter(ILoginActivityView view, DaoSession daoSession, Context context) {
        mContext = context;
        setModel(daoSession);
        bindView(view);
    }

    public void init2show(Intent intent) {
        userName = AppHelper.getInstance().getCurrentUserName();
        if (intent != null) {
            isLogined = intent.getBooleanExtra(AppContants.Account.IS_LOGINED, false);
        }
        // auto login app
        if (isLogined) {
            view().showLoading(true);
            view().initShow(userName);
            try {
                String rsa = AppHelper.getInstance().getCurrentPW();
                byte[] bytes = RSA.decryptBASE64(rsa);
                String pw = new String(bytes);
                HttpManager.getInstance().login(loginObserver,
                        AppHelper.getInstance().getCurrentUserName(),
                        pw);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        unbindView();
        loginObserver.unSubscribe();
    }

    public void login(String userName, String pwd) {
        if (checkData(userName, pwd)) {
            view().showLoading(true);
            this.userName = userName;
            this.pwd = pwd;
            HttpManager.getInstance().login(loginObserver, this.userName, this.pwd);

        }
    }

    private boolean checkData(String userName, String pwd) {
        if (TextUtils.isEmpty(userName)) {
            view().showToastMsg(R.string.text_user_empty);
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            view().showToastMsg(R.string.text_pwd_empty);
            return false;
        }
        return true;
    }
}
