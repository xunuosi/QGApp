package sinolight.cn.qgapp.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.data.db.DaoSession;
import sinolight.cn.qgapp.data.db.GreenDaoHelper;
import sinolight.cn.qgapp.utils.ToastUtil;

/**
 * Created by xns on 2017/6/1.
 * 全局Module
 */
@Module
public class ApplicationModule {

    private final App context;

    public ApplicationModule(App context) {
        this.context = context;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return this.context;
    }

    @Provides @Singleton
    public ToastUtil provideToastUtil(){
        return new ToastUtil(context);
    }

    @Provides @Singleton
    DaoSession provideDaoSession() {
        return GreenDaoHelper.getDaoSession();
    }

}
