package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.KDBDicActivityModule;
import sinolight.cn.qgapp.views.activity.KDBDicDetailActivity;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, KDBDicActivityModule.class})
public interface KDBDicActivityComponent extends ActivityComponent {

    void inject(KDBDicDetailActivity kdbDicActivity);
}
