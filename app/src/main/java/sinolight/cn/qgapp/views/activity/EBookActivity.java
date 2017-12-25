package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.github.zagum.expandicon.ExpandIconView;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerEBookActivityComponent;
import sinolight.cn.qgapp.dagger.module.EBookActivityModule;
import sinolight.cn.qgapp.presenter.EBookActivityPresenter;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IEBookActivityView;
import sinolight.cn.qgapp.views.widget.popmenu.TopRightMenu;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by xns on 2017/8/2.
 * EBook Activity
 */

public class EBookActivity extends BaseActivity implements IEBookActivityView,
        OnRefreshListener, OnLoadMoreListener, TabLayout.OnTabSelectedListener,
        TreeNode.TreeNodeClickListener, PopupWindow.OnDismissListener {
    private static final int TYPE_COMPREHENSIVE = 0;
    private static final int TYPE_NEWGOODS = 1;
    private static final int TYPE_PRICE = 2;
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
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;

    private TopRightMenu mTopRightMenu;
    private AndroidTreeView tView;
    private String themeType;
    private String themeName;

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
        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = mTabLayoutEbook.newTab();
            tab.setCustomView(getCustomView(i));
            if (i == 0) {
                tab.select();
            }
            mTabLayoutEbook.addTab(tab);
        }
        // Add line divider
        addTabLayoutDivider();
        mTabLayoutEbook.addOnTabSelectedListener(EBookActivity.this);

        mLayoutManager = new LinearLayoutManager(EBookActivity.this, LinearLayoutManager.VERTICAL, false);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.addItemDecoration(new DividerItemDecoration(EBookActivity.this, DividerItemDecoration.VERTICAL));

        mSwipeEbook.setOnRefreshListener(EBookActivity.this);
        mSwipeEbook.setOnLoadMoreListener(EBookActivity.this);

        mSwipeEbook.setRefreshing(true);
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

    @OnClick({R.id.im_search_back_arrow, R.id.iv_toolbar_search, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_search_back_arrow:
                finish();
                break;
            case R.id.iv_toolbar_search:
                searchData();
                break;
            case R.id.iv_menu:
                popTreeMenu();
                break;
        }
    }

    private void searchData() {
        if (TextUtils.isEmpty(mEtToolbarSearch.getText().toString().trim())) {
            showToast(R.string.text_search_data_empty);
            return;
        } else {
            mPresenter.searchData(
                    mEtToolbarSearch.getText().toString().trim(),
                    themeType
            );
        }
    }

    @Override
    public void initShow(String title) {

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
                        .setHeight(ScreenUtil.getScreenHeight(mContext)/3*2)     //默认高度480
                        .setWidth(ScreenUtil.getScreenWidth(mContext)/3*2)      //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true
                        .dimBackground(true)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .showAsDropDown(mIvMenu, -225, 0);
            } else {
                mTopRightMenu.showAsDropDown(mIvMenu, -225, 0);
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
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
            mSwipeEbook.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeEbook.setRefreshing(true);
                }
            });
        } else {
            mSwipeEbook.setRefreshing(false);
        }
    }

    @Override
    public void showLoadMoreing(boolean enable) {
        if (!mSwipeEbook.isLoadMoreEnabled()) {
            return;
        }
        if (enable) {
            mSwipeEbook.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeEbook.setLoadingMore(true);
                }
            });
        } else {
            mSwipeEbook.setLoadingMore(false);
        }
    }

    @Override
    public void hasMoreData(boolean hasMore) {
        if (mSwipeEbook != null) {
            if (mSwipeEbook.isLoadingMore()) {
                showLoadMoreing(false);
            }
            mSwipeEbook.setLoadMoreEnabled(hasMore);
        }
    }

    @Override
    public void changeSortView(int position, int state) {
        TabLayout.Tab tab = mTabLayoutEbook.getTabAt(position);
        if (tab != null) {
            View view = tab.getCustomView();
            if (view != null) {
                ((ExpandIconView) view.findViewById(R.id.tab_item_ex_icon)).setState(state, true);
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //change data type
        switch (tab.getPosition()) {
            case TYPE_COMPREHENSIVE:
                mPresenter.setDataType(AppContants.EBook.SortType.SORT_COMPREHENSIVE, false);
                break;
            case TYPE_NEWGOODS:
                mPresenter.setDataType(AppContants.EBook.SortType.SORT_NEWGOODS, false);
                break;
            case TYPE_PRICE:
                mPresenter.setDataType(AppContants.EBook.SortType.SORT_PRICE, false);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case TYPE_COMPREHENSIVE:
                mPresenter.setDataType(AppContants.EBook.SortType.SORT_COMPREHENSIVE, true);
                break;
            case TYPE_NEWGOODS:
                mPresenter.setDataType(AppContants.EBook.SortType.SORT_NEWGOODS, true);
                break;
            case TYPE_PRICE:
                mPresenter.setDataType(AppContants.EBook.SortType.SORT_PRICE, true);
                break;
        }
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore(mEtToolbarSearch.getText().toString().trim(), themeType);
    }

    @Override
    public void onRefresh() {
        // Refresh data clear all of condition
        mEtToolbarSearch.setText(null);
        themeType = null;
        mPresenter.refreshView();
    }

    @Override
    public void onDismiss() {
        Toast.makeText(mContext, "你选择了:" + themeName + "分类", Toast.LENGTH_SHORT).show();
        // recover window Alpha
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
        // Request themeType Data
        mPresenter.loadDataWithPara(
                mEtToolbarSearch.getText().toString().trim(),
                themeType,
                false,
                true
        );
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        TreeParentHolder.IconTreeItem item = (TreeParentHolder.IconTreeItem) value;
        themeType = item.id;
        themeName = item.name;
    }

    private View getCustomView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item, null);
        TextView tv = view.findViewById(R.id.tab_item_tv);
        ExpandIconView exView = view.findViewById(R.id.tab_item_ex_icon);
        switch (position) {
            case 0:
                tv.setText(getText(R.string.text_comprehensive));
                exView.setState(ExpandIconView.MORE, false);
                break;
            case 1:
                tv.setText(getText(R.string.text_new_goods));
                exView.setState(ExpandIconView.MORE, false);
                break;
            case 2:
                tv.setText(getText(R.string.text_price));
                exView.setState(ExpandIconView.MORE, false);
                break;
        }
        return view;
    }
}
