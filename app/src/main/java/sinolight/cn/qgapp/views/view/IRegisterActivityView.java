package sinolight.cn.qgapp.views.view;

import android.support.annotation.Nullable;

/**
 * Created by xns on 2017/6/29.
 * RegisterActivity View
 */

public interface IRegisterActivityView {

    void initShow(@Nullable String vCode);

    void showToastMsg(int msgId);
}
