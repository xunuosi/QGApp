package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerDBResActivityComponent;
import sinolight.cn.qgapp.dagger.module.DBResActivityModule;
import sinolight.cn.qgapp.presenter.DBResActivityPresenter;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IDBResActivityView;
import sinolight.cn.qgapp.views.widget.popmenu.TopRightMenu;

/**
 * Created by xns on 2017/7/11.
 * 行业库资源的Activity
 */

public class DBResourceActivity extends BaseActivity implements
        IDBResActivityView, TreeNode.TreeNodeClickListener, OnRefreshListener, OnLoadMoreListener {
    private static final String TAG = "DBResourceActivity";

    private TopRightMenu mTopRightMenu;
    private AndroidTreeView tView;
    private RecyclerView.LayoutManager mLayoutManager;

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
    public int setLayoutId() {
        return R.layout.activity_db_res;
    }

    @Override
    protected void initViews() {
        mLayoutManager = new LinearLayoutManager(DBResourceActivity.this, LinearLayoutManager.VERTICAL, false);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.addItemDecoration(new DBResourceActivity.LinearDivider(mContext));

        mSwipeDbRes.setRefreshing(true);
        mSwipeDbRes.setOnRefreshListener(DBResourceActivity.this);
        mSwipeDbRes.setOnLoadMoreListener(DBResourceActivity.this);
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
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_menu:
                mPresenter.popTreeMenu();
                break;
        }
    }

    @Override
    public void initShow(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void popTreeMenu(TreeNode root) {
        if (tView == null) {
            tView = new AndroidTreeView(DBResourceActivity.this, root);
            tView.setDefaultAnimation(true);
//        tView.setUse2dScroll(true);
            tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
            tView.setDefaultViewHolder(TreeParentHolder.class);
            tView.setDefaultNodeClickListener(DBResourceActivity.this);
            tView.setUseAutoToggle(true);
        }
        if (mTopRightMenu == null) {
            mTopRightMenu = new TopRightMenu(DBResourceActivity.this, tView.getView());
            mTopRightMenu
                    .setHeight(850)     //默认高度480
                    .setWidth(450)      //默认宽度wrap_content
                    .showIcon(true)     //显示菜单图标，默认为true
                    .dimBackground(true)           //背景变暗，默认为true
                    .needAnimationStyle(true)   //显示动画，默认为true
                    .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                    .showAsDropDown(ivMenu, -225, 0);
        } else {
            mTopRightMenu.showAsDropDown(ivMenu, -225, 0);
        }
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListView(KDBResAdapter adapter) {
        mSwipeTarget.setAdapter(adapter);
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
        if (mSwipeDbRes.isLoadingMore()) {
            showLoadMoreing(false);
        }
        mSwipeDbRes.setLoadMoreEnabled(hasMore);
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        TreeParentHolder.IconTreeItem item = (TreeParentHolder.IconTreeItem) value;
        Toast.makeText(mContext, "onClick:" + item.id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        // 正在加载数据时禁止加载更多数据
        if (!mSwipeDbRes.isLoadingMore()) {
            mPresenter.loadMore();
        }
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
