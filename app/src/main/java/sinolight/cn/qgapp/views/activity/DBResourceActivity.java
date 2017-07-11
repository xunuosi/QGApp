package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerDBResActivityComponent;
import sinolight.cn.qgapp.dagger.module.DBResActivityModule;
import sinolight.cn.qgapp.presenter.DBResActivityPresenter;
import sinolight.cn.qgapp.views.view.IDBResActivityView;
import sinolight.cn.qgapp.views.widget.popmenu.MenuItem;
import sinolight.cn.qgapp.views.widget.popmenu.TopRightMenu;

/**
 * Created by xns on 2017/7/11.
 * 行业库资源的Activity
 */

public class DBResourceActivity extends BaseActivity implements IDBResActivityView {
    private static final String TAG = "DBResourceActivity";
    private TopRightMenu mTopRightMenu;
    private boolean showIcon = true;
    private boolean dimBg = true;
    private boolean needAnim = true;
    @Inject
    Context mContext;
    @Inject
    DBResActivityPresenter mPresenter;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tool_bar_db_res)
    Toolbar mToolBarDbRes;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, DBResourceActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
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
                .build();
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
                mTopRightMenu = new TopRightMenu(DBResourceActivity.this);
                List<MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new MenuItem(R.drawable.qr_scan, "发起多人聊天"));
                menuItems.add(new MenuItem(R.drawable.qr_scan, "加好友"));
                menuItems.add(new MenuItem(R.drawable.qr_scan, "扫一扫"));
                mTopRightMenu
                        .setHeight(480)     //默认高度480
                        .setWidth(320)      //默认宽度wrap_content
                        .showIcon(showIcon)     //显示菜单图标，默认为true
                        .dimBackground(dimBg)           //背景变暗，默认为true
                        .needAnimationStyle(needAnim)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .addMenuItem(new MenuItem(R.drawable.qr_scan, "面对面快传"))
                        .addMenuItem(new MenuItem(R.drawable.qr_scan, "付款"))
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                Toast.makeText(DBResourceActivity.this, "点击菜单:" + position, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .showAsDropDown(ivMenu, -225, 10);
                break;
        }
    }
}
