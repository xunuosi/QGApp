package sinolight.cn.qgapp.dagger.module;


import android.content.Context;


import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.presenter.BaiKeAnaFragmentPresenter;
import sinolight.cn.qgapp.presenter.BaiKeWordFragmentPresenter;
import sinolight.cn.qgapp.presenter.CollectArticlePresenter;
import sinolight.cn.qgapp.presenter.CollectBookPresenter;
import sinolight.cn.qgapp.presenter.CollectCookPresenter;
import sinolight.cn.qgapp.presenter.CollectDicPresenter;
import sinolight.cn.qgapp.presenter.CollectIndustryAnalysisPresenter;
import sinolight.cn.qgapp.presenter.CollectPicPresenter;
import sinolight.cn.qgapp.presenter.CollectStdPresenter;
import sinolight.cn.qgapp.presenter.CollectVideoPresenter;
import sinolight.cn.qgapp.presenter.DBResArticlePresenter;
import sinolight.cn.qgapp.presenter.DBResMaterialPresenter;
import sinolight.cn.qgapp.presenter.DBResPicPresenter;
import sinolight.cn.qgapp.presenter.DBResVideoPresenter;
import sinolight.cn.qgapp.presenter.HomeFragmentPresenter;
import sinolight.cn.qgapp.presenter.KnowledgePresenter;
import sinolight.cn.qgapp.presenter.ResultArticlePresenter;
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

    @Provides
    @PerActivity
    DBResPicPresenter provideDBResPicPresenter(Context context) {
        return new DBResPicPresenter(context);
    }

    @Provides
    @PerActivity
    DBResVideoPresenter provideDBResVideoPresenter(Context context) {
        return new DBResVideoPresenter(context);
    }

    @Provides
    @PerActivity
    BaiKeAnaFragmentPresenter provideBaiKeAnaFragmentPresenter(Context context) {
        return new BaiKeAnaFragmentPresenter(context);
    }

    @Provides
    @PerActivity
    BaiKeWordFragmentPresenter provideBaiKeWordFragmentPresenter(Context context) {
        return new BaiKeWordFragmentPresenter(context);
    }

    @Provides
    @PerActivity
    CollectBookPresenter provideCollectBookPresenter(Context context) {
        return new CollectBookPresenter(context);
    }

    @Provides
    @PerActivity
    CollectStdPresenter provideCollectStdPresenter(Context context) {
        return new CollectStdPresenter(context);
    }

    @Provides
    @PerActivity
    CollectPicPresenter provideCollectPicPresenter(Context context) {
        return new CollectPicPresenter(context);
    }

    @Provides
    @PerActivity
    CollectArticlePresenter provideCollectArticlePresenter(Context context) {
        return new CollectArticlePresenter(context);
    }

    @Provides
    @PerActivity
    CollectDicPresenter provideCollectDicPresenter(Context context) {
        return new CollectDicPresenter(context);
    }

    @Provides
    @PerActivity
    CollectIndustryAnalysisPresenter provideCollectIndustryAnalysisPresenter(Context context) {
        return new CollectIndustryAnalysisPresenter(context);
    }

    @Provides
    @PerActivity
    CollectCookPresenter provideCollectCookPresenter(Context context) {
        return new CollectCookPresenter(context);
    }

    @Provides
    @PerActivity
    CollectVideoPresenter provideCollectVideoPresenter(Context context) {
        return new CollectVideoPresenter(context);
    }

    @Provides
    @PerActivity
    ResultArticlePresenter provideResultArticlePresenter(Context context) {
        return new ResultArticlePresenter(context);
    }
}
