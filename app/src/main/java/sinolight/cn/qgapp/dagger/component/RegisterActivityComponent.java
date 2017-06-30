package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.RegisterActivityModule;
import sinolight.cn.qgapp.views.activity.RegisterActivity;

/**
 * Created by xns on 2017/6/29.
 * HomeActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, RegisterActivityModule.class})
public interface RegisterActivityComponent extends ActivityComponent {

    void inject(RegisterActivity registerActivity);
}
