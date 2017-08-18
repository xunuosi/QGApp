package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.MyTabAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerKDBStdActivityComponent;
import sinolight.cn.qgapp.dagger.module.KDBStdActivityModule;
import sinolight.cn.qgapp.data.http.entity.StdInfoEntity;
import sinolight.cn.qgapp.presenter.KDBStdActivityPresenter;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.views.fragment.StdInfoFragment;
import sinolight.cn.qgapp.views.view.IKDBStdDetailActivityView;

/**
 * Created by xns on 2017/7/25.
 * 标准详情页
 */

public class KDBStdDetailActivity extends BaseActivity implements IKDBStdDetailActivityView {
    @Inject
    Context mContext;
    @Inject
    KDBStdActivityPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_kdb_std_detail)
    Toolbar mTbKdbStdDetail;
    @BindView(R.id.iv_kdb_std_cover)
    SimpleDraweeView mIvKdbStdCover;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R.id.tv_kdb_std_title)
    TextView mTvKdbStdTitle;
    @BindView(R.id.tv_kdb_std_pub_name)
    TextView mTvKdbStdPubName;
    @BindView(R.id.tv_kdb_std_pub_time)
    TextView mTvKdbStdPubTime;
    @BindView(R.id.tl_kdb_std_detail)
    TabLayout mTlKdbStdDetail;
    @BindView(R.id.vp_kdb_std_detail)
    ViewPager mVpKdbStdDetail;
    @BindView(R.id.tv_kdb_std_from)
    TextView mTvKdbStdFrom;
    @BindView(R.id.iv_collect)
    ImageView mIvCollect;

    private List<String> mTitles = new ArrayList<>();
    private MyTabAdapter mTabAdapter;
    private List<Fragment> mFragments;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, KDBStdDetailActivity.class);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.init2show(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_kdb_std_detail;
    }

    @Override
    protected void initViews() {
        mTitles.add(getString(R.string.text_std_info));
        mTitles.add(getString(R.string.text_std_scope));
        mTitles.add(getString(R.string.text_table_of_contents));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerKDBStdActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .kDBStdActivityModule(new KDBStdActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showRefreshing(boolean enable) {
        if (enable) {
            mLoadingRoot.setVisibility(View.VISIBLE);
        } else {
            mLoadingRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorToast(int msgId) {
        String msg = getString(msgId);
        this.showStrToast(msg);
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init2Show(StdInfoEntity stdData) {
        mTvTitle.setText(stdData.getName());

        int width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.kdb_book_detail_cover_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        int height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.kdb_book_detail_cover_height) /
                App.getContext().getResources().getDisplayMetrics().density);
        ImageUtil.frescoShowImageByUri(
                mContext,
                stdData.getCover(),
                mIvKdbStdCover,
                width,
                height
        );
        mTvKdbStdTitle.setText(stdData.getName());
        mTvKdbStdPubName.setText(formatStr(R.string.text_publish_name_format, stdData.getImdate()));
        mTvKdbStdPubTime.setText(formatStr(R.string.text_publish_time_format, stdData.getIssuedate()));
        setCollectState(stdData.isfavor());

        mFragments = new ArrayList<>();
        mFragments.add(StdInfoFragment.newInstance(StdInfoFragment.TYPE_STD_INFO, stdData));
        mFragments.add(StdInfoFragment.newInstance(StdInfoFragment.TYPE_STD_INTRODUCTION, stdData));
        mFragments.add(StdInfoFragment.newInstance(StdInfoFragment.TYPE_STD_TABLE_OF_CONTENTS, stdData));

        mTabAdapter = new MyTabAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mVpKdbStdDetail.setAdapter(mTabAdapter);
        mTlKdbStdDetail.setupWithViewPager(mVpKdbStdDetail);
    }

    private String formatStr(int baseStrId, String child) {
        String local = getString(baseStrId);
        return String.format(local, child);
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_collect, R.id.btn_kdb_std_read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_collect:
                mPresenter.collectRes(AppContants.DataBase.Res.RES_STANDARD);
                break;
            case R.id.btn_kdb_std_read:
                gotoChapterActivity();
                break;
        }
    }

    @Override
    public void setCollectState(boolean enable) {
        if (enable) {
            mIvCollect.setImageDrawable(getDrawable(R.drawable.ic_icon_collected));
        } else {
            mIvCollect.setImageDrawable(getDrawable(R.drawable.icon_collect));
        }
    }

    private void gotoChapterActivity() {
        startActivity(mPresenter.gotoActivity());
    }
}
