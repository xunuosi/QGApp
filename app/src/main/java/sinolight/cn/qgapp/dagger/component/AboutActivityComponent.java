package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.AboutActivityModule;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.views.activity.AboutActivity;
import sinolight.cn.qgapp.views.activity.SystemActivity;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, AboutActivityModule.class})
public interface AboutActivityComponent extends ActivityComponent {

    void inject(AboutActivity aboutActivity);
}
