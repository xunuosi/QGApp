package sinolight.cn.qgapp;

import android.content.Context;

import sinolight.cn.qgapp.utils.SharedPfUtil;

/**
 * Created by xns on 2017/7/4.
 * App数据读取
 */

public class AppModel {
    private Context mContext;

    public AppModel(Context context) {
        mContext = context;
    }

    public String getCurrentUserName() {
        return SharedPfUtil.getParam(mContext, AppContants.Account.USER_NAME, "String");
    }

    public void setCurrentUserName(String userName) {
        SharedPfUtil.setParam(mContext, AppContants.Account.USER_NAME, userName);
    }

    public String getCurrentToken() {
        return SharedPfUtil.getParam(mContext, AppContants.Account.TOKEN, "String");
    }

    public void setCurrentToken(String token) {
        SharedPfUtil.setParam(mContext, AppContants.Account.TOKEN, token);
    }
}
