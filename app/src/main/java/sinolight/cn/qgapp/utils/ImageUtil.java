package sinolight.cn.qgapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by liubin on 2017/1/17.
 * 图片工具类
 */

public class ImageUtil {
    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final  String FIRE_DELIMITER = "/";

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;    //图片的原始高 宽
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //inJustDecodeBounds设置为true，可以不把图片读到内存中,但依然可以计算出图片的大小
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //此时返回的就是已经被压缩的bitmap。。可以用于显示在界面上
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * Fresco框架显示图片的方法
     * @param activity
     * @param path
     * @param view
     * @param width
     * @param height
     */
    public static void frescoShowImageByUri(Activity activity, String path, SimpleDraweeView view,
                                            int width, int height) {

        if (path == null || activity == null || view == null) {
            return;
        }
        if (width == 0 || height == 0) {
            width = 70;
            height = 70;
        }

        Uri uri = Uri.parse(path);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(
                        ScreenUtil.dip2px(activity, width), ScreenUtil.dip2px(activity, height)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(view.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();

        view.setController(controller);
    }

    /**
     * Fresco框架显示图片的方法
     * @param activity
     * @param resId
     * @param view
     * @param width
     * @param height
     */
    public static void frescoShowImageByResId(Activity activity, int resId, SimpleDraweeView view,
                                              int width, int height) {

        if (activity == null || view == null) {
            return;
        }
        if (width == 0 || height == 0) {
            width = 70;
            height = 70;
        }

        ImageRequest request = ImageRequestBuilder.newBuilderWithResourceId(resId)
                .setResizeOptions(new ResizeOptions(
                        ScreenUtil.dip2px(activity, width), ScreenUtil.dip2px(activity, height)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(view.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();

        view.setController(controller);
    }

    /**
     * Fresco框架显示图片的方法
     * @param activity
     * @param path
     * @param view
     * @param width
     * @param height
     */
    public static void frescoShowImageByUri2px(Activity activity, String path, SimpleDraweeView view,
                                               int width, int height) {

        if (path == null || activity == null || view == null) {
            return;
        }
        if (width == 0 || height == 0) {
            width = 70;
            height = 70;
        }

        Uri uri = Uri.parse(path);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(view.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();

        view.setController(controller);
    }

    /**
     * Resource ID 转换成Uri的方法
     * @param context
     * @param resId
     * @return
     */
    public static Uri resIdToUri(Context context, int resId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName()
                + FIRE_DELIMITER + resId);
    }

    /**
     * 设置Fresco为圆角的方法
     *
     * @param view:Fresco View
     * @param corner:圆角角度
     * @param borderColorId:边框颜色
     * @param borderWidth:边框宽度
     */
    public static void frescoShowCorner(SimpleDraweeView view, float corner, int borderColorId, float borderWidth) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(corner);
        roundingParams.setBorder(borderColorId, borderWidth);
        roundingParams.setRoundAsCircle(false);
        view.getHierarchy().setRoundingParams(roundingParams);
    }
}
