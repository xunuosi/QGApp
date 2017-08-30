package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.adapter.MyTabAdapter;
import sinolight.cn.qgapp.dagger.HasComponent;
import sinolight.cn.qgapp.dagger.component.DaggerUserComponent;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.dagger.module.UserModule;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.views.fragment.CollectArticleFragment;
import sinolight.cn.qgapp.views.fragment.CollectBookFragment;
import sinolight.cn.qgapp.views.fragment.CollectCookFragment;
import sinolight.cn.qgapp.views.fragment.CollectDicFragment;
import sinolight.cn.qgapp.views.fragment.CollectIndustryAnalysisFragment;
import sinolight.cn.qgapp.views.fragment.CollectPicFragment;
import sinolight.cn.qgapp.views.fragment.CollectStdFragment;
import sinolight.cn.qgapp.views.fragment.CollectVideoFragment;

/**
 * Created by xns on 2017/8/30.
 * display research result
 */

public class SearchDisplayActivity extends BaseActivity implements HasComponent<UserComponent> {

    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tool_bar_research_dis)
    Toolbar mToolBarResearchDis;
    @BindView(R2.id.tabLayout_research_dis)
    TabLayout mTabLayoutResearchDis;
    @BindView(R2.id.vp_research_dis)
    ViewPager mVpResearchDis;

    private List<String> mTitles = new ArrayList<>();
    private MyTabAdapter mTabAdapter;
    private List<Fragment> mFragments;
    private UserComponent userComponent;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, SearchDisplayActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        return R.layout.activity_search_dis;
    }

    @Override
    protected void initViews() {
        mTvTitle.setText(R.string.text_my_collect);

        mVpResearchDis.setAdapter(mTabAdapter);
        mTabLayoutResearchDis.setupWithViewPager(mVpResearchDis);
    }

    @Override
    protected void initData() {
        mTitles.add(getString(R.string.text_book));
        mTitles.add(getString(R.string.text_standard));
        mTitles.add(getString(R.string.text_img));
        mTitles.add(getString(R.string.text_article));
        mTitles.add(getString(R.string.text_dictionary));
        mTitles.add(getString(R.string.text_analysis));

        mFragments = new ArrayList<>();
        mFragments.add(CollectBookFragment.newInstance());
        mFragments.add(CollectStdFragment.newInstance());
        mFragments.add(CollectPicFragment.newInstance());
        mFragments.add(CollectArticleFragment.newInstance());
        mFragments.add(CollectDicFragment.newInstance());
        mFragments.add(CollectIndustryAnalysisFragment.newInstance());

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
