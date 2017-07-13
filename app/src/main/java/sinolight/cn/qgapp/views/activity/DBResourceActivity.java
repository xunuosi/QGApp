package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerDBResActivityComponent;
import sinolight.cn.qgapp.dagger.module.DBResActivityModule;
import sinolight.cn.qgapp.presenter.DBResActivityPresenter;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.holder.ArrowExpandHolder;
import sinolight.cn.qgapp.views.view.IDBResActivityView;
import sinolight.cn.qgapp.views.widget.popmenu.TopRightMenu;

/**
 * Created by xns on 2017/7/11.
 * 行业库资源的Activity
 */

public class DBResourceActivity extends BaseActivity implements IDBResActivityView {
    private static final String TAG = "DBResourceActivity";
    private static final String NAME = "xns";

    private TopRightMenu mTopRightMenu;
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                break;
            case R.id.iv_menu:
                createTree();
                break;
        }
    }

    private void createTree() {
        TreeNode root = TreeNode.root();
        TreeNode s1 = new TreeNode(new ArrowExpandHolder.IconTreeItem(R.drawable.arrow_white_down, "Folder with very long name ")).setViewHolder(
                new ArrowExpandHolder(mContext));
        TreeNode s2 = new TreeNode(new ArrowExpandHolder.IconTreeItem(R.drawable.arrow_white_down, "Folder with very long name ")).setViewHolder(
                new ArrowExpandHolder(mContext));
        fillFolder(s1);
        fillFolder(s2);

        root.addChildren(s1, s2);

        AndroidTreeView tView = new AndroidTreeView(DBResourceActivity.this, root);
        tView.setDefaultAnimation(true);
//        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        tView.setDefaultViewHolder(ArrowExpandHolder.class);
        tView.setUseAutoToggle(false);
        View menu = tView.getView();

        mTopRightMenu = new TopRightMenu(DBResourceActivity.this, menu);
        mTopRightMenu
                .setHeight(850)     //默认高度480
                .setWidth(600)      //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)           //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                .showAsDropDown(ivMenu, -225, 0);
    }
    private void fillFolder(TreeNode folder) {
        TreeNode currentNode = folder;
        for (int i = 0; i < 4; i++) {
            TreeNode file = new TreeNode(new ArrowExpandHolder.IconTreeItem(R.drawable.arrow_white_down, NAME + " " + i))
                    .setViewHolder(new ArrowExpandHolder(mContext));
            currentNode.addChild(file);
            currentNode = file;
        }
    }


    @Override
    public void initShow(String title) {
        tvTitle.setText(title);
    }
}
