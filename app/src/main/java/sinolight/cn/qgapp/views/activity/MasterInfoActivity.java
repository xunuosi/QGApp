package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerMasterInfoActivityComponent;
import sinolight.cn.qgapp.dagger.module.MasterInfoActivityModule;
import sinolight.cn.qgapp.data.http.entity.MasterEntity;
import sinolight.cn.qgapp.presenter.MasterInfoActivityPresenter;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.views.view.IMasterInfoActivityView;

/**
 * Created by xns on 2017/8/10.
 * 素材详情
 */

public class MasterInfoActivity extends BaseActivity implements IMasterInfoActivityView {
    @Inject
    Context mContext;
    @Inject
    MasterInfoActivityPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_master_info)
    Toolbar mTbMasterInfo;
    @BindView(R.id.iv_master_info)
    SimpleDraweeView mIvMasterInfo;
    @BindView(R.id.tv_master_info_name)
    TextView mTvMasterInfoName;
    @BindView(R.id.tv_master_info_product)
    TextView mTvMasterInfoProduct;
    @BindView(R.id.tv_master_info_content)
    TextView mTvMasterInfoContent;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, MasterInfoActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.checkoutIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_master_info;
    }

    @Override
    protected void initViews() {
        mTvTitle.setText(R.string.text_master_store);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerMasterInfoActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .masterInfoActivityModule(new MasterInfoActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
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
    public void init2show(MasterEntity masterData) {
        int width = (int) (getResources().getDimensionPixelOffset(R.dimen.master_info_iv_width) /
                getResources().getDisplayMetrics().density);
        int height = (int) (getResources().getDimensionPixelOffset(R.dimen.master_info_iv_height) /
               getResources().getDisplayMetrics().density);

        ImageUtil.frescoShowImageByUri(
                mContext,
                masterData.getCover(),
                mIvMasterInfo,
                width,
                height
        );

        mTvMasterInfoName.setText(masterData.getName());
        mTvMasterInfoProduct.setText(masterData.getWorks());
        mTvMasterInfoContent.setText(formatStr(R.string.text_two_empty_format, masterData.getAbs()));
        mTvMasterInfoContent.setMovementMethod(ScrollingMovementMethod.getInstance());

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
