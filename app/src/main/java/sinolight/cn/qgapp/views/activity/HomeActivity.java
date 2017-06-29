package sinolight.cn.qgapp.views.activity;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerHomeActivityComponent;
import sinolight.cn.qgapp.dagger.module.HomeActivityModule;
import sinolight.cn.qgapp.utils.PermissionListener;
import sinolight.cn.qgapp.views.fragment.HomeFragment;
import sinolight.cn.qgapp.views.fragment.KnowledgeFragment;
import sinolight.cn.qgapp.views.fragment.ResourceFragment;
import sinolight.cn.qgapp.views.fragment.UserFragment;
import sinolight.cn.qgapp.views.view.IHomeActivityView;

public class HomeActivity extends BaseActivity implements PermissionListener,IHomeActivityView {
    private NavigationController mNavigationController;

    @Inject
    Context mContext;
    @Inject
    HomeFragment mHomeFragment;
    @Inject
    KnowledgeFragment mKnowledgeFragment;
    @Inject
    ResourceFragment mResourceFragment;
    @Inject
    UserFragment mUserFragment;

    @BindView(R.id.bottom_tab)
    PageBottomTabLayout bottomTab;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.home_activity_container)
    FrameLayout mHomeActivityContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        checkAppPermission();
    }

    private void initializeInjector() {
        DaggerHomeActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .homeActivityModule(new HomeActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolBar);
        tvTitle.setText(R.string.toolbar_home_title);
        mNavigationController = bottomTab.custom()
                .addItem(newItem(R.drawable.tab_home,R.drawable.tab_home_pre,getString(R.string.bottomBar_home)))
                .addItem(newItem(R.drawable.tab_knowledge,R.drawable.tab_knowledge_pre,getString(R.string.bottomBar_knowledge)))
                .addItem(newItem(R.drawable.tab_resource,R.drawable.tab_resource_pre,getString(R.string.bottomBar_res)))
                .addItem(newItem(R.drawable.tab_user,R.drawable.tab_user_pre,getString(R.string.bottomBar_user)))
                .build();

        addFragment(R.id.home_activity_container, mHomeFragment);
        mNavigationController.setSelect(0);

        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                //选中时触发
                switch (index) {
                    case 0:
                        replaceFragment(R.id.home_activity_container, mHomeFragment);
                        break;
                    case 1:
                        replaceFragment(R.id.home_activity_container, mKnowledgeFragment);
                        break;
                    case 2:
                        replaceFragment(R.id.home_activity_container, mResourceFragment);
                        break;
                    case 3:
                        replaceFragment(R.id.home_activity_container, mUserFragment);
                        break;
                }
            }

            @Override
            public void onRepeat(int index) {
                //重复选中时触发

            }
        });
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(ActivityCompat.getColor(mContext, R.color.color_bottom_text));
        normalItemView.setTextCheckedColor(ActivityCompat.getColor(mContext, R.color.color_selected_bottom_text));
        return normalItemView;
    }

    private void checkAppPermission() {
        addPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET}, this);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.im_back_arrow)
    public void onViewClicked() {
    }

    @Override
    public void onGranted() {

    }

    @Override
    public void onDenied(List<String> deniedPermission) {

    }
}
