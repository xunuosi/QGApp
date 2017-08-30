package sinolight.cn.qgapp.presenter;

import android.content.Context;

import java.util.List;

import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.data.db.DaoSession;
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

    }

    public void queryData(String key) {
        // save db
        AppHelper.getInstance().saveSearchData(key);
        // goto display activity

    }

    public void init2Show() {
        // load search history
        List<String> data = AppHelper.getInstance().getSearchDatas();
        if (data != null && !data.isEmpty()) {
            view().loadSearchDataHistory(data);
        }

    }
}
