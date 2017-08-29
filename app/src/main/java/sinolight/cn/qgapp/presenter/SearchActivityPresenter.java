package sinolight.cn.qgapp.presenter;

import android.content.Context;

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
}
