package sinolight.cn.qgapp.views.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.DaggerHomeActivityComponent;
import sinolight.cn.qgapp.dagger.component.DaggerUserComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.dagger.module.HomeActivityModule;
import sinolight.cn.qgapp.dagger.module.UserModule;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.TokenEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.utils.PermissionListener;
import sinolight.cn.qgapp.utils.RSA;
import sinolight.cn.qgapp.views.fragment.BaseFragment;
import sinolight.cn.qgapp.views.fragment.HomeFragment;
import sinolight.cn.qgapp.views.fragment.KnowledgeFragment;
import sinolight.cn.qgapp.views.fragment.ResourceFragment;
import sinolight.cn.qgapp.views.fragment.UserFragment;
import sinolight.cn.qgapp.views.view.IHomeActivityView;

public class HomeActivity extends BaseActivity implements PermissionListener, IHomeActivityView, HasComponent<UserComponent> {
    private static final String TAG = "HomeActivity";
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_KNOWLEDGE = 1;
    private static final int FRAGMENT_RES = 2;
    private static final int FRAGMENT_USER = 3;

    private NavigationController mNavigationController;
    private UserComponent userComponent;
    private boolean isLogined;
    private boolean isSplashActivity;
    // 标志位表示当前是否为HomeFragment
    private boolean isHomeFragment = false;
    private HttpSubscriber loginObserver = new HttpSubscriber(new OnResultCallBack<TokenEntity>() {

        @Override
        public void onSuccess(TokenEntity tokenEntity) {
            String token = tokenEntity.getToken();
            AppHelper.getInstance().setCurrentToken(token);
        }

        @Override
        public void onError(int code, String errorMsg) {
            L.d(TAG, "code:" + code + ",errorMsg:" + errorMsg);
            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            startActivity(LoginActivity.getCallIntent(mContext).putExtra(AppContants.Account.USER_NAME, 
                    AppHelper.getInstance().getCurrentUserName()));
        }
    }); 
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
    @BindView(R.id.home_activity_container)
    FrameLayout mHomeActivityContainer;

    private BaseFragment currentFragment;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        checkAppPermission();
        Intent intent = getIntent();
        if (intent != null) {
            isLogined = intent.getBooleanExtra(AppContants.Account.IS_LOGINED, false);
            isSplashActivity = intent.getBooleanExtra(AppContants.Account.IS_SPLASHACTIVITY, false);
            if (isSplashActivity) {
                if (isLogined) {
                    try {
                        String rsa = AppHelper.getInstance().getCurrentPW();
                        byte[] bytes = RSA.decryptBASE64(rsa);
                        String pw = new String(bytes);
                        HttpManager.getInstance().login(loginObserver,
                                AppHelper.getInstance().getCurrentUserName(),
                                pw);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Go to LoginActivity
                    startActivity(LoginActivity.getCallIntent(mContext));
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginObserver.unSubscribe();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerHomeActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .homeActivityModule(new HomeActivityModule(this))
                .build()
                .inject(this);

        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule())
                .build();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews() {
        mNavigationController = bottomTab.custom()
                .addItem(newItem(R.drawable.tab_home, R.drawable.tab_home_pre, getString(R.string.bottomBar_home)))
                .addItem(newItem(R.drawable.tab_knowledge, R.drawable.tab_knowledge_pre, getString(R.string.bottomBar_knowledge)))
                .addItem(newItem(R.drawable.tab_resource, R.drawable.tab_resource_pre, getString(R.string.bottomBar_res)))
                .addItem(newItem(R.drawable.tab_user, R.drawable.tab_user_pre, getString(R.string.bottomBar_user)))
                .build();

        if (!mHomeFragment.isAdded()) {
            addFragment(R.id.home_activity_container, mHomeFragment);
            isHomeFragment = true;
            currentFragment = mHomeFragment;
        }
        mNavigationController.setSelect(0);

        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                //选中时触发
                switch (index) {
                    case FRAGMENT_HOME:
                        clickHomeFragment();
                        break;
                    case FRAGMENT_KNOWLEDGE:
                        clickKnowledgeFragment();
                        break;
                    case FRAGMENT_RES:
                        clickResourceFragment();
                        break;
                    case FRAGMENT_USER:
                        if (isLogined) {
                            clickUserFragment();
                        } else {
                            startActivity(LoginActivity.getCallIntent(mContext));
                        }
                        break;
                }
            }

            @Override
            public void onRepeat(int index) {
                //重复选中时触发

            }
        });
    }

    private void clickUserFragment() {
        isHomeFragment = false;
        mNavigationController.setSelect(FRAGMENT_USER);
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mUserFragment);
    }

    private void clickResourceFragment() {
        isHomeFragment = false;
        mNavigationController.setSelect(FRAGMENT_RES);
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mResourceFragment);
    }

    private void clickKnowledgeFragment() {
        isHomeFragment = false;
        mNavigationController.setSelect(FRAGMENT_KNOWLEDGE);
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mKnowledgeFragment);
    }

    private void clickHomeFragment() {
        isHomeFragment = true;
        mNavigationController.setSelect(FRAGMENT_HOME);
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mHomeFragment);
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
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

    @Override
    public void onGranted() {

    }

    @Override
    public void onDenied(List<String> deniedPermission) {

    }

    @Override
    public void onBackPressed() {
        if (!isHomeFragment) {
            mNavigationController.setSelect(0);
            clickHomeFragment();
            isHomeFragment = true;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }

    /**
     * 添加或者显示 fragment
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.home_activity_container, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = (BaseFragment) fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeFragment(AppContants.HomeStore.Type type) {
        switch (type) {
            case TYPE_DB_KNOWLEDGE:
                clickKnowledgeFragment();
                break;
            case TYPE_DB_RES:
                clickResourceFragment();
                break;
        }
    }
}
