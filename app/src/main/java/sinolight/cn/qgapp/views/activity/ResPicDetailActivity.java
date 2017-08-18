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

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.dagger.component.DaggerPicDetailActivityComponent;
import sinolight.cn.qgapp.dagger.module.PicDetailActivityModule;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.presenter.PicDetailActivityPresenter;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.view.IResPicDetailView;

/**
 * Created by xns on 2017/8/17.
 * Pic detail activity
 */

public class ResPicDetailActivity extends BaseActivity implements IResPicDetailView {

    @Inject
    Context mContext;
    @Inject
    PicDetailActivityPresenter mPresenter;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tool_bar_pic_info)
    Toolbar mToolBarPicInfo;
    @BindView(R2.id.iv_pic_detail_content)
    SimpleDraweeView mIvPicDetailContent;
    @BindView(R2.id.tv_pic_detail_abs)
    TextView mTvPicDetailAbs;
    @BindView(R2.id.loading_root)
    RelativeLayout mLoadingRoot;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, ResPicDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.checkIntent(getIntent());
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_pic_detail;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerPicDetailActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .picDetailActivityModule(new PicDetailActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.im_back_arrow)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        showStrToast(msg);
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showView(ResImgEntity imgEntity) {
        mTvTitle.setText(imgEntity.getTitle());

        int width = ScreenUtil.getScreenWidth2Dp(mContext);
        int height = (int) (getResources().getDimensionPixelOffset(
                R.dimen.video_set_img_height) / getResources().getDisplayMetrics().density);
        ImageUtil.frescoShowImageByUri(
                mContext,
                imgEntity.getCover(),
                mIvPicDetailContent,
                width,
                height
        );

        mTvPicDetailAbs.setText(imgEntity.getAbs());
    }

    @Override
    public void showRefreshing(boolean enable) {
        if (enable) {
            mLoadingRoot.setVisibility(View.VISIBLE);
        } else {
            mLoadingRoot.setVisibility(View.GONE);
        }
    }
}
