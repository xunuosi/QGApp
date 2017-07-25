package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.KDBStdActivityPresenter;
import sinolight.cn.qgapp.views.view.IKDBStdDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity的Module
 */
@Module
public class KDBStdActivityModule {
    private IKDBStdDetailActivityView view;

    public KDBStdActivityModule(IKDBStdDetailActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IKDBStdDetailActivityView provideIKDBDicDetailActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    KDBStdActivityPresenter provideKDBStdActivityPresenter(Context context, IKDBStdDetailActivityView view) {
        return new KDBStdActivityPresenter(context, view);
    }

}
