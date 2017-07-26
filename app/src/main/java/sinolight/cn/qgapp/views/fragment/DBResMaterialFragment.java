package sinolight.cn.qgapp.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.presenter.DBResMaterialPresenter;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IDBResMaterialFragmentView;

/**
 * Created by xns on 2017/7/26.
 * 热门素材Fragment
 */

public class DBResMaterialFragment extends BaseLazyLoadFragment implements IDBResMaterialFragmentView {
    private static final String TAG = "DBResMaterialFragment";
    @Inject
    DBResMaterialPresenter mPresenter;
    @BindView(R.id.rv_dbres_material)
    RecyclerView mRvDbresMaterial;
    Unbinder unbinder;
    private LinearLayoutManager mLayoutManager;

    public static DBResMaterialFragment newInstance() {
        return new DBResMaterialFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d(TAG,"onCreateView");
        final View fragmentView = inflater.inflate(R.layout.fragment_res_db_material, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.d(TAG,"onViewCreated");
        this.getComponent().inject(this);
        mPresenter.bindView(this);
        initView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRvDbresMaterial.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        L.d(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d(TAG,"onResume");

    }

    @Override
    protected void lazyLoad() {
        L.d(TAG, "lazyLoad");
        if (!isVisible) {
            return;
        }
        mPresenter.init2Show();
    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.clear();
    }

    @Override
    public void showErrorToast(int msgId) {

    }

    @Override
    public void init2Show(CommonTitleAdapter adapter) {
        mRvDbresMaterial.setAdapter(adapter);
    }

    @Override
    public void gotoActivity(Intent callIntent) {

    }
}
