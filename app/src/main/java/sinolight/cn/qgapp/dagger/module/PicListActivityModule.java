package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.PicListActivityPresenter;
import sinolight.cn.qgapp.views.view.IPicListActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class PicListActivityModule {
    private IPicListActivityView view;

    public PicListActivityModule(IPicListActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IPicListActivityView provideIPicListActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    PicListActivityPresenter providePicListActivityPresenter(IPicListActivityView view, Context context) {
        return new PicListActivityPresenter(context, view);
    }

}
