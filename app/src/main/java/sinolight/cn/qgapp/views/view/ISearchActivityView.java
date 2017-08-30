package sinolight.cn.qgapp.views.view;

import android.content.Intent;

import java.util.List;

/**
 * Created by xns on 2017/8/29.
 * SearchActivity View
 */

public interface ISearchActivityView {

    void showToast(int msgId);

    void showStrToast(String msg);

    void gotoActivity(Intent intent);

    void loadSearchDataHistory(List<String> data);
}
