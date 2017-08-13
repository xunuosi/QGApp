package sinolight.cn.qgapp.views.view;


import sinolight.cn.qgapp.data.http.entity.BookInfoEntity;

/**
 * Created by xns on 2017/6/29.
 * This is knowledge database view interface about resource of book.
 */

public interface IKDBBookDetailActivityView {

    void showRefreshing(boolean enable);

    void showErrorToast(int msgId);

    void init2Show(BookInfoEntity dicData);

    void showStrToast(String msg);

    void setCollectState(boolean enable);
}
