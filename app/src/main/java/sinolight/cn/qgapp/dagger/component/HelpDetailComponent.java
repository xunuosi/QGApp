package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.HelpDetailModule;
import sinolight.cn.qgapp.views.activity.HelpInfoActivity;

/**
 * Created by xns on 2017/6/29.
 * DBResActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, HelpDetailModule.class})
public interface HelpDetailComponent extends ActivityComponent {

    void inject(HelpInfoActivity helpInfoActivity);
}
