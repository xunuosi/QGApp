package sinolight.cn.qgapp.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.presenter.DBResVideoPresenter;
import sinolight.cn.qgapp.views.view.IDBResVideoFragmentView;

/**
 * Created by xns on 2017/7/26.
 * 资源库视频Fragment
 */

public class DBResVideoFragment extends BaseFragment implements IDBResVideoFragmentView, OnRefreshListener {
    @Inject
    DBResVideoPresenter mPresenter;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_db_res_video)
    SwipeToLoadLayout mSwipeDbResVideo;
    Unbinder unbinder;

    private LinearLayoutManager mLayoutManager;

    public static DBResVideoFragment newInstance() {
        return new DBResVideoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_res_db_video, container, false);

        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent().inject(this);
        mPresenter.bindView(this);
        initView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mSwipeDbResVideo.setOnRefreshListener(this);
        this.showRefreshing(true);
    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void onRefresh() {
        mPresenter.init2Show();
    }

    @Override
    public void showErrorToast(int msgId) {
        showToastMessage(getString(msgId));
    }

    @Override
    public void init2Show(CommonTitleAdapter adapter) {
        if (mSwipeTarget != null && mSwipeTarget.getAdapter() == null) {
            mSwipeTarget.setAdapter(adapter);
        }
    }

    @Override
    public void gotoActivity(Intent callIntent) {

    }

    @Override
    public void showRefreshing(boolean enable) {
        if (mSwipeDbResVideo != null) {
            if (enable) {
                mSwipeDbResVideo.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeDbResVideo.setRefreshing(true);
                    }
                });
            } else {
                mSwipeDbResVideo.setRefreshing(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
