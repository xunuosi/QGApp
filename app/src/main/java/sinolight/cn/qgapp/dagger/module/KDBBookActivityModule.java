package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.KDBBookActivityPresenter;
import sinolight.cn.qgapp.views.view.IKDBBookDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity的Module
 */
@Module
public class KDBBookActivityModule {
    private IKDBBookDetailActivityView view;

    public KDBBookActivityModule(IKDBBookDetailActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IKDBBookDetailActivityView provideIKDBBookDetailActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    KDBBookActivityPresenter provideKDBBookActivityPresenter(Context context, IKDBBookDetailActivityView view) {
        return new KDBBookActivityPresenter(context, view);
    }

}
