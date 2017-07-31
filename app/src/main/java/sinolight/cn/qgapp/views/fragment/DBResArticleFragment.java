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
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.presenter.DBResArticlePresenter;
import sinolight.cn.qgapp.views.view.IDBResArticleFragmentView;

/**
 * Created by xns on 2017/7/26.
 * 热门素材Fragment
 */

public class DBResArticleFragment extends BaseFragment implements IDBResArticleFragmentView, OnRefreshListener {
    @Inject
    DBResArticlePresenter mPresenter;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_db_res_article)
    SwipeToLoadLayout mSwipeDbResArticle;
    Unbinder unbinder;

    private RecyclerView.LayoutManager mLayoutManager;

    public static DBResArticleFragment newInstance() {
        return new DBResArticleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_res_db_articlel, container, false);

        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
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
        initView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.addItemDecoration(new LinearDivider(getContext()));

        this.showRefreshing(true);
        mSwipeDbResArticle.setOnRefreshListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected UserComponent getComponent() {
        return ((HasComponent<UserComponent>) getActivity()).getComponent();
    }

    @Override
    public void showErrorToast(int msgId) {
        String msg = getString(msgId);
        showToastMessage(msg);
    }

    @Override
    public void init2Show(CommonTitleAdapter adapter) {
        if (mSwipeTarget.getAdapter() == null) {
            mSwipeTarget.setAdapter(adapter);
        }
    }

    @Override
    public void gotoActivity(Intent callIntent) {

    }

    @Override
    public void showRefreshing(boolean enable) {
        if (enable) {
            mSwipeDbResArticle.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeDbResArticle.setRefreshing(true);
                }
            });
        } else {
            mSwipeDbResArticle.setRefreshing(false);
        }
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
