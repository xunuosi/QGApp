package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.MaterialDetailActivityPresenter;
import sinolight.cn.qgapp.views.view.IMaterialDetailActivityView;

/**
 * Created by xns on 2017/6/29.
 * VideoInfoActivity
 */
@Module
public class MaterialInfoActivityModule {
    private IMaterialDetailActivityView view;

    public MaterialInfoActivityModule(IMaterialDetailActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IMaterialDetailActivityView provideIMaterialDetailActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    MaterialDetailActivityPresenter provideMaterialDetailActivityPresenter(IMaterialDetailActivityView view, Context context) {
        return new MaterialDetailActivityPresenter(view, context);
    }

}
