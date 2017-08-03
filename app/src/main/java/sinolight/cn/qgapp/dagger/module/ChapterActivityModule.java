package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.ChapterActivityPresenter;
import sinolight.cn.qgapp.views.view.IChapterActivityView;

/**
 * Created by xns on 2017/6/29.
 * ChapterActivity的Module
 */
@Module
public class ChapterActivityModule {
    private IChapterActivityView view;

    public ChapterActivityModule(IChapterActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IChapterActivityView provideIChapterActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    ChapterActivityPresenter provideChapterActivityPresenter(IChapterActivityView view, Context context) {
        return new ChapterActivityPresenter(context, view);
    }

}
