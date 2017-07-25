package sinolight.cn.qgapp.dagger.module;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.PerActivity;
import sinolight.cn.qgapp.data.bean.LocalDataBean;
import sinolight.cn.qgapp.presenter.HomeFragmentPresenter;
import sinolight.cn.qgapp.presenter.KnowledgePresenter;
import sinolight.cn.qgapp.presenter.RegisterActivityPresenter;
import sinolight.cn.qgapp.presenter.UserFragmentPresenter;

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
}
