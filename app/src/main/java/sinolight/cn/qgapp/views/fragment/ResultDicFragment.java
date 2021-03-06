package sinolight.cn.qgapp.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.presenter.ResultDicPresenter;
import sinolight.cn.qgapp.views.view.ICollectBookFragmentView;

/**
 * Created by xns on 2017/8/14.
 * Collect Book
 */

public class ResultDicFragment extends BaseCollectFragment implements ICollectBookFragmentView,
        OnRefreshListener, OnLoadMoreListener {
    @Inject
    ResultDicPresenter mPresenter;
    @BindView(R2.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R2.id.swipe_collect)
    SwipeToLoadLayout mSwipe;
    Unbinder unbinder;
    @BindView(R2.id.tv_collect_empty)
    TextView mTvCollectEmpty;

    private RecyclerView.LayoutManager mLayoutManager;
    private String key;
    private String dbId;

    public static ResultDicFragment newInstance(String dbId, String key) {
        ResultDicFragment fragment = new ResultDicFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppContants.Search.SEARCH_DB_ID, dbId);
        bundle.putString(AppContants.Search.SEARCH_KEY, key);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            dbId = bundle.getString(AppContants.Search.SEARCH_DB_ID);
            key = bundle.getString(AppContants.Search.SEARCH_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
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

        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mSwipe.setOnRefreshListener(this);
        mSwipe.setOnLoadMoreListener(this);

        mSwipe.setRefreshing(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshView(dbId, key);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getActivity().getString(msgId);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setHideEmpty(boolean enable) {
        if (enable) {
            mTvCollectEmpty.setVisibility(View.GONE);
        } else {
            mTvCollectEmpty.setVisibility(View.VISIBLE);
        }
    }
}
