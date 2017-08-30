package sinolight.cn.qgapp.presenter;

import android.content.Context;

import java.util.List;

import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.views.view.ISearchDisplayActivityView;

/**
 * Created by xns on 2017/8/29.
 * Search Presenter
 */

public class SearchDisplayActivityPresenter extends BasePresenter<ISearchDisplayActivityView, DaoSession> {
    private Context mContext;

    public SearchDisplayActivityPresenter(Context context, ISearchDisplayActivityView view, DaoSession daoSession) {
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
    }

    public void init2Show() {
        // load search history
        List<String> data = AppHelper.getInstance().getSearchDatas();
        if (data != null && !data.isEmpty()) {
            view().loadSearchDataHistory(data);
        }

    }
}
