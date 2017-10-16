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
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerChapterActivityComponent;
import sinolight.cn.qgapp.dagger.module.ChapterActivityModule;
import sinolight.cn.qgapp.presenter.ChapterActivityPresenter;
import sinolight.cn.qgapp.views.view.IChapterActivityView;

/**
 * Created by xns on 2017/8/3.
 * 章节界面
 */

public class ChapterActivity extends BaseActivity implements IChapterActivityView,
        OnRefreshListener, OnLoadMoreListener {
    @Inject
    Context context;
    @Inject
    ChapterActivityPresenter mPresenter;
    @BindView(R.id.tv_chapter_title)
    TextView mTvChapterTitle;
    @BindView(R.id.tool_bar_chapter)
    Toolbar mToolBarChapter;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_chapter_list_set)
    SwipeToLoadLayout mSwipe;
    private LinearLayoutManager mLayoutManager;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, ChapterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.checkIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_chapter;
    }

    @Override
    protected void initViews() {
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.addItemDecoration(new DividerItemDecoration(ChapterActivity.this, DividerItemDecoration.VERTICAL));
        mSwipeTarget.setHasFixedSize(true);

        mSwipe.setOnRefreshListener(ChapterActivity.this);
        mSwipe.setOnLoadMoreListener(ChapterActivity.this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerChapterActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .chapterActivityModule(new ChapterActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        this.showToastStr(msg);
    }

    @Override
    public void showListView(KDBResAdapter adapter, String title) {
        if (mSwipeTarget != null && mSwipeTarget.getAdapter() == null) {
            mSwipeTarget.setAdapter(adapter);
            mTvChapterTitle.setText(title);
        }
    }

    @Override
    public void showToastStr(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
        if (mSwipe == null) {
            return;
        }
        if (mSwipe.isLoadingMore()) {
            showLoadMoreing(false);
        }
        mSwipe.setLoadMoreEnabled(hasMore);
    }

    @OnClick(R.id.iv_chapter_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshData();
    }

}
