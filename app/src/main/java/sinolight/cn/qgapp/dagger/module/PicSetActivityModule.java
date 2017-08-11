package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.PicSetActivityPresenter;
import sinolight.cn.qgapp.presenter.VideoListActivityPresenter;
import sinolight.cn.qgapp.views.view.IPicSetActivityView;
import sinolight.cn.qgapp.views.view.IVideoListActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class PicSetActivityModule {
    private IPicSetActivityView view;

    public PicSetActivityModule(IPicSetActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IPicSetActivityView provideIPicSetActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    PicSetActivityPresenter providePicSetActivityPresenter(IPicSetActivityView view, Context context) {
        return new PicSetActivityPresenter(context, view);
    }

}
