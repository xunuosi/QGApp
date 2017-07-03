package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.db.DaoSession;
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
    private String token;

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
    }

    public void login(String userName, String pwd) {
        if (checkData(userName, pwd)) {

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
