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
import android.widget.ImageView;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.MyTabAdapter;
import sinolight.cn.qgapp.dagger.component.UserComponent;
import sinolight.cn.qgapp.views.widget.popmenu.TopRightMenu;

/**
 * Created by xns on 2017/6/29.
 * 资源库首页
 */

public class ResourceFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    private static final int TYPE_MATERIAL_FRAGMENT = 0;
    private static final int TYPE_ARTICLE_FRAGMENT = 1;
    private static final int TYPE_PIC_FRAGMENT = 2;
    private static final int TYPE_VIDEO_FRAGMENT = 3;

    @BindView(R.id.et_toolbar_search)
    EditText mEtToolbarSearch;
    @BindView(R.id.tool_bar_rf)
    Toolbar mToolBarRf;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_rf)
    ViewPager mVpRf;
    Unbinder unbinder;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;

    private MyTabAdapter mTabAdapter;
    private List<Fragment> mFragments;
    private List<String> mTitles = new ArrayList<>();
    // TreeMenu
    private TopRightMenu mTopRightMenu;
    private AndroidTreeView tView;

    public static ResourceFragment newInstance() {
        return new ResourceFragment();
    }

    public ResourceFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
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
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVpRf.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mVpRf);
        mTabLayout.addOnTabSelectedListener(this);
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

    @OnClick({R.id.im_search_back_arrow, R.id.iv_toolbar_search, R.id.iv_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_search_back_arrow:
                getActivity().onBackPressed();
                break;
            case R.id.iv_toolbar_search:
                getSearchData();
                break;
            case R.id.iv_menu:
                popTreeMenu();
                break;
        }
    }

    private void getSearchData() {
        if (checkSearchElement()) {

            return;
        } else {

        }
    }

    private boolean checkSearchElement() {
        return TextUtils.isEmpty(mEtToolbarSearch.getText().toString().trim());
    }

    private void popTreeMenu() {
        ((DBResArticleFragment) mFragments.get(1)).popTreeMenu(mIvMenu);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        switch (position) {
            case TYPE_MATERIAL_FRAGMENT:
            case TYPE_PIC_FRAGMENT:
            case TYPE_VIDEO_FRAGMENT:
                mIvMenu.setVisibility(View.GONE);
                break;
            case TYPE_ARTICLE_FRAGMENT:
                mIvMenu.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
