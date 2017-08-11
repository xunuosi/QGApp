package sinolight.cn.qgapp.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.MyTabAdapter;
import sinolight.cn.qgapp.dagger.component.UserComponent;

/**
 * Created by xns on 2017/6/29.
 * 资源库首页
 */

public class ResourceFragment extends BaseFragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.et_db_detail_search)
    EditText mEtToolbarSearch;
    @BindView(R.id.tool_bar_rf)
    Toolbar mToolBarRf;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_rf)
    ViewPager mVpRf;
    Unbinder unbinder;

    private MyTabAdapter mTabAdapter;
    private List<Fragment> mFragments;
    private List<String> mTitles = new ArrayList<>();

    public static ResourceFragment newInstance() {
        return new ResourceFragment();
    }

    public ResourceFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        mTitles.add(getString(R.string.text_material));
        mTitles.add(getString(R.string.text_article));
        mTitles.add(getString(R.string.text_img));
        mTitles.add(getString(R.string.text_video));

        mFragments = new ArrayList<>();
        mFragments.add(DBResMaterialFragment.newInstance());
        mFragments.add(DBResArticleFragment.newInstance());
        mFragments.add(DBResPicFragment.newInstance());
        mFragments.add(DBResVideoFragment.newInstance());

        mTabAdapter = new MyTabAdapter(
                getActivity().getSupportFragmentManager(),
                mFragments,
                mTitles
        );


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_resource, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        initData();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVpRf.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mVpRf);
        mTabLayout.addOnTabSelectedListener(this);

        mVpRf.addOnPageChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragments.clear();
    }

    @Override
    protected UserComponent getComponent() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.im_back_arrow_search, R.id.iv_db_detail_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow_search:
                getActivity().onBackPressed();
                break;
            case R.id.iv_db_detail_search:
                getSearchData();
                break;
        }
    }

    private void getSearchData() {
        if (checkSearchElement()) {
            showToastMessage(getString(R.string.text_search_data_empty));
            return;
        } else {
            // TODO: 2017/7/31
        }
    }

    private boolean checkSearchElement() {
        return TextUtils.isEmpty(mEtToolbarSearch.getText().toString().trim());
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
        resetState();
    }

    private void resetState() {
        mEtToolbarSearch.setText(null);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
