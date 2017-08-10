package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.MasterListActivityModule;
import sinolight.cn.qgapp.views.activity.MasterListActivity;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MasterListActivityModule.class})
public interface MasterListActivityComponent extends ActivityComponent {

    void inject(MasterListActivity masterListActivity);
}
