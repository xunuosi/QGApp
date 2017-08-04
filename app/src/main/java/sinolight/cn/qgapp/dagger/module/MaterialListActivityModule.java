package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.MaterialListActivityPresenter;
import sinolight.cn.qgapp.views.view.IMaterialListActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class MaterialListActivityModule {
    private IMaterialListActivityView view;

    public MaterialListActivityModule(IMaterialListActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IMaterialListActivityView provideIMaterialListActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    MaterialListActivityPresenter provideMaterialListActivityPresenter(IMaterialListActivityView view, Context context) {
        return new MaterialListActivityPresenter(view, context);
    }

}
