package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.views.activity.SearchDisplayActivity;
import sinolight.cn.qgapp.views.view.ISearchActivityView;

/**
 * Created by xns on 2017/8/29.
 * Search Presenter
 */

public class SearchActivityPresenter extends BasePresenter<ISearchActivityView, DaoSession> {
    private Context mContext;

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
}
