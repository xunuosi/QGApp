package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.SearchDisplayActivityModule;
import sinolight.cn.qgapp.views.activity.SearchDisplayActivity;

/**
 * Created by xns on 2017/6/29.
 * HomeActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SearchDisplayActivityModule.class})
public interface SearchDisplayActivityComponent extends ActivityComponent {

    void inject(SearchDisplayActivity searchActivity);
}
