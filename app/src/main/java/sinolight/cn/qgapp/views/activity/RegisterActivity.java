package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerRegisterActivityComponent;
import sinolight.cn.qgapp.dagger.module.RegisterActivityModule;
import sinolight.cn.qgapp.presenter.RegisterActivityPresenter;
import sinolight.cn.qgapp.utils.ToastUtil;
import sinolight.cn.qgapp.utils.VCodeUtil;
import sinolight.cn.qgapp.views.view.IRegisterActivityView;

/**
 * Created by xns on 2017/6/30.
 * 注册界面
 */

public class RegisterActivity extends BaseActivity implements IRegisterActivityView {
    @Inject
    Context mContext;
    @Inject
    RegisterActivityPresenter mPresenter;
    @Inject
    ToastUtil mToast;
    @BindView(R.id.et_register_user)
    EditText mEtRegisterUser;
    @BindView(R.id.et_email)
    EditText mEtEmail;
    @BindView(R.id.et_pw)
    EditText mEtPw;
    @BindView(R.id.et_repw)
    EditText mEtRepw;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.iv_register_code)
    ImageView mIvRegisterCode;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R.id.btn_register_reg)
    Button mBtnRegisterReg;

    private WeakReference<Bitmap> codeBitmap;


    public static Intent getCallIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializeInjector() {
        DaggerRegisterActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .registerActivityModule(new RegisterActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        mPresenter.init2show();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public void initShow(String vCode) {
        if (vCode != null) {
            codeBitmap = new WeakReference<>(VCodeUtil.createSecurityCodeBitmap(135, 35, 16, 1.5f, vCode));
            mIvRegisterCode.setImageBitmap(codeBitmap.get());
        }
        mIvRegisterCode.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_error_red_24dp));

    }

    @Override
    public void showToastMsg(int msgId) {
        String msg = getString(msgId);
        mToast.showToast(msg);
    }

    @Override
    public void gotoActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showLoading(boolean enable) {
        if (enable) {
            mLoadingRoot.setVisibility(View.VISIBLE);
        } else {
            mLoadingRoot.setVisibility(View.GONE);
        }
        mBtnRegisterReg.setEnabled(!enable);
    }

    @OnClick({R.id.iv_register_code, R.id.btn_register_reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_register_code:
                mPresenter.refresh2show();
                break;
            case R.id.btn_register_reg:
                mPresenter.registerAccount(mEtRegisterUser.getText().toString().trim(),
                        mEtEmail.getText().toString().trim(),
                        mEtPw.getText().toString().trim(),
                        mEtRepw.getText().toString().trim(),
                        mEtCode.getText().toString().trim());
                break;
        }
    }

}
