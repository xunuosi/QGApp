package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.HelpActivityPresenter;
import sinolight.cn.qgapp.views.view.IHelpActivityView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class HelpActivityModule {
    private IHelpActivityView view;

    public HelpActivityModule(IHelpActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IHelpActivityView provideIHelpActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    HelpActivityPresenter provideHelpActivityPresenter(IHelpActivityView view, Context context) {
        return new HelpActivityPresenter(context, view);
    }

}
