package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.AboutActivityPresenter;
import sinolight.cn.qgapp.presenter.SystemActivityPresenter;
import sinolight.cn.qgapp.views.view.IAboutActivityView;
import sinolight.cn.qgapp.views.view.ISystemActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class AboutActivityModule {
    private IAboutActivityView view;

    public AboutActivityModule(IAboutActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IAboutActivityView provideIAboutActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    AboutActivityPresenter provideAboutActivityPresenter(IAboutActivityView view, Context context) {
        return new AboutActivityPresenter(context, view);
    }

}
