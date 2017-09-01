package sinolight.cn.qgapp.views.view;


import android.text.Spanned;

import sinolight.cn.qgapp.data.http.entity.ReaderEntity;

/**
 * Created by xns on 2017/6/29.
 * 阅读界面
 */

public interface IReadActivityView {

    void showToast(int msgId);

    void showRefreshing(boolean enable);

    void showData(ReaderEntity readData);

    void showReadContent(Spanned spanned);

    void showFooter(String name);

    void showStrToast(String msg);

    void setCollectState(boolean enable);

    void showCollectBtn(boolean enable);
}
