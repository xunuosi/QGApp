package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerKDBDicActivityComponent;
import sinolight.cn.qgapp.dagger.module.KDBDicActivityModule;
import sinolight.cn.qgapp.presenter.KDBDicActivityPresenter;
import sinolight.cn.qgapp.views.view.IKDBDicDetailActivityView;

/**
 * Created by admin on 2017/7/21.
 * This is activity which is dictionary of knowledge dataBase.
 */

public class KDBDicDetailActivity extends BaseActivity implements IKDBDicDetailActivityView {
    @Inject
    Context mContext;
    @Inject
    KDBDicActivityPresenter mPresenter;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, KDBDicDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_kdb_dic_detail;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerKDBDicActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .kDBDicActivityModule(new KDBDicActivityModule(this))
                .build()
                .inject(this);
    }
}
