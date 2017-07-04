package sinolight.cn.qgapp.dagger.module;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.PerActivity;

/**
 * Created by xns on 2017/6/2.
 * Fragment Module
 */
@Module
public class UserModule {

    public UserModule() {}

    @Provides
    @PerActivity
    List<Drawable> provideBannerDatas(Context context) {
        ArrayList<Drawable> list = new ArrayList<>();
        list.add(ContextCompat.getDrawable(context, R.drawable.home_banner));
        list.add(ContextCompat.getDrawable(context, R.drawable.home_banner));
        list.add(ContextCompat.getDrawable(context, R.drawable.home_banner));
        return list;
    }

}
