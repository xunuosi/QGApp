package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.PicListActivityModule;
import sinolight.cn.qgapp.views.activity.ResPicListActivity;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PicListActivityModule.class})
public interface PicListActivityComponent extends ActivityComponent {

    void inject(ResPicListActivity resPicListActivity);
}
