package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerHelpDetailComponent;
import sinolight.cn.qgapp.dagger.module.HelpDetailModule;
import sinolight.cn.qgapp.data.http.entity.HelpDataEntity;
import sinolight.cn.qgapp.presenter.HelpDetailPresenter;
import sinolight.cn.qgapp.views.view.IHelpDetailView;

/**
 * Created by xns on 2017/9/5.
 * Help info activity
 */

public class HelpInfoActivity extends BaseActivity implements IHelpDetailView {
    @Inject
    Context mContext;
    @Inject
    HelpDetailPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tool_bar_help_info)
    Toolbar mToolBarHelpInfo;
    @BindView(R.id.tv_help_info_content)
    TextView mTvHelpInfoContent;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;


    public static Intent getCallIntent(Context context) {
        return new Intent(context, HelpInfoActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.checkout(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_help_info;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerHelpDetailComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .helpDetailModule(new HelpDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToastByRes(int strID) {
        String msg = getString(strID);
        this.showToastByStr(msg);
    }

    @Override
    public void showToastByStr(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefreshing(boolean enable) {
        if (enable) {
            mLoadingRoot.setVisibility(View.VISIBLE);
        } else {
            mLoadingRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void showView(HelpDataEntity data) {
        mTvTitle.setText(data.getTitle());
        mTvHelpInfoContent.setText(formatStr(R.string.text_two_empty_format, data.getContent()));
    }

    private String formatStr(int baseStrId, String child) {
        String local = getString(baseStrId);
        return String.format(local, child);
    }

    @OnClick(R.id.im_back_arrow)
    public void onViewClicked() {
        finish();
    }
}
