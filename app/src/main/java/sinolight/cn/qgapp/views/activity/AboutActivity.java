package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerAboutActivityComponent;
import sinolight.cn.qgapp.dagger.module.AboutActivityModule;
import sinolight.cn.qgapp.data.http.entity.AboutEntity;
import sinolight.cn.qgapp.presenter.AboutActivityPresenter;
import sinolight.cn.qgapp.views.view.IAboutActivityView;

/**
 * Created by xns on 2017/8/15.
 * 关于我们界面
 */

public class AboutActivity extends BaseActivity implements IAboutActivityView {
    @Inject
    Context mContext;
    @Inject
    AboutActivityPresenter mPresenter;
    @BindView(R.id.tv_about_title)
    TextView mTvAboutTitle;
    @BindView(R.id.tool_bar_about)
    Toolbar mToolBarAbout;
    @BindView(R.id.tv_about_content)
    TextView mTvAboutContent;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.init2Show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerAboutActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .aboutActivityModule(new AboutActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgID) {
        String msg = getString(msgID);
        this.showToast(msg);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showView(AboutEntity data) {
        mTvAboutTitle.setText(data.getTitle());
        mTvAboutContent.setText(formatStr(R.string.text_two_empty_format,data.getData()));
    }

    private String formatStr(int baseStrId, String child) {
        String local = getString(baseStrId);
        return String.format(local, child);
    }

    @OnClick(R.id.iv_about_back)
    public void onViewClicked() {
        finish();
    }
}
