package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.utils.L;

/**
 * Created by xns on 2017/7/10.
 * 行业库详情界面
 */

public class DBaseDetailActivity extends BaseActivity {
    private static final String TAG = "DBaseDetailActivity";
    @Inject
    Context mContext;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, DBaseDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(TAG, "context:" + mContext);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_db_detail;
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
