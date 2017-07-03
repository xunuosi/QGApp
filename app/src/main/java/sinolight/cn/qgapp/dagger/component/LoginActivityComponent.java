package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.LoginActivityModule;
import sinolight.cn.qgapp.views.activity.LoginActivity;

/**
 * Created by xns on 2017/6/29.
 * HomeActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LoginActivityModule.class})
public interface LoginActivityComponent extends ActivityComponent {

    void inject(LoginActivity loginActivity);
}
