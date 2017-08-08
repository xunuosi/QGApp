package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.MasterHomeActivityPresenter;
import sinolight.cn.qgapp.views.view.IMasterHomeActivityView;

/**
 * Created by xns on 2017/6/29.
 * ChapterActivity的Module
 */
@Module
public class MasterHomeActivityModule {
    private IMasterHomeActivityView view;

    public MasterHomeActivityModule(IMasterHomeActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IMasterHomeActivityView provideIMasterHomeActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    MasterHomeActivityPresenter provideMasterHomeActivityPresenter(IMasterHomeActivityView view, Context context) {
        return new MasterHomeActivityPresenter(context, view);
    }

}
