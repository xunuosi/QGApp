package sinolight.cn.qgapp.dagger.component;

import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.UserModule;
import sinolight.cn.qgapp.data.http.entity.DBResArticleEntity;
import sinolight.cn.qgapp.views.fragment.DBResArticleFragment;
import sinolight.cn.qgapp.views.fragment.DBResMaterialFragment;
import sinolight.cn.qgapp.views.fragment.HomeFragment;
import sinolight.cn.qgapp.views.fragment.KnowledgeFragment;
import sinolight.cn.qgapp.views.fragment.UserFragment;
import sinolight.cn.qgapp.views.fragment.UserHomeFragment;

/**
 * Created by xns on 2017/6/2.
 * Fragment组件
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {

    void inject(HomeFragment homeFragment);

    void inject(KnowledgeFragment knowledgeFragment);

    void inject(UserFragment userFragment);

    void inject(UserHomeFragment userHomeFragment);

    void inject(DBResMaterialFragment dbResMaterialFragment);

    void inject(DBResArticleFragment dbResArticleFragment);
}
