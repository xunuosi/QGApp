package sinolight.cn.qgapp.views.view;

import android.content.Intent;

import sinolight.cn.qgapp.adapter.HomeAdapter;

/**
 * Created by xns on 2017/7/5.
 * HomeFragment View层接口
 */

public interface IHomeFragmentView {

    void showView(HomeAdapter adapter);

    void showLoading(boolean enable);

    void gotoActivity(Intent callIntent);

    void showMsgByStr(String msg);
}
