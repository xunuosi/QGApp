package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.data.http.entity.AboutEntity;

/**
 * Created by xns on 2017/8/15.
 * AboutActivity View Frame
 */

public interface IAboutActivityView  {

    void showToast(int msgID);

    void showToast(String msg);

    void showView(AboutEntity data);
}
