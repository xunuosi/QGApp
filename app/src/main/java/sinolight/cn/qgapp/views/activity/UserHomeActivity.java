package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.MyTabAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.DaggerUserComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.dagger.module.UserModule;
import sinolight.cn.qgapp.data.http.entity.UserEntity;
import sinolight.cn.qgapp.views.fragment.UserHomeFragment;

/**
 * Created by xns on 2017/7/25.
 * 个人主页界面
 */

public class UserHomeActivity extends BaseActivity implements HasComponent<UserComponent> {
    @BindView(R.id.tv_user_home_tb_title)
    TextView mTvUserHomeTbTitle;
    @BindView(R.id.tb_user_home)
    Toolbar mTbUserHome;
    @BindView(R.id.tl_user_home)
    TabLayout mTlUserHome;
    @BindView(R.id.vp_user_home)
    ViewPager mVpUserHome;

    private UserComponent userComponent;
    private MyTabAdapter mTabAdapter;
    private List<Fragment> mFragments;
    private List<String> mTitles = new ArrayList<>();
    private UserEntity mUserData;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, UserHomeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        handleData(getIntent());
    }

    /**
     * Handle and checkout data method
     * @param intent
     */
    private void handleData(Intent intent) {
        if (intent != null) {
            mUserData = intent.getParcelableExtra(AppContants.User.USER_BEAN);
        }
        if (mUserData != null) {
            showSuccess();
        } else {
            Toast.makeText(App.getContext(), getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get data to show
     */
    private void showSuccess() {
        mFragments = new ArrayList<>();
        mFragments.add(UserHomeFragment.newInstance(UserHomeFragment.TYPE_PERSONINFO, mUserData));
        mFragments.add(UserHomeFragment.newInstance(UserHomeFragment.TYPE_SECURITY, mUserData));

        mTabAdapter = new MyTabAdapter(getSupportFragmentManager(), mFragments, mTitles);

        mVpUserHome.setAdapter(mTabAdapter);
        mTlUserHome.setupWithViewPager(mVpUserHome);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_user_home;
    }

    @Override
    protected void initViews() {
        mTitles.add(getString(R.string.text_person_info));
        mTitles.add(getString(R.string.text_account_security));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        this.userComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(new UserModule())
                .build();
    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }

    @OnClick(R.id.im_back_arrow)
    public void onViewClicked() {
       finish();
    }
}
