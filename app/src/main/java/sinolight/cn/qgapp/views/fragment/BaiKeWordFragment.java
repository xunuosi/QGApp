package sinolight.cn.qgapp.views.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.data.bean.EventAction;
import sinolight.cn.qgapp.presenter.BaiKeWordFragmentPresenter;
import sinolight.cn.qgapp.views.activity.BaiKeActivity;
import sinolight.cn.qgapp.views.view.IBaiKeFragmentView;

/**
 * Created by xns on 2017/7/24.
 * 百科数据库界面
 */

public class BaiKeWordFragment extends BaseLazyLoadFragment implements IBaiKeFragmentView,
        OnRefreshListener, OnLoadMoreListener {
    private static final String TAG = "BaiKeWordFragment";

    @Inject
    BaiKeWordFragmentPresenter mPresenter;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_baike_word)
    SwipeToLoadLayout mSwipe;
    @BindView(R.id.tv_count_baike_word)
    TextView mTvCountBaikeWord;
    Unbinder unbinder;

    private RecyclerView.LayoutManager mLayoutManager;
    private String key;

    public static BaiKeWordFragment newInstance() {
        BaiKeWordFragment fragment = new BaiKeWordFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mPresenter.clear();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void searchData(EventAction action) {
        switch (action) {
            case ACTION_SEARCH_WORD:
                key = ((BaiKeActivity) getActivity()).getKey();
                mPresenter.searchData(
                        key,
                        null
                );
                break;
        }
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
        initWordView();

        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mSwipe.setOnRefreshListener(this);
        mSwipe.setOnLoadMoreListener(this);

        mSwipe.setRefreshing(true);
    }

    private void initWordView() {
        mTvCountBaikeWord.setVisibility(View.VISIBLE);
    }

    private String formatStr(int baseStrId, String child) {
        String local = getString(baseStrId);
        return String.format(local, child);
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
        String msg = getString(msgId);
        this.showToastMessage(msg);
    }

    @Override
    public void showListView(KDBResAdapter adapter) {
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
        if (mSwipe.isLoadingMore()) {
            showLoadMoreing(false);
        }
        mSwipe.setLoadMoreEnabled(hasMore);
    }

    @Override
    public void showFooterView(boolean enable, String msg) {
        if (enable) {
            mTvCountBaikeWord.setText(formatStr(R.string.text_word_count, msg));
            mTvCountBaikeWord.setVisibility(View.VISIBLE);
        } else {
            mTvCountBaikeWord.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore(key, null);
    }

    @Override
    public void onRefresh() {
        EventBus.getDefault().post(EventAction.ACTION_RESET);
        key = null;
        mPresenter.refreshView();
    }

}
