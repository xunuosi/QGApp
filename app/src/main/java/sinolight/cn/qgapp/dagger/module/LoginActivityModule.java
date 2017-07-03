package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.presenter.LoginActivityPresenter;
import sinolight.cn.qgapp.views.view.ILoginActivityView;

/**
 * Created by xns on 2017/6/29.
 * LoginActivity的Module
 */
@Module
public class LoginActivityModule {
    private ILoginActivityView view;

    public LoginActivityModule(ILoginActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    ILoginActivityView provideIRegisterActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    LoginActivityPresenter provideITMDetailPresenter(ILoginActivityView view, DaoSession daoSession, Context context) {
        return new LoginActivityPresenter(view, daoSession, context);
    }

}
