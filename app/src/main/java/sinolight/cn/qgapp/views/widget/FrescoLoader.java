package sinolight.cn.qgapp.views.widget;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    public FrescoLoader(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        if (path instanceof Integer) {
            ImageUtil.frescoShowImageByResId(context, (int)path, (SimpleDraweeView)imageView, width, height);
        } else if (path instanceof String) {
            ImageUtil.frescoShowImageByUri(context, (String)path, (SimpleDraweeView)imageView, width, height);
        }
//        //用fresco加载图片
//        Uri uri = Uri.parse((String) path);
//        imageView.setImageURI(uri);

    }

    //提供createImageView 方法，方便fresco自定义ImageView
    @Override
    public ImageView createImageView(Context context) {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
        return simpleDraweeView;
    }
}