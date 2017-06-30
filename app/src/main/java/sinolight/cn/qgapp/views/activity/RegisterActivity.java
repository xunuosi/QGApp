package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/6/30.
 * 注册界面
 */

public class RegisterActivity extends BaseActivity {

    public static Intent getCallIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }
}
