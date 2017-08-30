package sinolight.cn.qgapp.dagger.component;

import dagger.Component;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.dagger.module.ActivityModule;
import sinolight.cn.qgapp.dagger.module.UserModule;
import sinolight.cn.qgapp.views.fragment.BaiKeAnalysisFragment;
import sinolight.cn.qgapp.views.fragment.BaiKeWordFragment;
import sinolight.cn.qgapp.views.fragment.CollectArticleFragment;
import sinolight.cn.qgapp.views.fragment.CollectBookFragment;
import sinolight.cn.qgapp.views.fragment.CollectCookFragment;
import sinolight.cn.qgapp.views.fragment.CollectDicFragment;
import sinolight.cn.qgapp.views.fragment.CollectIndustryAnalysisFragment;
import sinolight.cn.qgapp.views.fragment.CollectPicFragment;
import sinolight.cn.qgapp.views.fragment.CollectStdFragment;
import sinolight.cn.qgapp.views.fragment.CollectVideoFragment;
import sinolight.cn.qgapp.views.fragment.DBResArticleFragment;
import sinolight.cn.qgapp.views.fragment.DBResMaterialFragment;
import sinolight.cn.qgapp.views.fragment.DBResPicFragment;
import sinolight.cn.qgapp.views.fragment.DBResVideoFragment;
import sinolight.cn.qgapp.views.fragment.HomeFragment;
import sinolight.cn.qgapp.views.fragment.KnowledgeFragment;
import sinolight.cn.qgapp.views.fragment.ResultArticleFragment;
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

    void inject(DBResPicFragment dbResPicFragment);

    void inject(DBResVideoFragment dbResVideoFragment);

    void inject(BaiKeAnalysisFragment baiKeAnalysisFragment);

    void inject(BaiKeWordFragment baiKeWordFragment);

    void inject(CollectBookFragment collectBookFragment);

    void inject(CollectStdFragment collectStdFragment);

    void inject(CollectPicFragment collectPicFragment);

    void inject(CollectArticleFragment collectArticleFragment);

    void inject(CollectDicFragment collectDicFragment);

    void inject(CollectIndustryAnalysisFragment collectIndustryAnalysisFragment);

    void inject(CollectCookFragment collectCookFragment);

    void inject(CollectVideoFragment collectVideoFragment);

    void inject(ResultArticleFragment resultArticleFragment);
}
