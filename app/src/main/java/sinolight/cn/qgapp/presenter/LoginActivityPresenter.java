package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;


import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.TokenEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.utils.MD5;
import sinolight.cn.qgapp.utils.SharedPfUtil;
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
    private HttpSubscriber loginObserver = new HttpSubscriber(new OnResultCallBack<TokenEntity>() {

        @Override
        public void onSuccess(TokenEntity tokenEntity) {
            String token = tokenEntity.getToken();

            AppHelper.getInstance().setCurrentUserName(userName);
            AppHelper.getInstance().setCurrentToken(token);
            AppHelper.getInstance().setCurrentPW(pwd);
            showSuccess();
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "code:" + code + ",errorMsg:" + errorMsg);
            view().showLoading(false);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
        }
    });

    private void showSuccess() {
        view().showLoading(false);
        view().showToastMsg(R.string.text_login_success);
        // Go to HomeActivity
        view().gotoActivity(HomeActivity.getCallIntent(mContext));
    }

    public LoginActivityPresenter(ILoginActivityView view, DaoSession daoSession, Context context) {
        mContext = context;
        setModel(daoSession);
        bindView(view);
    }

    public void init2show(Intent intent) {
        if (intent != null) {
            userName = intent.getStringExtra(AppContants.Account.USER_NAME);
        }
        if (userName != null) {
            view().initShow(userName);
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
            this.pwd = MD5.getMessageDigest(pwd);
            HttpManager.getInstance().login(loginObserver, userName, pwd);
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
