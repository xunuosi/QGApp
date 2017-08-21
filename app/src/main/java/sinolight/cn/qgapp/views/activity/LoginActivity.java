package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
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
import sinolight.cn.qgapp.utils.ActivityCollector;
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

    private boolean mIsExit;


    public static Intent getCallIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        processExtraData(null);
    }

    private void processExtraData(Intent intent) {
        mPresenter.init2show(intent);
    }

    @Override
    protected void initializeInjector() {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                ActivityCollector.closeApp();
            } else {
                this.showToastMsg(R.string.attention_exit_app);
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        // must store the new intent unless getIntent() will return the old one
//        setIntent(intent);
        processExtraData(intent);
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
        // focus pw
        mEtLoginPw.requestFocus();
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

    @Override
    public void gotoActivity(Intent callIntent) {
        startActivity(callIntent);
    }
}
