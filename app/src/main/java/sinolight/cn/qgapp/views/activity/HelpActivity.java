package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.dagger.component.DaggerHelpActivityComponent;
import sinolight.cn.qgapp.dagger.module.HelpActivityModule;
import sinolight.cn.qgapp.presenter.HelpActivityPresenter;
import sinolight.cn.qgapp.views.view.IHelpActivityView;

/**
 * Created by xns on 2017/9/4.
 * Help activity
 */

public class HelpActivity extends BaseActivity implements IHelpActivityView, OnRefreshListener {

    @Inject
    Context mContext;
    @Inject
    HelpActivityPresenter mPresenter;

    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tool_bar_help)
    Toolbar mToolBarHelp;
    @BindView(R2.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R2.id.swipe_help)
    SwipeToLoadLayout mSwipe;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(mToolBarHelp);
        mTvTitle.setText(R.string.text_help);

        mSwipe.setRefreshing(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerHelpActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .helpActivityModule(new HelpActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.im_back_arrow)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showToastByRes(int strID) {
        String str = getString(strID);
        this.showToastByStr(str);
    }

    @Override
    public void showToastByStr(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefreshing(boolean enable) {
        if (mSwipe == null) {
            return;
        }
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
    public void gotoActivity(Intent callIntent) {
        startActivity(callIntent);
    }

    @Override
    public void showListView(RecyclerView.Adapter adapter) {

    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }
}
