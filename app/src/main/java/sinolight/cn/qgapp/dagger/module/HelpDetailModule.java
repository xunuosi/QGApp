package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.HelpDetailPresenter;
import sinolight.cn.qgapp.views.view.IHelpDetailView;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的Module
 */
@Module
public class HelpDetailModule {
    private IHelpDetailView view;

    public HelpDetailModule(IHelpDetailView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IHelpDetailView provideIHelpDetailView() {
        return this.view;
    }

    @Provides
    @PerActivity
    HelpDetailPresenter provideHelpDetailPresenter(IHelpDetailView view, Context context) {
        return new HelpDetailPresenter(context, view);
    }

}
