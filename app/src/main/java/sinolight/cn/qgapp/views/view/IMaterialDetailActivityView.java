package sinolight.cn.qgapp.views.view;

/**
 * Created by xns on 2017/8/4.
 * 菜谱详情的View层
 */

public interface IMaterialDetailActivityView {

    void showToast(int msgId);

    void showRefreshing(boolean enable);

    void showView();
}
