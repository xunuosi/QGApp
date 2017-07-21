package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.KDBDicActivityPresenter;
import sinolight.cn.qgapp.views.view.IKDBDicDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity的Module
 */
@Module
public class KDBDicActivityModule {
    private IKDBDicDetailActivityView view;

    public KDBDicActivityModule(IKDBDicDetailActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IKDBDicDetailActivityView provideIKDBDicDetailActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    KDBDicActivityPresenter provideKDBDicActivityPresenter(Context context, IKDBDicDetailActivityView view) {
        return new KDBDicActivityPresenter(context, view);
    }

}
