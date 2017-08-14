package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.adapter.MyDatabaseAdapter;
import sinolight.cn.qgapp.views.view.IMyDatabaseActivityView;

/**
 * Created by xns on 2017/8/14.
 * 我的资源库
 */

public class MyDatabaseActivity extends BaseActivity implements IMyDatabaseActivityView {

    @BindView(R2.id.tool_bar_my_database)
    Toolbar mToolBarMyDatabase;
    @BindView(R2.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R2.id.swipe_my_database)
    SwipeToLoadLayout mSwipeMyDatabase;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, MyDatabaseActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_database;
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

    @OnClick(R.id.iv_kf_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showToast(int msgID) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void setRefreshEnable(boolean enable) {

    }

    @Override
    public void showListView(MyDatabaseAdapter adapter) {

    }
}
