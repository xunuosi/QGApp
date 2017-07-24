package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.KDBBookActivityModule;
import sinolight.cn.qgapp.views.activity.KDBBookDetailActivity;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, KDBBookActivityModule.class})
public interface KDBBookActivityComponent extends ActivityComponent {

    void inject(KDBBookDetailActivity kdbBookDetailActivity);
}