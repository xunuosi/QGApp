package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.VideoInfoActivityPresenter;
import sinolight.cn.qgapp.presenter.VideoListSetActivityPresenter;
import sinolight.cn.qgapp.views.view.IVideoInfoActivityView;
import sinolight.cn.qgapp.views.view.IVideoListSetActivityView;

/**
 * Created by xns on 2017/6/29.
 * VideoInfoActivity
 */
@Module
public class VideoInfoActivityModule {
    private IVideoInfoActivityView view;

    public VideoInfoActivityModule(IVideoInfoActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IVideoInfoActivityView provideIDBResActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    VideoInfoActivityPresenter provideVideoInfoActivityPresenter(IVideoInfoActivityView view, Context context) {
        return new VideoInfoActivityPresenter(view, context);
    }

}
