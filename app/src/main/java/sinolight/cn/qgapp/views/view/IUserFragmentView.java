package sinolight.cn.qgapp.views.view;


import android.content.Intent;

import sinolight.cn.qgapp.data.http.entity.UserEntity;

/**
 * Created by xns on 2017/7/5.
 * UserFragment View层接口
 */

public interface IUserFragmentView {

    void showErrorToast(int msgId);

    void init2Show(UserEntity userData);

    void gotoActivity(Intent callIntent);
}
