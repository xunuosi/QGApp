package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.PicDetailActivityModule;
import sinolight.cn.qgapp.views.activity.ResPicDetailActivity;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PicDetailActivityModule.class})
public interface PicDetailActivityComponent extends ActivityComponent {

    void inject(ResPicDetailActivity resPicDetailActivity);
}
