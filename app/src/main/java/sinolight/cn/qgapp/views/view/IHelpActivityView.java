package sinolight.cn.qgapp.views.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

/**
 * Created by xns on 2017/9/4.
 * Help Activity View frame
 */

public interface IHelpActivityView {

    void showToastByRes(int strID);

    void showToastByStr(String msg);

    void showRefreshing(boolean enable);

    void gotoActivity(Intent callIntent);

    void showListView(RecyclerView.Adapter adapter);
}
