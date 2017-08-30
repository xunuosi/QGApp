package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.SearchDisplayActivity;
import sinolight.cn.qgapp.views.view.ISearchActivityView;

/**
 * Created by xns on 2017/8/29.
 * Search Presenter
 */

public class SearchActivityPresenter extends BasePresenter<ISearchActivityView, DaoSession> {
    private static final String TAG = "SearchActivityPresenter";
    private Context mContext;
    // Default value DB_FOOD
    private AppContants.DataBase.Type mDataBaseType = AppContants.DataBase.Type.DB_FOOD;
    private AppContants.DataBase.Type[] dbArr = {
            AppContants.DataBase.Type.DB_FOOD,
            AppContants.DataBase.Type.DB_ART,
            AppContants.DataBase.Type.DB_PAPER,
            AppContants.DataBase.Type.DB_LEATHER,
            AppContants.DataBase.Type.DB_FURNITURE,
            AppContants.DataBase.Type.DB_PACK,
            AppContants.DataBase.Type.DB_CLOTHING,
            AppContants.DataBase.Type.DB_ELECTROMECHANICAL,
            AppContants.DataBase.Type.DB_WEIGHING

    };

    public SearchActivityPresenter(Context context, ISearchActivityView view, DaoSession daoSession) {
        mContext = context;
        bindView(view);
        setModel(daoSession);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        unbindView();
    }

    public void queryData(String key) {
        // save db
        AppHelper.getInstance().saveSearchData(key);
        // goto display activity
        Intent callIntent = SearchDisplayActivity.getCallIntent(mContext);
        callIntent.putExtra(AppContants.Search.SEARCH_KEY, key);
        callIntent.putExtra(AppContants.Search.SEARCH_DB_TYPE, mDataBaseType);
        view().gotoActivity(callIntent);
    }

    public void init2Show() {
        loadSearchHistory();
    }

    public void loadSearchHistory() {
        // load search history
        List<String> data = AppHelper.getInstance().getSearchDatas();
        if (data != null && !data.isEmpty()) {
            view().loadSearchDataHistory(data);
        }
    }

    public void chooseDataBase(int dbIndex) {
        mDataBaseType = dbArr[dbIndex];
    }
}
