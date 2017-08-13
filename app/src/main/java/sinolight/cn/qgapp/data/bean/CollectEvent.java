package sinolight.cn.qgapp.data.bean;

import sinolight.cn.qgapp.AppContants;

/**
 * Created by admin on 2017/8/13.
 * Collect Event Bean
 */

public class CollectEvent {
    private String id;
    private AppContants.DataBase.Res resType;

    public CollectEvent() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AppContants.DataBase.Res getResType() {
        return resType;
    }

    public void setResType(AppContants.DataBase.Res resType) {
        this.resType = resType;
    }
}
