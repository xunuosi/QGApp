package sinolight.cn.qgapp.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.views.view.ILoginActivityView;

/**
 * Created by xns on 2017/6/29.
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements ILoginActivityView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
