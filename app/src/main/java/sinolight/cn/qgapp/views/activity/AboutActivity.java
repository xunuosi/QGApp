package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;

import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/8/15.
 * 关于我们界面
 */

public class AboutActivity extends BaseActivity {

    public static Intent getCallIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_about;
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
