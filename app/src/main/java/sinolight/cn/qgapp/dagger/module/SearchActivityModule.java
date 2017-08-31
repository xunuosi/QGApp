package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.presenter.SearchActivityPresenter;
import sinolight.cn.qgapp.views.view.ISearchActivityView;

/**
 * Created by xns on 2017/6/29.
 * HomeActivity的Module
 */
@Module
public class SearchActivityModule {
    private ISearchActivityView view;

    public SearchActivityModule(ISearchActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    ISearchActivityView provideISearchActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    SearchActivityPresenter provideSearchActivityPresenter(Context context, ISearchActivityView view, DaoSession daoSession) {
        return new SearchActivityPresenter(context, view, daoSession);
    }
}
