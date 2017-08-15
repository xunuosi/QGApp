package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/8/15.
 * System Activity
 */

public class SystemActivity extends BaseActivity {

    public static Intent getCallIntent(Context context) {
        return new Intent(context, SystemActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

    }
}
