package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.VideoListActivityPresenter;
import sinolight.cn.qgapp.views.view.IVideoListActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class VideoListActivityModule {
    private IVideoListActivityView view;

    public VideoListActivityModule(IVideoListActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IVideoListActivityView provideIDBResActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    VideoListActivityPresenter provideITMDetailPresenter(IVideoListActivityView view, Context context) {
        return new VideoListActivityPresenter(view, context);
    }

}
