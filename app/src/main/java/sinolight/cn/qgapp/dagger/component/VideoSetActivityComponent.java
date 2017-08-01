package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.VideoSetActivityModule;
import sinolight.cn.qgapp.views.activity.VideoListSetActivity;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, VideoSetActivityModule.class})
public interface VideoSetActivityComponent extends ActivityComponent {

    void inject(VideoListSetActivity videoListSetActivity);
}
