package sinolight.cn.qgapp;

import android.content.Context;

import java.util.List;

import sinolight.cn.qgapp.data.bean.SearchData;
import sinolight.cn.qgapp.data.db.GreenDaoHelper;
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

    public String getCurrentPW() {
        return SharedPfUtil.getParam(mContext, AppContants.Account.PASS_WORD, "String");
    }

    public void setCurrentPW(String pwd) {
        SharedPfUtil.setParam(mContext, AppContants.Account.PASS_WORD, pwd);
    }

    public void setSearchData(SearchData data) {
        GreenDaoHelper.getDaoSession().getSearchDataDao().insertOrReplace(data);
    }

    public List<SearchData> getSearchData() {
        return GreenDaoHelper.getDaoSession().getSearchDataDao().loadAll();
    }
}
