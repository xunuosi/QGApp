package sinolight.cn.qgapp.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

import org.xml.sax.XMLReader;

import java.util.Locale;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.views.activity.PictureDisplayActivity;

/**
 * Created by xns on 2017/9/5.
 * Img Tag Handler
 */

public class ImgTagHandler implements Html.TagHandler {

    private Context mContext;
    private OnImgClickListener mListener;

    public ImgTagHandler(Context context, OnImgClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable editable, XMLReader xmlReader) {
        // 处理标签<img>
        if (tag.toLowerCase(Locale.getDefault()).equals("img")) {
            // 获取长度
            int len = editable.length();
            // 获取图片地址
            ImageSpan[] images = editable.getSpans(len - 1, len, ImageSpan.class);
            String imgUrl = images[0].getSource();

            // 使图片可点击并监听点击事件
            editable.setSpan(new ClickableImage(mContext, imgUrl), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private class ClickableImage extends ClickableSpan {

        private String url;
        private Context context;

        public ClickableImage(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            // 进行图片点击之后的处理
            mListener.onImgOnClick(widget, url);
        }

    }

    public interface OnImgClickListener {
        void onImgOnClick(View view, String imgUrl);
    }
}
