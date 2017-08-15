package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.SystemActivityPresenter;
import sinolight.cn.qgapp.views.view.ISystemActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class SysActivityModule {
    private ISystemActivityView view;

    public SysActivityModule(ISystemActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    ISystemActivityView provideISystemActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    SystemActivityPresenter provideSystemActivityPresenter(ISystemActivityView view, Context context) {
        return new SystemActivityPresenter(context, view);
    }

}
