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
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.presenter.DBResArticleDetailPresenter;
import sinolight.cn.qgapp.presenter.DBResArticlePresenter;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IDBResArticleDetailView;
import sinolight.cn.qgapp.views.view.IDBResArticleFragmentView;
import sinolight.cn.qgapp.views.widget.popmenu.TopRightMenu;

/**
 * Created by xns on 2017/7/26.
 * 热门素材Fragment
 */

public class DBResArticleDetailFragment extends BaseFragment implements IDBResArticleDetailView, TreeNode.TreeNodeClickListener,
        PopupWindow.OnDismissListener, OnRefreshListener, OnLoadMoreListener {

    DBResArticleDetailPresenter mPresenter;

    RecyclerView mSwipeTarget;
    SwipeToLoadLayout mSwipeDbResArticle;
    Unbinder unbinder;
    // TreeMenu
    private TopRightMenu mTopRightMenu;
    private AndroidTreeView tView;

    private RecyclerView.LayoutManager mLayoutManager;
    private String themeType;
    private String searchData;

    public static DBResArticleDetailFragment newInstance() {
        return new DBResArticleDetailFragment();
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

        mSwipeDbResArticle.setRefreshing(true);
        mSwipeDbResArticle.setOnRefreshListener(this);
        mSwipeDbResArticle.setOnLoadMoreListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.init2Show();
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
    public void init2Show(KDBResAdapter adapter) {
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
    public void showLoadMoreing(boolean enable) {
        if (!mSwipeDbResArticle.isLoadMoreEnabled()) {
            return;
        }
        if (enable) {
            mSwipeDbResArticle.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeDbResArticle.setLoadingMore(true);
                }
            });
        } else {
            mSwipeDbResArticle.setLoadingMore(false);
        }
    }

    @Override
    public void hasMoreData(boolean hasMore) {
        if (mSwipeDbResArticle.isLoadingMore()) {
            showLoadMoreing(false);
        }
        mSwipeDbResArticle.setLoadMoreEnabled(hasMore);
    }

    public void popTreeMenu(View target) {
        TreeNode treeRoot = mPresenter.popTreeMenu();
        if (treeRoot != null) {
            if (tView == null) {
                tView = new AndroidTreeView(getActivity(), treeRoot);
//                tView.setDefaultAnimation(true);
//                tView.setUse2dScroll(true);
                tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
                tView.setDefaultViewHolder(TreeParentHolder.class);
                tView.setDefaultNodeClickListener(this);
                tView.setUseAutoToggle(true);
            }
            if (mTopRightMenu == null) {
                mTopRightMenu = new TopRightMenu(getActivity(), tView.getView());
                mTopRightMenu
                        .setHeight(850)     //默认高度480
                        .setWidth(450)      //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true
                        .dimBackground(true)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .showAsDropDown(target, -225, 0);
            } else {
                mTopRightMenu.showAsDropDown(target, -225, 0);
            }
            if (mTopRightMenu.getPopView() != null) {
                mTopRightMenu.getPopView().setOnDismissListener(this);
            }
        } else {
            showToastMessage(getString(R.string.text_tree_menu_loading));
        }
    }

    @Override
    public void onDismiss() {
        // recover window Alpha
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 1.0f;
        getActivity().getWindow().setAttributes(lp);
        // Request themeType Data
        mPresenter.loadDataWithPara(searchData, themeType, false);
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        TreeParentHolder.IconTreeItem item = (TreeParentHolder.IconTreeItem) value;
        themeType = item.id;
        Toast.makeText(App.getContext(), "你选择了:" + item.name + "分类", Toast.LENGTH_SHORT).show();
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
    public void onLoadMore() {
        // 正在加载数据时禁止加载更多数据
        mPresenter.loadMore(searchData, themeType);
    }

    @Override
    public void onRefresh() {
        // Refresh data clear all of condition
//        mEtDbDetailSearch.setText(null);
        themeType = null;
        searchData = null;
        mPresenter.refreshView();
    }

    /**
     * Get Parent Fragment transfer searchData
     * @param searchData
     */
    public void transferSearchData(String searchData) {
        this.searchData = searchData;
        mPresenter.loadDataWithPara(this.searchData, themeType, false);
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
