package sinolight.cn.qgapp.views.view;


import sinolight.cn.qgapp.data.http.entity.MasterEntity;

/**
 * Created by xns on 2017/6/29.
 * 资源库视频集合界面
 */

public interface IMasterInfoActivityView {

    void showToast(int msgId);

    void showRefreshing(boolean enable);

    void init2show(MasterEntity masterData);

}
