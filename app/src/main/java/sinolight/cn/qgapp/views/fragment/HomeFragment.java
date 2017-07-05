package sinolight.cn.qgapp.views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.HomeAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.data.bean.LocalDataBean;
import sinolight.cn.qgapp.presenter.HomeFragmentPresenter;
import sinolight.cn.qgapp.utils.MyItemDivider;
import sinolight.cn.qgapp.views.view.IHomeFragmentView;

/**
 * Created by xns on 2017/6/29.
 * 首页Fragment
 */

public class HomeFragment extends BaseFragment implements IHomeFragmentView {
    private static final String TAG = "HomeFragment";
    @Inject
    List<LocalDataBean> storeDatas;
    @Inject
    Context mContext;
    @Inject
    HomeFragmentPresenter mPresenter;
    @BindView(R.id.rv_hf)
    RecyclerView mRvHf;
    Unbinder unbinder;

    private RecyclerView.LayoutManager mLayoutManager;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.bindView(this);
        mPresenter.initData();
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
    public void showView(HomeAdapter adapter) {
        mLayoutManager = new GridLayoutManager(mContext, 3);
        mRvHf.setLayoutManager(mLayoutManager);
        mRvHf.setHasFixedSize(true);
        mRvHf.addItemDecoration(new MyItemDivider(mContext));
        mRvHf.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_hf_search)
    public void onViewClicked() {
    }
}
