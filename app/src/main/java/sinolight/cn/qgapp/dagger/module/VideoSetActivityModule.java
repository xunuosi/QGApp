package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.DBResActivityPresenter;
import sinolight.cn.qgapp.presenter.VideoListSetActivityPresenter;
import sinolight.cn.qgapp.views.view.IDBResActivityView;
import sinolight.cn.qgapp.views.view.IVideoListSetActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class VideoSetActivityModule {
    private IVideoListSetActivityView view;

    public VideoSetActivityModule(IVideoListSetActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IVideoListSetActivityView provideIDBResActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    VideoListSetActivityPresenter provideVideoListSetActivityPresenter(IVideoListSetActivityView view, Context context) {
        return new VideoListSetActivityPresenter(view, context);
    }

}
