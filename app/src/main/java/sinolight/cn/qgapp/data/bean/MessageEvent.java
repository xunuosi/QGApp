package sinolight.cn.qgapp.data.bean;

import sinolight.cn.qgapp.AppContants;

/**
 * Created by xns on 2017/8/2.
 * EventBus post object
 */

public class MessageEvent {
    private AppContants.HomeStore.Type homeStoreType;

    public MessageEvent() {
    }

    public MessageEvent(AppContants.HomeStore.Type homeStoreType) {
        this.homeStoreType = homeStoreType;
    }

    public AppContants.HomeStore.Type getHomeStoreType() {
        return homeStoreType;
    }

    public void setHomeStoreType(AppContants.HomeStore.Type homeStoreType) {
        this.homeStoreType = homeStoreType;
    }
}
