package sinolight.cn.qgapp.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;

/**
 * Created by xns on 2017/8/2.
 * EBook Activity
 */

public class EBookActivity extends BaseActivity {

    @BindView(R.id.et_toolbar_search)
    EditText mEtToolbarSearch;
    @BindView(R.id.tool_bar_ebook)
    Toolbar mToolBarEbook;
    @BindView(R.id.tabLayout_ebook)
    TabLayout mTabLayoutEbook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_ebook;
    }

    @Override
    protected void initViews() {
        mTabLayoutEbook.addTab(mTabLayoutEbook.newTab().setText(R.string.text_comprehensive), true);
        mTabLayoutEbook.addTab(mTabLayoutEbook.newTab().setText(R.string.text_new_goods));
        mTabLayoutEbook.addTab(mTabLayoutEbook.newTab().setText(R.string.text_price));
        // Add line divider
        LinearLayout linearLayout = (LinearLayout) mTabLayoutEbook.getChildAt(0);
        linearLayout.setPadding(0,16,0,16);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {

    }

    @OnClick({R.id.im_search_back_arrow, R.id.iv_toolbar_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_search_back_arrow:
                break;
            case R.id.iv_toolbar_search:
                break;
        }
    }
}
