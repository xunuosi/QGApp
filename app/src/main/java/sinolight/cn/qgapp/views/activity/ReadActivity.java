package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerReadActivityComponent;
import sinolight.cn.qgapp.dagger.module.ReadActivityModule;
import sinolight.cn.qgapp.data.http.entity.ReaderEntity;
import sinolight.cn.qgapp.presenter.ReadActivityPresenter;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.view.IReadActivityView;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

/**
 * Created by xns on 2017/8/2.
 * ReadActivity
 */

public class ReadActivity extends BaseActivity implements IReadActivityView {
    @Inject
    Context mContext;
    @Inject
    ReadActivityPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_read_info)
    Toolbar mTbReadInfo;
    @BindView(R.id.tv_read_content)
    TextView mTvReadContent;
    @BindView(R.id.tv_read_footer)
    TextView mTvReadFooter;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;


    public static Intent getCallIntent(Context context) {
        return new Intent(context, ReadActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.checkoutIntent(getIntent());
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_read;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerReadActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .readActivityModule(new ReadActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        this.showStrToast(msg);
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
    public void showData(ReaderEntity readData) {
        mTvTitle.setText(readData.getTitle());
        this.setCollectState(readData.isfavor());
        mTvReadContent.setText(inflateHtmlData(readData.getHtml()));
    }

    /**
     * 解析html中包含的图片
     *
     * @param html
     * @return
     */
    private Spanned inflateHtmlData(String html) {
        Spanned sp = null;
        if (Build.VERSION.SDK_INT >= 24) {
            sp = Html.fromHtml(html, FROM_HTML_MODE_COMPACT, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);

                    int width = ScreenUtil.getScreenWidth2Dp(mContext) - 32;
                    int height = (int) (getResources().getDimensionPixelOffset(R.dimen.cook_info_item_image_height) /
                            getResources().getDisplayMetrics().density);

                    Uri uri = Uri.parse(source);

                    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                            .setResizeOptions(new ResizeOptions(
                                    ScreenUtil.dip2px(mContext, width), ScreenUtil.dip2px(mContext, height)))
                            .build();

                    PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(simpleDraweeView.getController())
                            .setControllerListener(new BaseControllerListener<ImageInfo>())
                            .build();
                    simpleDraweeView.setController(controller);
                    simpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);

                    Drawable drawable = simpleDraweeView.getDrawable();
                    drawable.setBounds(0, 0, ScreenUtil.dip2px(mContext, width), ScreenUtil.dip2px(mContext, height));
                    return drawable;
                }
            }, null);
        } else {
            sp = Html.fromHtml(html, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);

                    int width = ScreenUtil.getScreenWidth2Dp(mContext) - 32;
                    int height = (int) (getResources().getDimensionPixelOffset(R.dimen.cook_info_item_image_height) /
                            getResources().getDisplayMetrics().density);

                    Uri uri = Uri.parse(source);

                    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                            .setResizeOptions(new ResizeOptions(
                                    ScreenUtil.dip2px(mContext, width), ScreenUtil.dip2px(mContext, height)))
                            .build();

                    PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                            .setImageRequest(request)
                            .setOldController(simpleDraweeView.getController())
                            .setControllerListener(new BaseControllerListener<ImageInfo>())
                            .build();
                    simpleDraweeView.setController(controller);
                    simpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);

                    Drawable drawable = simpleDraweeView.getDrawable();
                    drawable.setBounds(0, 0, ScreenUtil.dip2px(mContext, width), ScreenUtil.dip2px(mContext, height));
                    return drawable;
                }
            }, null);
        }

        return sp;
    }

    @Override
    public void showFooter(String name) {
        mTvReadFooter.setText(name);
    }

    @Override
    public void setCollectState(boolean enable) {
        if (enable) {
            ivCollect.setImageDrawable(getDrawable(R.drawable.ic_icon_collected));
        } else {
            ivCollect.setImageDrawable(getDrawable(R.drawable.icon_collect));
        }
    }

    @Override
    public void showCollectBtn(boolean enable) {
        if (enable) {
            ivCollect.setVisibility(View.VISIBLE);
        } else {
            ivCollect.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_collect:
                mPresenter.collectRes();
                break;
        }
    }
}
