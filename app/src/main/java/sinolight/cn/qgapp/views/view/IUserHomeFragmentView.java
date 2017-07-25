package sinolight.cn.qgapp.views.view;



import sinolight.cn.qgapp.data.http.entity.UserEntity;

/**
 * Created by xns on 2017/7/5.
 * UserHomeFragment View层接口
 */

public interface IUserHomeFragmentView {

    void showErrorToast(int msgId);

    void init2Show(UserEntity userData);

}
