package sinolight.cn.qgapp.views.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.data.bean.EventAction;
import sinolight.cn.qgapp.presenter.BaiKeAnaFragmentPresenter;
import sinolight.cn.qgapp.views.view.IBaiKeFragmentView;

/**
 * Created by xns on 2017/7/24.
 * 百科数据库界面
 */

public class BaiKeAnalysisFragment extends BaseLazyLoadFragment implements IBaiKeFragmentView,
        OnRefreshListener, OnLoadMoreListener {
    private static final String TAG = "BaiKeAnalysisFragment";

    @Inject
    BaiKeAnaFragmentPresenter mPresenter;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_baike_word)
    SwipeToLoadLayout mSwipe;
    @BindView(R.id.tv_count_baike_word)
    TextView mTvCountBaikeWord;
    Unbinder unbinder;

    private RecyclerView.LayoutManager mLayoutManager;

    public static BaiKeAnalysisFragment newInstance() {
        BaiKeAnalysisFragment fragment = new BaiKeAnalysisFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baike, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent().inject(this);
        mPresenter.bindView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAnalysisView();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.addItemDecoration(new LinearDivider(getActivity()));

        mSwipe.setOnRefreshListener(this);
        mSwipe.setOnLoadMoreListener(this);

        mSwipe.setRefreshing(true);
    }

    private void initAnalysisView() {
        mTvCountBaikeWord.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void showToast(int msgId) {

    }

    @Override
    public void showListView(KDBResAdapter adapter) {
        if (mSwipeTarget != null && mSwipeTarget.getAdapter() == null) {
            mSwipeTarget.setAdapter(adapter);
        }
    }

    @Override
    public void gotoActivity(Intent callIntent) {

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
        if (mSwipe.isLoadingMore()) {
            showLoadMoreing(false);
        }
        mSwipe.setLoadMoreEnabled(hasMore);
    }

    @Override
    public void showFooterView(boolean enable, String msg) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {
        EventBus.getDefault().post(EventAction.ACTION_RESET);
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
