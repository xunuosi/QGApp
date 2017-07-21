package sinolight.cn.qgapp.views.view;

import sinolight.cn.qgapp.data.http.entity.DicInfoEntity;

/**
 * Created by xns on 2017/6/29.
 * This is knowledge database view interface about resource of dictionary.
 */

public interface IKDBDicDetailActivityView {

    void showRefreshing(boolean enable);

    void init2Show(DicInfoEntity dicData);
}
