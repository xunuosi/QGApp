package sinolight.cn.qgapp.data.bean;

import android.view.View;

/**
 * Created by xns on 2017/9/22.
 * onClick to display img event
 */

public class EventImg {
    private String url;
    private View mView;

    public EventImg() {
    }

    public EventImg(String url, View view) {
        this.url = url;
        mView = view;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }
}
