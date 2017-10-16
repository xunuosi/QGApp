package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerMaterialListActivityComponent;
import sinolight.cn.qgapp.dagger.module.MaterialListActivityModule;
import sinolight.cn.qgapp.presenter.MaterialListActivityPresenter;
import sinolight.cn.qgapp.views.holder.TreeParentHolder;
import sinolight.cn.qgapp.views.view.IMaterialListActivityView;
import sinolight.cn.qgapp.views.widget.popmenu.TopRightMenu;

/**
 * Created by xns on 2017/8/1.
 * 菜谱列表的Activity
 */

public class MaterialListActivity extends BaseActivity implements IMaterialListActivityView,
        OnRefreshListener, OnLoadMoreListener, TreeNode.TreeNodeClickListener, PopupWindow.OnDismissListener {
    private static final String TAG = "MaterialListActivity";

    @Inject
    Context mContext;
    @Inject
    MaterialListActivityPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tool_bar_material_list)
    Toolbar mToolBarMaterialList;
    @BindView(R.id.et_material_list_search)
    EditText mEtMaterialListSearch;
    @BindView(R.id.swipe_target)
    RecyclerView mSwipeTarget;
    @BindView(R.id.swipe_material_list)
    SwipeToLoadLayout mSwipeMaterialList;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;

    private TopRightMenu mTopRightMenu;
    private AndroidTreeView tView;
    private String themeType;
    private String themeName;

    private LinearLayoutManager mLayoutManager;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, MaterialListActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.loadTreeMenu();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_material_list;
    }

    @Override
    protected void initViews() {
        mTvTitle.setText(R.string.text_material_list);

        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mSwipeTarget.setLayoutManager(mLayoutManager);
        mSwipeTarget.setHasFixedSize(true);
        mSwipeTarget.addItemDecoration(new DividerItemDecoration(MaterialListActivity.this, DividerItemDecoration.VERTICAL));

        mSwipeMaterialList.setOnRefreshListener(MaterialListActivity.this);
        mSwipeMaterialList.setOnLoadMoreListener(MaterialListActivity.this);
        mSwipeMaterialList.setRefreshing(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerMaterialListActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .materialListActivityModule(new MaterialListActivityModule(this))
                .build()
                .inject(this);
    }


    private void searchData() {
        if (TextUtils.isEmpty(mEtMaterialListSearch.getText().toString().trim())) {
            showToast(R.string.text_search_data_empty);
            return;
        } else {
            mPresenter.searchData(mEtMaterialListSearch.getText().toString().trim(), themeType);
        }
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore(mEtMaterialListSearch.getText().toString().trim(), themeType);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListView(CommonTitleAdapter adapter) {
        if (mSwipeTarget != null && mSwipeTarget.getAdapter() == null) {
            mSwipeTarget.setAdapter(adapter);
        }
    }


    @Override
    public void showRefreshing(boolean enable) {
        if (enable) {
            mSwipeMaterialList.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeMaterialList.setRefreshing(true);
                }
            });
        } else {
            mSwipeMaterialList.setRefreshing(false);
        }
    }

    @Override
    public void showLoadMoreing(boolean enable) {
        if (!mSwipeMaterialList.isLoadMoreEnabled()) {
            return;
        }
        if (enable) {
            mSwipeMaterialList.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeMaterialList.setLoadingMore(true);
                }
            });
        } else {
            mSwipeMaterialList.setLoadingMore(false);
        }
    }

    @Override
    public void hasMoreData(boolean hasMore) {
        if (mSwipeMaterialList != null) {
            if (mSwipeMaterialList.isLoadingMore()) {
                showLoadMoreing(false);
            }
            mSwipeMaterialList.setLoadMoreEnabled(hasMore);
        }
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
    public void onRefresh() {
        mEtMaterialListSearch.setText(null);
        themeType = null;
        mPresenter.refreshView();
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_menu, R.id.iv_material_list_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_menu:
                popTreeMenu();
                break;
            case R.id.iv_material_list_search:
                searchData();
                break;
        }
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
                mEtMaterialListSearch.getText().toString().trim(),
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
}
