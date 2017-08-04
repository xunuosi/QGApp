package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerMaterialInfoActivityComponent;
import sinolight.cn.qgapp.dagger.module.MaterialInfoActivityModule;
import sinolight.cn.qgapp.presenter.MaterialDetailActivityPresenter;
import sinolight.cn.qgapp.views.view.IMaterialDetailActivityView;

/**
 * Created by xns on 2017/8/4.
 * 菜谱界面
 */

public class MaterialDetailActivity extends BaseActivity implements IMaterialDetailActivityView {
    private static final String TAG = "MaterialDetailActivity";

    @Inject
    Context mContext;
    @Inject
    MaterialDetailActivityPresenter mPresenter;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, MaterialDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_material_detail;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerMaterialInfoActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .materialInfoActivityModule(new MaterialInfoActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {

    }

    @Override
    public void showRefreshing(boolean enable) {

    }

    @Override
    public void showView() {

    }
}
