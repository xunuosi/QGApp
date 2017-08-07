package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.adapter.CookAdapter;
import sinolight.cn.qgapp.data.http.entity.CookContentEntity;
import sinolight.cn.qgapp.data.http.entity.CookEntity;

/**
 * Created by xns on 2017/8/4.
 * 菜谱详情的View层
 */

public interface IMaterialDetailActivityView {

    void showRefreshView(boolean enable);

    void showToast(int msgId);

    void showListView(CookAdapter adapter);

    void showTitle(String title);
}
