package sinolight.cn.qgapp.dagger.module;


import android.content.Context;


import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.DBResArticlePresenter;
import sinolight.cn.qgapp.presenter.DBResMaterialPresenter;
import sinolight.cn.qgapp.presenter.HomeFragmentPresenter;
import sinolight.cn.qgapp.presenter.KnowledgePresenter;
import sinolight.cn.qgapp.presenter.UserFragmentPresenter;
import sinolight.cn.qgapp.presenter.UserHomeFragmentPresenter;

/**
 * Created by xns on 2017/6/2.
 * Fragment Module
 */
@Module
public class UserModule {

    public UserModule() {}

    @Provides
    @PerActivity
    HomeFragmentPresenter provideHomeFragmentPresenter(Context context) {
        return new HomeFragmentPresenter(context);
    }

    @Provides
    @PerActivity
    KnowledgePresenter provideKnowledgePresenter(Context context) {
        return new KnowledgePresenter(context);
    }

    @Provides
    @PerActivity
    UserFragmentPresenter provideUserFragmentPresenter(Context context) {
        return new UserFragmentPresenter(context);
    }

    @Provides
    @PerActivity
    UserHomeFragmentPresenter provideUserHomeFragmentPresenter(Context context) {
        return new UserHomeFragmentPresenter(context);
    }

    @Provides
    @PerActivity
    DBResMaterialPresenter provideDBResMaterialPresenter(Context context) {
        return new DBResMaterialPresenter(context);
    }

    @Provides
    @PerActivity
    DBResArticlePresenter provideDBResArticlePresenter(Context context) {
        return new DBResArticlePresenter(context);
    }
}
