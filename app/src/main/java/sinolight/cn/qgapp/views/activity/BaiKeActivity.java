package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import sinolight.cn.qgapp.data.bean.EventAction;
import sinolight.cn.qgapp.views.fragment.BaiKeAnalysisFragment;
import sinolight.cn.qgapp.views.fragment.BaiKeWordFragment;
import sinolight.cn.qgapp.views.fragment.BaseLazyLoadFragment;

/**
 * Created by xns on 2017/8/10.
 * 百科库的界面
 */

public class BaiKeActivity extends BaseActivity implements HasComponent<UserComponent>,
        TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    private static final int TYPE_ANALYSIS = 0;
    private static final int TYPE_WORD = 1;

    @BindView(R.id.tabLayout_baike)
    TabLayout mTabLayoutBaike;
    @BindView(R.id.vp_baike)
    ViewPager mVpBaike;
    @BindView(R.id.et_db_detail_search)
    EditText mEtDbDetailSearch;
    @BindView(R.id.iv_db_detail_search)
    ImageView mIvDbDetailSearch;
    @BindView(R.id.tool_bar_baike)
    Toolbar mToolBarBaike;

    private List<String> mTitles = new ArrayList<>();
    private MyTabAdapter mTabAdapter;
    private List<Fragment> mFragments;
    private UserComponent userComponent;
    private BaseLazyLoadFragment currentFragment;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, BaiKeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
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
        mFragments.clear();
        currentFragment = null;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
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
        mFragments.add(BaiKeAnalysisFragment.newInstance());
        mFragments.add(BaiKeWordFragment.newInstance());

        mTabAdapter = new MyTabAdapter(
                getSupportFragmentManager(),
                mFragments,
                mTitles
        );
    }

    private void setCurrentFragment(BaseLazyLoadFragment currentFragment) {
        this.currentFragment = currentFragment;
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
        setCurrentFragment((BaseLazyLoadFragment) mFragments.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @OnClick({R.id.im_back_arrow_search, R.id.iv_db_detail_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow_search:
                finish();
                break;
            case R.id.iv_db_detail_search:
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetSearch(EventAction action) {
        switch (action) {
            case ACTION_RESET:
                mEtDbDetailSearch.setText(null);
                break;
        }

    }
}
