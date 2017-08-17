package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.PicDetailActivityPresenter;
import sinolight.cn.qgapp.views.view.IResPicDetailView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class PicDetailActivityModule {
    private IResPicDetailView view;

    public PicDetailActivityModule(IResPicDetailView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IResPicDetailView provideIResPicDetailView() {
        return this.view;
    }

    @Provides
    @PerActivity
    PicDetailActivityPresenter providePicDetailActivityPresenter(IResPicDetailView view, Context context) {
        return new PicDetailActivityPresenter(context, view);
    }

}
