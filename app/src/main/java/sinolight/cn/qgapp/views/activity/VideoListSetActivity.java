package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.VideoAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerVideoSetActivityComponent;
import sinolight.cn.qgapp.dagger.module.VideoSetActivityModule;
import sinolight.cn.qgapp.presenter.VideoListSetActivityPresenter;
import sinolight.cn.qgapp.views.view.IVideoListSetActivityView;

/**
 * Created by xns on 2017/8/1.
 * 视频列表的Activity
 */

public class VideoListSetActivity extends BaseActivity implements IVideoListSetActivityView,
        OnRefreshListener, OnLoadMoreListener {
    private static final String TAG = "VideoListSetActivity";

    @Inject
    Context mContext;
    @Inject
    VideoListSetActivityPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.tool_bar_video_list_set)
    Toolbar mToolBarVideoListSet;
    @BindView(R.id.et_video_list_set_search)
    EditText mEtVideoListSetSearch;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_video_list_set)
    SwipeToLoadLayout mSwipeVideoListSet;

    private LinearLayoutManager mLayoutManager;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, VideoListSetActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_video_list_set;
    }

    @Override
    protected void initViews() {
        mTvTitle.setText(R.string.text_video_set);
        mIvMenu.setVisibility(View.GONE);

        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.addItemDecoration(new LinearDivider(mContext));

        mSwipeVideoListSet.setOnRefreshListener(VideoListSetActivity.this);
        mSwipeVideoListSet.setOnLoadMoreListener(VideoListSetActivity.this);

        this.showRefreshing(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerVideoSetActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .videoSetActivityModule(new VideoSetActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_video_list_set_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_video_list_set_search:
                searchData();
                break;
        }
    }

    private void searchData() {
        if (TextUtils.isEmpty(mEtVideoListSetSearch.getText().toString().trim())) {
            showToast(R.string.text_search_data_empty);
            return;
        } else {
            mPresenter.loadDataWithPara(
                    mEtVideoListSetSearch.getText().toString().trim(),
                    false,
                    true
            );
        }
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore(mEtVideoListSetSearch.getText().toString().trim());
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListView(VideoAdapter adapter) {
        if (mSwipeTarget.getAdapter() == null) {
            mSwipeTarget.setAdapter(adapter);
        }
    }


    @Override
    public void showRefreshing(boolean enable) {
        if (enable) {
            mSwipeVideoListSet.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeVideoListSet.setRefreshing(true);
                }
            });
        } else {
            mSwipeVideoListSet.setRefreshing(false);
        }
    }

    @Override
    public void showLoadMoreing(boolean enable) {
        if (!mSwipeVideoListSet.isLoadMoreEnabled()) {
            return;
        }
        if (enable) {
            mSwipeVideoListSet.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeVideoListSet.setLoadingMore(true);
                }
            });
        } else {
            mSwipeVideoListSet.setLoadingMore(false);
        }
    }

    @Override
    public void hasMoreData(boolean hasMore) {
        if (mSwipeVideoListSet.isLoadingMore()) {
            showLoadMoreing(false);
        }
        mSwipeVideoListSet.setLoadMoreEnabled(hasMore);
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshView();
    }

    private class LinearDivider extends Y_DividerItemDecoration {
        private Context context;

        public LinearDivider(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public Y_Divider getDivider(int itemPosition) {
            Y_Divider divider = null;
            divider = new Y_DividerBuilder()
                    .setLeftSideLine(false, ContextCompat.getColor(context, R.color.color_transparent_all), 0.5f, 0, 0)
                    .setBottomSideLine(true, ContextCompat.getColor(context, R.color.color_bottom_divider), 0.5f, 0, 0)
                    .setRightSideLine(false, ContextCompat.getColor(context, R.color.color_transparent_all), 0.5f, 0, 0)
                    .setTopSideLine(false, ContextCompat.getColor(context, R.color.color_transparent_all), 0.5f, 0, 0)
                    .create();
            return divider;
        }
    }
}
