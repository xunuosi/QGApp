package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.MasterInfoActivityPresenter;
import sinolight.cn.qgapp.views.view.IMasterInfoActivityView;

/**
 * Created by xns on 2017/6/29.
 * VideoInfoActivity
 */
@Module
public class MasterInfoActivityModule {
    private IMasterInfoActivityView view;

    public MasterInfoActivityModule(IMasterInfoActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IMasterInfoActivityView provideIMasterInfoActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    MasterInfoActivityPresenter provideMasterInfoActivityPresenter(IMasterInfoActivityView view, Context context) {
        return new MasterInfoActivityPresenter(view, context);
    }

}
