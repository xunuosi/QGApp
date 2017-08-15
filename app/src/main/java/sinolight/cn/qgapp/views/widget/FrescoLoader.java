package sinolight.cn.qgapp.views.widget;

import android.content.Context;
import android.graphics.PointF;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.loader.ImageLoader;

import sinolight.cn.qgapp.utils.ImageUtil;

/**
 * Created by xns on 2017/7/4.
 * Banner框架使用Fresco框架时图片加载类
 */

public class FrescoLoader extends ImageLoader {
    private int width;
    private int height;
    // 是否需要裁剪
    private boolean isCut;

    public FrescoLoader(int width, int height, boolean isCut) {
        this.width = width;
        this.height = height;
        this.isCut = isCut;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        if (path instanceof Integer) {
            ImageUtil.frescoShowImageByResId(context, (int)path, (SimpleDraweeView)imageView, width, height);
        } else if (path instanceof String) {
            if (isCut) {
                ImageUtil.frescoShowBannerByUri(
                        context,
                        (String) path,
                        (SimpleDraweeView) imageView,
                        0.2f,
                        0,
                        0.6f,
                        1f,
                        width,
                        height);
            } else {
                ImageUtil.frescoShowImageByUri(
                        context,
                        (String) path,
                        (SimpleDraweeView) imageView,
                        width,
                        height
                );
            }
        }
//        //用fresco加载图片
//        Uri uri = Uri.parse((String) path);
//        imageView.setImageURI(uri);

    }

    //提供createImageView 方法，方便fresco自定义ImageView
    @Override
    public ImageView createImageView(Context context) {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);

        simpleDraweeView.getHierarchy().setActualImageFocusPoint(new PointF(0f,0f));
        simpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
        return simpleDraweeView;
    }
}