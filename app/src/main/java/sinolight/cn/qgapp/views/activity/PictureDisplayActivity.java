package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.samples.zoomable.ZoomableDraweeView;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.ScreenUtil;

/**
 * Created by xns on 2017/9/5.
 * Pic display activity
 */

public class PictureDisplayActivity extends BaseActivity {
    public static final String TRANSIT_PIC = "picture";

    @BindView(R.id.iv_pic_display)
    ZoomableDraweeView mIvPicDisplay;
    @BindView(R.id.root_ac_pic_display)
    ConstraintLayout mRootAcPicDisplay;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, PictureDisplayActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String imgUrl = intent.getStringExtra(AppContants.Img.IMG_URL);
            if (imgUrl != null) {
                loadImg(imgUrl);
            }
        }
    }

    private void loadImg(String imgUrl) {
        int width = ScreenUtil.getScreenWidth2Dp(PictureDisplayActivity.this);
        int height = (int) (getResources().getDimensionPixelOffset(R.dimen.pic_display_height) /
                getResources().getDisplayMetrics().density);

        mIvPicDisplay.getHierarchy().setActualImageFocusPoint(new PointF(0f, 0f));
        mIvPicDisplay.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);

        ImageUtil.zoomableViewShowImageByUri(
                PictureDisplayActivity.this,
                imgUrl,
                mIvPicDisplay,
                width,
                height
        );
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_pic_display;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {

    }

    @OnClick(R.id.root_ac_pic_display)
    public void onViewClicked() {
        finish();
    }
}
