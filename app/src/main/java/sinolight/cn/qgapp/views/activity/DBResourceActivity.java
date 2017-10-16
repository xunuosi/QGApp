package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.github.zagum.expandicon.ExpandIconView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerDBResActivityComponent;
import sinolight.cn.qgapp.dagger.module.DBResActivityModule;
import sinolight.cn.qgapp.data.bean.CollectEvent;
import sinolight.cn.qgapp.presenter.DBResActivityPresenter;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IDBResActivityView;
import sinolight.cn.qgapp.views.widget.popmenu.TopRightMenu;

/**
 * Created by xns on 2017/7/11.
 * 行业库资源的Activity
 */

public class DBResourceActivity extends BaseActivity implements
        IDBResActivityView, TreeNode.TreeNodeClickListener, OnRefreshListener, OnLoadMoreListener,
        PopupWindow.OnDismissListener {
    private static final String TAG = "DBResourceActivity";

    private TopRightMenu mTopRightMenu;
    private AndroidTreeView tView;
    private RecyclerView.LayoutManager mLayoutManager;
    private String themeType;
    private String themeName;

    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_db_res)
    SwipeToLoadLayout mSwipeDbRes;
    @Inject
    Context mContext;
    @Inject
    DBResActivityPresenter mPresenter;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_db_detail_search)
    EditText mEtDbDetailSearch;
    @BindView(R.id.tv_count_db_res)
    TextView mTvCountDbRes;
    @BindView(R.id.db_res_root_action)
    ConstraintLayout mDbResRootAction;
    @BindView(R.id.tv_db_res_pb_time)
    TextView mTvDbResPbTime;
    @BindView(R.id.ex_icon_de_res_pb_time)
    ExpandIconView mExIconDeResPbTime;
    @BindView(R.id.tv_db_res_browse_count)
    TextView mTvDbResBrowseCount;
    @BindView(R.id.ex_icon_de_res_browse_count)
    ExpandIconView mExIconDeResBrowseCount;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, DBResourceActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mPresenter.show(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_db_res;
    }

    @Override
    protected void initViews() {

        mLayoutManager = new LinearLayoutManager(DBResourceActivity.this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.addItemDecoration(new DividerItemDecoration(DBResourceActivity.this, DividerItemDecoration.VERTICAL));

        mSwipeDbRes.setOnRefreshListener(DBResourceActivity.this);
        mSwipeDbRes.setOnLoadMoreListener(DBResourceActivity.this);

        initSortView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerDBResActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .dBResActivityModule(new DBResActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSwipeDbRes.isRefreshing()) {
            mSwipeDbRes.setRefreshing(false);
        }
        if (mSwipeDbRes.isLoadingMore()) {
            mSwipeDbRes.setLoadingMore(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_menu, R.id.iv_db_detail_search, R.id.tv_db_res_pb_time, R.id.tv_db_res_browse_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_menu:
                popTreeMenu();
                break;
            case R.id.iv_db_detail_search:
                searchData();
                break;
            case R.id.tv_db_res_pb_time:
                mPresenter.sortByAction(DBResActivityPresenter.SORT_ACTION_TIME);
                mExIconDeResBrowseCount.setFraction(0.5f, false);
                break;
            case R.id.tv_db_res_browse_count:
                mPresenter.sortByAction(DBResActivityPresenter.SORT_ACTION_BROWSE);
                mExIconDeResPbTime.setFraction(0.5f, false);
                break;
        }
    }

    private void searchData() {
        if (TextUtils.isEmpty(mEtDbDetailSearch.getText().toString().trim())) {
            showToast(R.string.text_search_data_empty);
            return;
        } else {
            mPresenter.searchData(
                    mEtDbDetailSearch.getText().toString().trim(),
                    themeType
            );
        }
    }

    @Override
    public void initShow(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void popTreeMenu() {
        TreeNode treeRoot = mPresenter.popTreeMenu();
        if (treeRoot != null) {
            if (tView == null) {
                tView = new AndroidTreeView(this, treeRoot);
//                tView.setDefaultAnimation(true);
//                tView.setUse2dScroll(true);
                tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom, true);
                tView.setDefaultViewHolder(TreeParentHolder.class);
                tView.setDefaultNodeClickListener(this);
                tView.setUseAutoToggle(true);
            }
            if (mTopRightMenu == null) {
                mTopRightMenu = new TopRightMenu(this, tView.getView());
                mTopRightMenu
                        .setHeight(850)     //默认高度480
                        .setWidth(520)      //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true
                        .dimBackground(true)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .showAsDropDown(ivMenu, -225, 0);
            } else {
                mTopRightMenu.showAsDropDown(ivMenu, -225, 0);
            }
            if (mTopRightMenu.getPopView() != null) {
                mTopRightMenu.getPopView().setOnDismissListener(this);
            }
        } else {
            showToast(R.string.text_tree_menu_loading);
        }
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        this.showStrToast(msg);
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
            mSwipeDbRes.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeDbRes.setRefreshing(true);
                }
            });
        } else {
            mSwipeDbRes.setRefreshing(false);
        }
    }

    @Override
    public void showLoadMoreing(boolean enable) {
        if (!mSwipeDbRes.isLoadMoreEnabled()) {
            return;
        }
        if (enable) {
            mSwipeDbRes.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeDbRes.setLoadingMore(true);
                }
            });
        } else {
            mSwipeDbRes.setLoadingMore(false);
        }
    }

    @Override
    public void hasMoreData(boolean hasMore) {
        if (mSwipeDbRes == null) {
            return;
        }
        if (mSwipeDbRes.isLoadingMore()) {
            showLoadMoreing(false);
        }
        mSwipeDbRes.setLoadMoreEnabled(hasMore);
    }

    @Override
    public void showSortTab(boolean enable) {
        if (enable) {
            mDbResRootAction.setVisibility(View.VISIBLE);
            mTvDbResPbTime.setVisibility(View.VISIBLE);
            mTvDbResBrowseCount.setVisibility(View.VISIBLE);
            mExIconDeResPbTime.setVisibility(View.VISIBLE);
            mExIconDeResBrowseCount.setVisibility(View.VISIBLE);
        } else {
            mDbResRootAction.setVisibility(View.GONE);
            mTvDbResPbTime.setVisibility(View.GONE);
            mTvDbResBrowseCount.setVisibility(View.GONE);
            mExIconDeResPbTime.setVisibility(View.GONE);
            mExIconDeResBrowseCount.setVisibility(View.GONE);
        }
    }

    /**
     * 显示词条页脚信息
     *
     * @param enable
     * @param msg
     */
    @Override
    public void showFooterView(boolean enable, String msg) {
        if (enable) {
            mTvCountDbRes.setText(String.format(getString(R.string.text_word_count), msg));
            mTvCountDbRes.setVisibility(View.VISIBLE);
        } else {
            mTvCountDbRes.setVisibility(View.GONE);
        }
    }

    /**
     * Hide popTreeMenuView
     *
     * @param isHide
     */
    @Override
    public void hideTreeMenu(boolean isHide) {
        if (isHide) {
            ivMenu.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        TreeParentHolder.IconTreeItem item = (TreeParentHolder.IconTreeItem) value;
        themeType = item.id;
        themeName = item.name;
    }

    @Override
    public void onLoadMore() {
        // 正在加载数据时禁止加载更多数据
        mPresenter.loadMore(mEtDbDetailSearch.getText().toString().trim(), themeType);
    }

    @Override
    public void onRefresh() {
        // Refresh data clear all of condition
        mEtDbDetailSearch.setText(null);
        themeType = null;
        mPresenter.refreshView();
    }

    /**
     * PopMenu dismiss call back
     */
    @Override
    public void onDismiss() {
        Toast.makeText(mContext, "你选择了:" + themeName + "分类", Toast.LENGTH_SHORT).show();
        // recover window Alpha
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
        // Request themeType Data
        mPresenter.loadDataWithPara(
                mEtDbDetailSearch.getText().toString().trim(),
                themeType,
                false,
                true
        );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ActionCollect(CollectEvent event) {
        mPresenter.collectRes(event);
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeTimeSortView(int state) {
        if (mExIconDeResPbTime != null) {
            mExIconDeResPbTime.setState(state, true);
        }
    }

    @Override
    public void changeBrowseView(int state) {
        if (mExIconDeResBrowseCount != null) {
            mExIconDeResBrowseCount.setState(state, true);
        }
    }

    @Override
    public void initSortView() {
        if (mExIconDeResPbTime != null && mExIconDeResBrowseCount != null) {
            mExIconDeResPbTime.setFraction(0.5f, false);
            mExIconDeResBrowseCount.setFraction(0.5f, false);
        }
    }
}
