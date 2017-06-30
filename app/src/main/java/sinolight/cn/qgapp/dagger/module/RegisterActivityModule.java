package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.presenter.RegisterActivityPresenter;
import sinolight.cn.qgapp.views.view.IRegisterActivityView;

/**
 * Created by xns on 2017/6/29.
 * HomeActivity的Module
 */
@Module
public class RegisterActivityModule {
    private IRegisterActivityView view;

    public RegisterActivityModule(IRegisterActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IRegisterActivityView provideIRegisterActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    RegisterActivityPresenter provideITMDetailPresenter(IRegisterActivityView view, Context context, DaoSession daoSession) {
        return new RegisterActivityPresenter(view, daoSession, context);
    }

}
