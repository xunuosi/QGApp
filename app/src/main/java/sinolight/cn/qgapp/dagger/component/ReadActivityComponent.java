package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.ReadActivityModule;
import sinolight.cn.qgapp.views.activity.ReadActivity;

/**
 * Created by xns on 2017/6/29.
 * ReadActivityComponent
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ReadActivityModule.class})
public interface ReadActivityComponent extends ActivityComponent {

    void inject(ReadActivity readActivity);
}
