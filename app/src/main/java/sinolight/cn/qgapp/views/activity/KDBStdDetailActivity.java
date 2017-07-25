package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerKDBStdActivityComponent;
import sinolight.cn.qgapp.dagger.module.KDBStdActivityModule;
import sinolight.cn.qgapp.data.http.entity.StdInfoEntity;
import sinolight.cn.qgapp.presenter.KDBStdActivityPresenter;
import sinolight.cn.qgapp.views.view.IKDBStdDetailActivityView;

/**
 * Created by xns on 2017/7/25.
 * 标准详情页
 */

public class KDBStdDetailActivity extends BaseActivity implements IKDBStdDetailActivityView {
    @Inject
    Context mContext;
    @Inject
    KDBStdActivityPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_kdb_std_detail)
    Toolbar mTbKdbStdDetail;
    @BindView(R.id.iv_kdb_std_cover)
    SimpleDraweeView mIvKdbStdCover;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R.id.tv_kdb_std_title)
    TextView mTvKdbStdTitle;
    @BindView(R.id.tv_kdb_std_pub_name)
    TextView mTvKdbStdPubName;
    @BindView(R.id.tv_kdb_std_pub_time)
    TextView mTvKdbStdPubTime;
    @BindView(R.id.tl_kdb_std_detail)
    TabLayout mTlKdbStdDetail;
    @BindView(R.id.vp_kdb_std_detail)
    ViewPager mVpKdbStdDetail;
    @BindView(R.id.tv_kdb_std_from)
    TextView mTvKdbStdFrom;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, KDBStdDetailActivity.class);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.init2show(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_kdb_std_detail;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerKDBStdActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .kDBStdActivityModule(new KDBStdActivityModule(this))
                .build()
                .inject(this);
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
    public void showErrorToast(int msgId) {

    }

    @Override
    public void init2Show(StdInfoEntity stdData) {

    }

    @OnClick({R.id.im_back_arrow, R.id.iv_collect, R.id.btn_kdb_std_read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                break;
            case R.id.iv_collect:
                break;
            case R.id.btn_kdb_std_read:
                break;
        }
    }
}
