package sinolight.cn.qgapp.data.bean;

import sinolight.cn.qgapp.AppContants;

/**
 * Created by xns on 2017/7/5.
 * 本地资源的实体类
 */

public class LocalDataBean {
    private int text;
    private int resId;
    private AppContants.HomeStore.Type mHomeStoreType;

    public LocalDataBean() {
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public AppContants.HomeStore.Type getHomeStoreType() {
        return mHomeStoreType;
    }

    public void setHomeStoreType(AppContants.HomeStore.Type homeStoreType) {
        mHomeStoreType = homeStoreType;
    }

}
