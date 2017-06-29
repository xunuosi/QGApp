package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.HomeActivityModule;
import sinolight.cn.qgapp.views.activity.HomeActivity;

/**
 * Created by xns on 2017/6/29.
 * HomeActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, HomeActivityModule.class})
public interface HomeActivityComponent extends ActivityComponent {

    void inject(HomeActivity homeActivity);
}
