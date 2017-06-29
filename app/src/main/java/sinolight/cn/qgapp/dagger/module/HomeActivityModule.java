package sinolight.cn.qgapp.dagger.module;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.views.fragment.HomeFragment;
import sinolight.cn.qgapp.views.fragment.KnowledgeFragment;
import sinolight.cn.qgapp.views.fragment.ResourceFragment;
import sinolight.cn.qgapp.views.fragment.UserFragment;
import sinolight.cn.qgapp.views.view.IHomeActivityView;

/**
 * Created by xns on 2017/6/29.
 * HomeActivity的Module
 */
@Module
public class HomeActivityModule {
    private IHomeActivityView view;

    public HomeActivityModule(IHomeActivityView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    IHomeActivityView provideHomeActivityView() {
        return this.view;
    }

    @Provides
    @PerActivity
    HomeFragment provideHomeFragment() {
        return HomeFragment.newInstance();
    }

    @Provides
    @PerActivity
    KnowledgeFragment provideKnowledgeFragment() {
        return KnowledgeFragment.newInstance();
    }

    @Provides
    @PerActivity
    ResourceFragment provideResourceFragment() {
        return ResourceFragment.newInstance();
    }

    @Provides
    @PerActivity
    UserFragment provideUserFragment() {
        return UserFragment.newInstance();
    }
}
