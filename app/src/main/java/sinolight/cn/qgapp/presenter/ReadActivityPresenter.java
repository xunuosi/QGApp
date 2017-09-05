package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.ReaderEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.ImgTagHandler;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.activity.ReadActivity;
import sinolight.cn.qgapp.views.view.IReadActivityView;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

/**
 * Created by xns on 2017/6/29.
 * VideoInfo Presenter
 */

public class ReadActivityPresenter extends BasePresenter<IReadActivityView, HttpManager> {
    private static final String TAG = "ReadActivityPresenter";
    private Context mContext;

    // VideoParentID
    private String readID;
    private AppContants.Read.Type readResType;
    private String chapteredID;
    private ReaderEntity readData;
    private String footerName;
    private Spanned spanned;
    private ReadActivity mReadActivity;

    private HttpSubscriber<ReaderEntity> mReadObserver = new HttpSubscriber<>(
            new OnResultCallBack<ReaderEntity>() {

                @Override
                public void onSuccess(ReaderEntity readerEntity) {
                    if (readerEntity != null) {
                        readData = readerEntity;
                        showSuccess();
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mReadObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
                }
            });

    private void showSuccess() {
        showWithData();
    }

    private void showError() {
        view().showRefreshing(false);
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showWithData() {
        spanned = this.inflateHtmlData(readData.getHtml());
        view().showData(readData);
        view().showReadContent(spanned);
        view().showFooter(footerName);
        this.closeRefreshing();
    }

    public ReadActivityPresenter(IReadActivityView view, Context context) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        if (mReadObserver != null) {
            mReadObserver.unSubscribe();
        }

        mCollectObserver.unSubscribe();
        mReadActivity = null;

        KDBResDataMapper.reset();
        unbindView();
    }

    private void closeRefreshing() {
        view().showRefreshing(false);
    }

    private void showErrorToast(int resId) {
        view().showToast(resId);
        view().showRefreshing(false);
    }

    public void init2Show() {
        resetState();
        // load video set data
        getData();
    }

    private void getData() {
        model.doReadNoCache(
                mReadObserver,
                AppHelper.getInstance().getCurrentToken(),
                readResType.getType(),
                readID,
                chapteredID
        );
    }

    /**
     * 重置状态
     */
    @Override
    protected void resetState() {
        super.resetState();
    }

    public void checkoutIntent(Intent intent, ReadActivity readActivity) {
        if (intent == null) {
            view().showRefreshing(false);
        } else {
            mReadActivity = readActivity;
            footerName = intent.getStringExtra(AppContants.Read.READ_NAME);
            readID = intent.getStringExtra(AppContants.Read.READ_ID);
            chapteredID = intent.getStringExtra(AppContants.Read.CHAPTERED_ID);
            readResType = (AppContants.Read.Type) intent.getSerializableExtra(AppContants.Read.READ_RES_TYPE);
            view().showRefreshing(true);
            // choose whether or not to display collect button
            changeCollectBtnState();
            // load data
            this.getData();
        }
    }

    private void changeCollectBtnState() {
        switch (readResType) {
            case TYPE_INDUSTRY:
            case TYPE_ARTICLE:
                view().showCollectBtn(true);
                break;
            default:
                view().showCollectBtn(false);
                break;
        }
    }

    private HttpSubscriber<Object> mCollectObserver = new HttpSubscriber<>(
            new OnResultCallBack<Object>() {

                @Override
                public void onSuccess(Object o) {
                    showErrorToast(R.string.text_collect_success);
                    view().showRefreshing(false);
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mCollectObserver code:" + code + ",errorMsg:" + errorMsg);
                    checkoutCollectState(code, errorMsg);
                }
            });

    private void checkoutCollectState(int code, String errorMsg) {
        if (code == AppContants.SUCCESS_CODE) {
            getData();
            view().showStrToast(errorMsg);
        } else {
            showError();
        }
    }

    public void collectRes() {
        model.collectResNoCache(
                mCollectObserver,
                AppHelper.getInstance().getCurrentToken(),
                readResType.getType(),
                readID,
                getAction()
        );
    }

    private int getAction() {
        if (readData.isfavor()) {
            return AppContants.Collect.ACTION_UNCOLLECT;
        } else {
            return AppContants.Collect.ACTION_COLLECT;
        }
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
                    int height = (int) (mContext.getResources().getDimensionPixelOffset(R.dimen.cook_info_item_image_height) /
                            mContext.getResources().getDisplayMetrics().density);

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
            }, new ImgTagHandler(mContext, mReadActivity));
        } else {
            sp = Html.fromHtml(html, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);

                    int width = ScreenUtil.getScreenWidth2Dp(mContext) - 32;
                    int height = (int) (mContext.getResources().getDimensionPixelOffset(R.dimen.cook_info_item_image_height) /
                            mContext.getResources().getDisplayMetrics().density);

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
            }, new ImgTagHandler(mContext, mReadActivity));
        }

        return sp;
    }


}
