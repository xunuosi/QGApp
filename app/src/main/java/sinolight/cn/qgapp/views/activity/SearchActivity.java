package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.dagger.component.DaggerSearchActivityComponent;
import sinolight.cn.qgapp.dagger.module.SearchActivityModule;
import sinolight.cn.qgapp.presenter.SearchActivityPresenter;
import sinolight.cn.qgapp.views.view.ISearchActivityView;

/**
 * Created by xns on 2017/8/29.
 * Search Activity
 */

public class SearchActivity extends BaseActivity implements ISearchActivityView {
    @Inject
    Context mContext;
    @Inject
    SearchActivityPresenter mPresenter;
    @Inject
    ArrayAdapter<CharSequence> spinnerAdapter;

    @BindView(R2.id.tb_search)
    Toolbar mTbSearch;
    @BindView(R2.id.spinner)
    Spinner mSpinner;
    @BindView(R2.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initViews() {
        mSpinner.setAdapter(spinnerAdapter);
        mTvTitle.setText(R.string.text_search);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerSearchActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .searchActivityModule(new SearchActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        this.showStrToast(msg);
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotoActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void showLoading(boolean enable) {
        if (enable) {
            mLoadingRoot.setVisibility(View.VISIBLE);
        } else {
            mLoadingRoot.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.im_back_arrow)
    public void onViewClicked() {
        finish();
    }
}
