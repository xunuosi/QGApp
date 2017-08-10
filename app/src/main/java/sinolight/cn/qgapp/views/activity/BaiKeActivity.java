package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.MyTabAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.DaggerUserComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.dagger.module.UserModule;
import sinolight.cn.qgapp.views.fragment.BaiKeFragment;
import sinolight.cn.qgapp.views.fragment.ResourceFragment;

/**
 * Created by xns on 2017/8/10.
 * 百科库的界面
 */

public class BaiKeActivity extends BaseActivity implements HasComponent<UserComponent>,
        TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.et_toolbar_search)
    EditText mEtToolbarSearch;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.tool_bar_baike)
    Toolbar mToolBarBaike;
    @BindView(R.id.tabLayout_baike)
    TabLayout mTabLayoutBaike;
    @BindView(R.id.vp_baike)
    ViewPager mVpBaike;

    private List<String> mTitles = new ArrayList<>();
    private MyTabAdapter mTabAdapter;
    private List<Fragment> mFragments;
    private UserComponent userComponent;
    private ResourceFragment currentFragment;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, BaiKeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments.clear();
        currentFragment = null;
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_baike;
    }

    @Override
    protected void initViews() {
        mVpBaike.setAdapter(mTabAdapter);
        mTabLayoutBaike.setupWithViewPager(mVpBaike);
        // Add line divider
        addTabLayoutDivider();

        mTabLayoutBaike.addOnTabSelectedListener(this);
        mVpBaike.addOnPageChangeListener(this);
    }

    private void addTabLayoutDivider() {
        LinearLayout linearLayout = (LinearLayout) mTabLayoutBaike.getChildAt(0);
        linearLayout.setPadding(0, 16, 0, 16);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
    }

    @Override
    protected void initData() {
        mTitles.add(getString(R.string.text_analysis));
        mTitles.add(getString(R.string.text_all_word));

        mFragments = new ArrayList<>();
        mFragments.add(BaiKeFragment.newInstance(BaiKeFragment.TYPE_BAIKE_ANALYSIS));
        mFragments.add(BaiKeFragment.newInstance(BaiKeFragment.TYPE_BAIKE_WORD));

        mTabAdapter = new MyTabAdapter(
                getSupportFragmentManager(),
                mFragments,
                mTitles
        );
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.im_search_back_arrow, R.id.iv_toolbar_search, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_search_back_arrow:
                finish();
                break;
            case R.id.iv_toolbar_search:
                break;
            case R.id.iv_menu:
                break;
        }
    }
}
