package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerPicSetActivityComponent;
import sinolight.cn.qgapp.dagger.module.PicSetActivityModule;
import sinolight.cn.qgapp.presenter.PicSetActivityPresenter;
import sinolight.cn.qgapp.views.view.IPicSetActivityView;

/**
 * Created by xns on 2017/8/11.
 * 资源库图集列表界面
 */

public class PicSetActivity extends BaseActivity implements IPicSetActivityView,
        OnRefreshListener, OnLoadMoreListener {

    @Inject
    Context mContext;
    @Inject
    PicSetActivityPresenter mPresenter;
    @BindView(R.id.et_db_detail_search)
    EditText mEtDbDetailSearch;
    @BindView(R.id.tool_bar_pic_set)
    Toolbar mToolBarPicSet;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_pic_set)
    SwipeToLoadLayout mSwipe;

    private RecyclerView.LayoutManager mLayoutManager;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, PicSetActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_pic_set;
    }

    @Override
    protected void initViews() {
        mLayoutManager = new LinearLayoutManager(mContext);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.addItemDecoration(new DividerItemDecoration(PicSetActivity.this, DividerItemDecoration.VERTICAL));

        mSwipe.setOnRefreshListener(this);
        mSwipe.setOnLoadMoreListener(this);

        mSwipe.setRefreshing(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerPicSetActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .picSetActivityModule(new PicSetActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        this.showStrToast(msg);
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListView(CommonTitleAdapter adapter) {
        if (mSwipeTarget != null && mSwipeTarget.getAdapter() == null) {
            mSwipeTarget.setAdapter(adapter);
        }
    }

    @Override
    public void showRefreshing(boolean enable) {
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
    public void showLoadMoreing(boolean enable) {
        if (!mSwipe.isLoadMoreEnabled()) {
            return;
        }
        if (enable) {
            mSwipe.post(new Runnable() {
                @Override
                public void run() {
                    mSwipe.setLoadingMore(true);
                }
            });
        } else {
            mSwipe.setLoadingMore(false);
        }
    }

    @Override
    public void hasMoreData(boolean hasMore) {
        if (mSwipeTarget != null) {
            if (mSwipe.isLoadingMore()) {
                showLoadMoreing(false);
            }
            mSwipe.setLoadMoreEnabled(hasMore);
        }
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore(mEtDbDetailSearch.getText().toString().trim(), null);
    }

    @Override
    public void onRefresh() {
        mEtDbDetailSearch.setText(null);
        mPresenter.refreshView();
    }

    @OnClick({R.id.im_back_arrow_search, R.id.iv_db_detail_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow_search:
                finish();
                break;
            case R.id.iv_db_detail_search:
                mPresenter.searchData(
                        mEtDbDetailSearch.getText().toString().trim(),
                        null
                );
                break;
        }
    }
}
