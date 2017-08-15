package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.dagger.component.DaggerSysActivityComponent;
import sinolight.cn.qgapp.dagger.module.SysActivityModule;
import sinolight.cn.qgapp.presenter.SystemActivityPresenter;
import sinolight.cn.qgapp.views.view.ISystemActivityView;

/**
 * Created by xns on 2017/8/15.
 * System Activity
 */

public class SystemActivity extends BaseActivity implements ISystemActivityView {
    @Inject
    Context mContext;
    @Inject
    SystemActivityPresenter mPresenter;
    @BindView(R2.id.tool_bar_my_database)
    Toolbar mToolBarMyDatabase;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, SystemActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_system;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerSysActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .sysActivityModule(new SysActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_sys_back, R.id.root_sys_about, R.id.root_sys_clear, R.id.root_sys_update, R.id.btn_sys_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_sys_back:
                finish();
                break;
            case R.id.root_sys_about:
                mPresenter.gotoAboutActivity();
                break;
            case R.id.root_sys_clear:
                break;
            case R.id.root_sys_update:
                break;
            case R.id.btn_sys_quit:
                break;
        }
    }

    @Override
    public void gotoActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showToast(int msgID) {
        String msg = getString(msgID);
        this.showToast(msg);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
