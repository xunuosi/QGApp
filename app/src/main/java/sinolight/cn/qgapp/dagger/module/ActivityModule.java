package sinolight.cn.qgapp.dagger.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.dagger.PerActivity;

/**
 * Created by xns on 2017/6/1.
 * Activity的Module
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.mActivity;
    }
}
