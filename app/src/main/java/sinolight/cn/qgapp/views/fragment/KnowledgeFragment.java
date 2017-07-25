package sinolight.cn.qgapp.views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KnowledgeAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.presenter.KnowledgePresenter;
import sinolight.cn.qgapp.views.view.IKnowledgeFragmentView;

/**
 * Created by xns on 2017/6/29.
 * 知识库Fragment
 */

public class KnowledgeFragment extends BaseFragment implements IKnowledgeFragmentView, OnRefreshListener {
    @Inject
    KnowledgePresenter mPresenter;
    @Inject
    Context mContext;
    @BindView(R.id.tool_bar_kf)
    Toolbar mToolBarKf;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_kf)
    SwipeToLoadLayout mSwipeKf;
    Unbinder unbinder;

    private RecyclerView.LayoutManager mLayoutManager;

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
        // 添加Swipe监听
        mSwipeKf.setOnRefreshListener(this);
        mSwipeKf.setRefreshing(true);

        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.addItemDecoration(new KnowledgeDivider(mContext));
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
        if (enable) {
            mSwipeKf.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeKf.setRefreshing(true);
                }
            });
        } else {
            mSwipeKf.setRefreshing(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSwipeKf.isRefreshing()) {
            mSwipeKf.setRefreshing(false);
        }
    }

    @Override
    public void showView(KnowledgeAdapter knowledgeAdapter) {
        mSwipeTarget.setAdapter(knowledgeAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_kf_back)
    public void onViewClicked() {
        getActivity().onBackPressed();
    }

    @Override
    public void onRefresh() {
        mPresenter.init2Show();
    }

    private class KnowledgeDivider extends Y_DividerItemDecoration {
        private Context context;

        public KnowledgeDivider(Context context) {
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
