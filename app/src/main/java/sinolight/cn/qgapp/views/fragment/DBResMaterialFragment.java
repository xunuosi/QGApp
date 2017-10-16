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
import sinolight.cn.qgapp.presenter.DBResMaterialPresenter;
import sinolight.cn.qgapp.views.view.IDBResMaterialFragmentView;

/**
 * Created by xns on 2017/7/26.
 * 热门素材Fragment
 */

public class DBResMaterialFragment extends BaseLazyLoadFragment implements IDBResMaterialFragmentView, OnRefreshListener {
    private static final String TAG = "DBResMaterialFragment";
    @Inject
    DBResMaterialPresenter mPresenter;
    @BindView(R.id.swipe_target)
    RecyclerView mRvDbresMaterial;
    Unbinder unbinder;
    @BindView(R.id.swipe_db_res_material)
    SwipeToLoadLayout mSwipeDbResMaterial;
    private LinearLayoutManager mLayoutManager;

    public static DBResMaterialFragment newInstance() {
        return new DBResMaterialFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_res_db_material, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent().inject(this);
        mPresenter.bindView(this);
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRvDbresMaterial.setHasFixedSize(true);
        mRvDbresMaterial.setLayoutManager(mLayoutManager);
        mRvDbresMaterial.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mSwipeDbResMaterial.setOnRefreshListener(this);
        this.showRefreshing(true);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void lazyLoad() {
        if (!isVisible) {
            return;
        }
    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public void showErrorToast(int msgId) {
        showToastMessage(getString(msgId));
    }

    @Override
    public void init2Show(CommonTitleAdapter adapter) {
        if (mRvDbresMaterial != null && mRvDbresMaterial.getAdapter() == null) {
            mRvDbresMaterial.setAdapter(adapter);
        }
    }

    @Override
    public void gotoActivity(Intent callIntent) {

    }

    @Override
    public void showRefreshing(boolean enable) {
        if (mSwipeDbResMaterial != null) {
            if (enable) {
                mSwipeDbResMaterial.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeDbResMaterial.setRefreshing(true);
                    }
                });
            } else {
                mSwipeDbResMaterial.setRefreshing(false);
            }
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.init2Show();
    }
}
