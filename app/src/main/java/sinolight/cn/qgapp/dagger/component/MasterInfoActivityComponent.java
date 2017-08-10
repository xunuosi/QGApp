package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.MasterInfoActivityModule;
import sinolight.cn.qgapp.views.activity.MasterInfoActivity;

/**
 * Created by xns on 2017/6/29.
 * VideoInfoActivity
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MasterInfoActivityModule.class})
public interface MasterInfoActivityComponent extends ActivityComponent {

    void inject(MasterInfoActivity masterInfoActivity);
}
