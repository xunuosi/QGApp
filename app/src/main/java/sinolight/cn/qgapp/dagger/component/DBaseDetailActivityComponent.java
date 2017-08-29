package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.views.activity.DBaseDetailActivity;

/**
 * Created by xns on 2017/6/1.
 * Activity组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface DBaseDetailActivityComponent {

    void inject(DBaseDetailActivity dBaseDetailActivity);
}
