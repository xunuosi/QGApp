package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.DaggerUserComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.dagger.module.UserModule;

/**
 * Created by xns on 2017/7/25.
 * 个人主页界面
 */

public class UserHomeActivity extends BaseActivity implements HasComponent<UserComponent> {
    private UserComponent userComponent;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, UserHomeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_user_home;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule())
                .build();
    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }
}
