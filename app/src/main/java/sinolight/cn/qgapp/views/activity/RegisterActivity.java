package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


import javax.inject.Inject;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerRegisterActivityComponent;
import sinolight.cn.qgapp.dagger.module.RegisterActivityModule;
import sinolight.cn.qgapp.presenter.RegisterActivityPresenter;
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




    public static Intent getCallIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    private void initializeInjector() {
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
        mPresenter.getVCode();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }
}
