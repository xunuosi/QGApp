package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.adapter.MyDatabaseAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerMyDataBaseActivityComponent;
import sinolight.cn.qgapp.dagger.module.MyDataBaseActivityModule;
import sinolight.cn.qgapp.presenter.MyDatabaseActivityPresenter;
import sinolight.cn.qgapp.views.view.IMyDatabaseActivityView;

/**
 * Created by xns on 2017/8/14.
 * 我的资源库
 */

public class MyDatabaseActivity extends BaseActivity implements IMyDatabaseActivityView, OnRefreshListener {
    @Inject
    Context mContext;
    @Inject
    MyDatabaseActivityPresenter mPresenter;
    @BindView(R2.id.tool_bar_my_database)
    Toolbar mToolBarMyDatabase;
    @BindView(R2.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R2.id.swipe_my_database)
    SwipeToLoadLayout mSwipe;

    private LinearLayoutManager mLayoutManager;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, MyDatabaseActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_database;
    }

    @Override
    protected void initViews() {
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.addItemDecoration(new DividerItemDecoration(MyDatabaseActivity.this, DividerItemDecoration.VERTICAL));

        mSwipe.setOnRefreshListener(this);
        mSwipe.setRefreshing(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerMyDataBaseActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .myDataBaseActivityModule(new MyDataBaseActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.iv_kf_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showToast(int msgID) {
        String msg = getString(msgID);
        this.showToast(msg);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setRefreshEnable(boolean enable) {
        if (enable) {
            mSwipe.post(new Runnable() {
                @Override
                public void run() {
                    mSwipe.setRefreshing(true);
                }
            });
        } else {
            mSwipe.setRefreshing(false);
        }
    }

    @Override
    public void showListView(MyDatabaseAdapter adapter) {
        if (mSwipeTarget != null && mSwipeTarget.getAdapter() == null) {
            mSwipeTarget.setAdapter(adapter);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.init2Show();
    }
}
