package sinolight.cn.qgapp.dagger.component;


import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.SearchActivityModule;
import sinolight.cn.qgapp.views.activity.SearchActivity;

/**
 * Created by xns on 2017/6/29.
 * HomeActivity的组件
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SearchActivityModule.class})
public interface SearchActivityComponent extends ActivityComponent {

    void inject(SearchActivity searchActivity);
}
