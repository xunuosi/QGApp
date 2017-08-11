package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.PicSetActivityModule;
import sinolight.cn.qgapp.dagger.module.VideoListActivityModule;
import sinolight.cn.qgapp.views.activity.PicSetActivity;
import sinolight.cn.qgapp.views.activity.VideoListActivity;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PicSetActivityModule.class})
public interface PicSetActivityComponent extends ActivityComponent {

    void inject(PicSetActivity picSetActivity);
}
