package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.MyDatabaseActivityPresenter;
import sinolight.cn.qgapp.views.view.IMyDatabaseActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class MyDataBaseActivityModule {
    private IMyDatabaseActivityView view;

    public MyDataBaseActivityModule(IMyDatabaseActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IMyDatabaseActivityView provideIMyDatabaseActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    MyDatabaseActivityPresenter provideMyDatabaseActivityPresenter(IMyDatabaseActivityView view, Context context) {
        return new MyDatabaseActivityPresenter(context, view);
    }

}
