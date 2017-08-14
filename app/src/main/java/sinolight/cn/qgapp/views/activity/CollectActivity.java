package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.views.fragment.CollectBookFragment;
import sinolight.cn.qgapp.views.fragment.CollectStdFragment;

/**
 * Created by xns on 2017/8/14.
 * 用户收藏界面
 */

public class CollectActivity extends BaseActivity implements HasComponent<UserComponent> {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tool_bar_collect)
    Toolbar mToolBarCollect;
    @BindView(R.id.tabLayout_collect)
    TabLayout mTabLayoutCollect;
    @BindView(R.id.vp_collect)
    ViewPager mVpCollect;

    private List<String> mTitles = new ArrayList<>();
    private MyTabAdapter mTabAdapter;
    private List<Fragment> mFragments;
    private UserComponent userComponent;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, CollectActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KDBResDataMapper.reset();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initViews() {
        mTvTitle.setText(R.string.text_my_collect);

        mVpCollect.setAdapter(mTabAdapter);
        mTabLayoutCollect.setupWithViewPager(mVpCollect);
    }

    @Override
    protected void initData() {
        mTitles.add(getString(R.string.text_book));
        mTitles.add(getString(R.string.text_standard));
        mTitles.add(getString(R.string.text_img));
        mTitles.add(getString(R.string.text_article));
        mTitles.add(getString(R.string.text_dictionary));
        mTitles.add(getString(R.string.text_analysis));
        mTitles.add(getString(R.string.text_cook));

        mFragments = new ArrayList<>();
        mFragments.add(CollectBookFragment.newInstance());
        mFragments.add(CollectStdFragment.newInstance());
        mFragments.add(CollectBookFragment.newInstance());
        mFragments.add(CollectBookFragment.newInstance());
        mFragments.add(CollectBookFragment.newInstance());
        mFragments.add(CollectBookFragment.newInstance());
        mFragments.add(CollectBookFragment.newInstance());

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

    @OnClick(R.id.im_back_arrow)
    public void onViewClicked() {
        finish();
    }

    @Override
    public UserComponent getComponent() {
        return userComponent;
    }
}
