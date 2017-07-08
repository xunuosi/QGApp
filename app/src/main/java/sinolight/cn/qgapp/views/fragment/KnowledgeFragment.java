package sinolight.cn.qgapp.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.presenter.KnowledgePresenter;
import sinolight.cn.qgapp.views.view.IKnowledgeFragmentView;

/**
 * Created by xns on 2017/6/29.
 * 知识库Fragment
 */

public class KnowledgeFragment extends BaseFragment implements IKnowledgeFragmentView {
    @Inject
    KnowledgePresenter mPresenter;

    public static KnowledgeFragment newInstance() {
        return new KnowledgeFragment();
    }

    public KnowledgeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_knowledge, container, false);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getComponent().inject(this);
        mPresenter.bindView(this);
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
    public void showLoading(boolean enable) {

    }
}
