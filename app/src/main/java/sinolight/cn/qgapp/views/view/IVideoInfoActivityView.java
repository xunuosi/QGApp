package sinolight.cn.qgapp.views.view;


import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;

/**
 * Created by xns on 2017/6/29.
 * 资源库视频集合界面
 */

public interface IVideoInfoActivityView {

    void showToast(int msgId);

    void showRefreshing(boolean enable);

    void initVideo(DBResVideoEntity videoData);

    void setCollectState(boolean enable);

    void showStrToast(String msg);

}
