package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.KDBBookActivityModule;
import sinolight.cn.qgapp.dagger.module.KDBStdActivityModule;
import sinolight.cn.qgapp.views.activity.KDBBookDetailActivity;
import sinolight.cn.qgapp.views.activity.KDBStdDetailActivity;

/**
 * Created by xns on 2017/6/29.
 * KDBDicActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, KDBStdActivityModule.class})
public interface KDBStdActivityComponent extends ActivityComponent {

    void inject(KDBStdDetailActivity kdbStdDetailActivity);
}
