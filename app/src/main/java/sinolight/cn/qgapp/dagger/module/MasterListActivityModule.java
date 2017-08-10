package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.MasterListActivityPresenter;
import sinolight.cn.qgapp.views.view.IMasterListActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class MasterListActivityModule {
    private IMasterListActivityView view;

    public MasterListActivityModule(IMasterListActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IMasterListActivityView provideIMasterListActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    MasterListActivityPresenter provideMasterListActivityPresenter(IMasterListActivityView view, Context context) {
        return new MasterListActivityPresenter(view, context);
    }

}
