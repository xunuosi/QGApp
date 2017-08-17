package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.data.http.entity.ResImgEntity;

/**
 * Created by xns on 2017/8/17.
 * Pic detail view frame
 */

public interface IResPicDetailView {

    void showToast(int msgId);

    void showStrToast(String msg);

    void showView(ResImgEntity imgEntity);

    void showRefreshing(boolean enable);
}
