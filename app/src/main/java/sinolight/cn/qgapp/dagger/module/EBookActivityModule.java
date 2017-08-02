package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.EBookActivityPresenter;
import sinolight.cn.qgapp.views.view.IEBookActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class EBookActivityModule {
    private IEBookActivityView view;

    public EBookActivityModule(IEBookActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IEBookActivityView provideIEBookActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    EBookActivityPresenter provideEBookActivityPresenter(IEBookActivityView view, Context context) {
        return new EBookActivityPresenter(view, context);
    }

}
