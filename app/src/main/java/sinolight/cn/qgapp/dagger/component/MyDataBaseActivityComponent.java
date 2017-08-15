package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.MyDataBaseActivityModule;
import sinolight.cn.qgapp.views.activity.MyDatabaseActivity;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MyDataBaseActivityModule.class})
public interface MyDataBaseActivityComponent extends ActivityComponent {

    void inject(MyDatabaseActivity myDatabaseActivity);
}
