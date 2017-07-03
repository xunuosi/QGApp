package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerLoginActivityComponent;
import sinolight.cn.qgapp.dagger.module.LoginActivityModule;
import sinolight.cn.qgapp.presenter.LoginActivityPresenter;
import sinolight.cn.qgapp.utils.ToastUtil;
import sinolight.cn.qgapp.views.view.ILoginActivityView;

/**
 * Created by xns on 2017/6/29.
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements ILoginActivityView {
    @Inject
    Context mContext;
    @Inject
    LoginActivityPresenter mPresenter;
    @Inject
    ToastUtil mToast;
    @BindView(R.id.et_login_user)
    EditText mEtLoginUser;
    @BindView(R.id.et_login_pw)
    EditText mEtLoginPw;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;


    public static Intent getCallIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    private void initializeInjector() {
        DaggerLoginActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .loginActivityModule(new LoginActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.init2show(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.login(mEtLoginUser.getText().toString().trim(),
                        mEtLoginPw.getText().toString().trim());
                break;
            case R.id.btn_register:
                startActivity(RegisterActivity.getCallIntent(App.getContext()));
                break;
        }
    }

    @Override
    public void initShow(String userName) {
        mEtLoginUser.setText(userName);
    }

    @Override
    public void showToastMsg(int msgId) {
        String msg = getString(msgId);
        mToast.showToast(msg);
    }

    @Override
    public void showLoading(boolean enable) {
        if (enable) {
            mLoadingRoot.setVisibility(View.VISIBLE);
        } else {
            mLoadingRoot.setVisibility(View.GONE);
        }
        mBtnLogin.setEnabled(!enable);
        mBtnRegister.setEnabled(!enable);
    }
}
