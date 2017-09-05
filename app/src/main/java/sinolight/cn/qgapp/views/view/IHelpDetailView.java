package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.data.http.entity.HelpDataEntity;

/**
 * Created by xns on 2017/9/5.
 * HelpDetail View frame
 */

public interface IHelpDetailView {

    void showToastByRes(int strID);

    void showToastByStr(String msg);

    void showRefreshing(boolean enable);

    void showView(HelpDataEntity data);
}
