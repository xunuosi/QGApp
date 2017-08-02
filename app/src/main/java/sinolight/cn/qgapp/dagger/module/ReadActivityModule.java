package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.ReadActivityPresenter;
import sinolight.cn.qgapp.views.view.IReadActivityView;

/**
 * Created by xns on 2017/6/29.
 * ReadActivityModule
 */
@Module
public class ReadActivityModule {
    private IReadActivityView view;

    public ReadActivityModule(IReadActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IReadActivityView provideIReadActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    ReadActivityPresenter provideReadActivityPresenter(IReadActivityView view, Context context) {
        return new ReadActivityPresenter(view, context);
    }

}
