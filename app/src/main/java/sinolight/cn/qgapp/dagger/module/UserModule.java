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
import sinolight.cn.qgapp.presenter.RegisterActivityPresenter;

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
    List<LocalDataBean> provideStoreDatas() {
        ArrayList<LocalDataBean> list = new ArrayList<>();
        for (int i=0;i<6;i++) {
            LocalDataBean bean = new LocalDataBean();
            bean.setResId(R.drawable.holder_circle_image);
            bean.setText(R.string.text_knowledge_store);
            list.add(bean);
        }
        return list;
    }

}
