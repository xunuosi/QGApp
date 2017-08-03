package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerEBookActivityComponent;
import sinolight.cn.qgapp.dagger.module.EBookActivityModule;
import sinolight.cn.qgapp.presenter.EBookActivityPresenter;
import sinolight.cn.qgapp.views.view.IEBookActivityView;

/**
 * Created by xns on 2017/8/2.
 * EBook Activity
 */

public class EBookActivity extends BaseActivity implements IEBookActivityView,
        OnRefreshListener, OnLoadMoreListener, TabLayout.OnTabSelectedListener {
    @Inject
    Context mContext;
    @Inject
    EBookActivityPresenter mPresenter;
    @BindView(R.id.et_toolbar_search)
    EditText mEtToolbarSearch;
    @BindView(R.id.tool_bar_ebook)
    Toolbar mToolBarEbook;
    @BindView(R.id.tabLayout_ebook)
    TabLayout mTabLayoutEbook;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_ebook)
    SwipeToLoadLayout mSwipeEbook;

    private LinearLayoutManager mLayoutManager;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, EBookActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSwipeEbook.isRefreshing()) {
            mSwipeEbook.setRefreshing(false);
        }
        if (mSwipeEbook.isLoadingMore()) {
            mSwipeEbook.setLoadingMore(false);
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_ebook;
    }

    @Override
    protected void initViews() {
        mTabLayoutEbook.addTab(mTabLayoutEbook.newTab().setText(R.string.text_comprehensive), true);
        mTabLayoutEbook.addTab(mTabLayoutEbook.newTab().setText(R.string.text_new_goods));
        mTabLayoutEbook.addTab(mTabLayoutEbook.newTab().setText(R.string.text_price));
        // Add line divider
        addTabLayoutDivider();
        mTabLayoutEbook.addOnTabSelectedListener(EBookActivity.this);

        mLayoutManager = new LinearLayoutManager(EBookActivity.this, LinearLayoutManager.VERTICAL, false);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.addItemDecoration(new LinearDivider(EBookActivity.this));

        mSwipeEbook.setOnRefreshListener(EBookActivity.this);
        mSwipeEbook.setOnLoadMoreListener(EBookActivity.this);

    }

    private void addTabLayoutDivider() {
        LinearLayout linearLayout = (LinearLayout) mTabLayoutEbook.getChildAt(0);
        linearLayout.setPadding(0, 16, 0, 16);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerEBookActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .eBookActivityModule(new EBookActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.im_search_back_arrow, R.id.iv_toolbar_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_search_back_arrow:
                finish();
                break;
            case R.id.iv_toolbar_search:
                break;
        }
    }

    @Override
    public void initShow(String title) {

    }

    @Override
    public void popTreeMenu() {

    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListView(KDBResAdapter adapter) {

    }

    @Override
    public void showRefreshing(boolean enable) {

    }

    @Override
    public void showLoadMoreing(boolean enable) {

    }

    @Override
    public void hasMoreData(boolean hasMore) {

    }

    @Override
    public void showTab(boolean enable) {

    }

    @Override
    public void showFooterView(boolean enable, String msg) {

    }

    @Override
    public void hideTreeMenu(boolean isHide) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

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
