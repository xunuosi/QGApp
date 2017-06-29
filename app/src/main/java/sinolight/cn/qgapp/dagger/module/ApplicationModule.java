package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.App;

/**
 * Created by xns on 2017/6/1.
 * 全局Module
 */
@Module
public class ApplicationModule {

    private final App mApplication;

    public ApplicationModule(App application) {
        this.mApplication = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return this.mApplication;
    }

}
