package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.VideoInfoActivityModule;
import sinolight.cn.qgapp.views.activity.VideoInfoActivity;

/**
 * Created by xns on 2017/6/29.
 * VideoInfoActivity
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, VideoInfoActivityModule.class})
public interface VideoInfoActivityComponent extends ActivityComponent {

    void inject(VideoInfoActivity videoInfoActivity);
}
