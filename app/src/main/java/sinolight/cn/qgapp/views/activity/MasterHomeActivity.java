package sinolight.cn.qgapp.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/8/8.
 * 专家首页
 */

public class MasterHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_master_home;
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
