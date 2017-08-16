package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerKDBDicActivityComponent;
import sinolight.cn.qgapp.dagger.module.KDBDicActivityModule;
import sinolight.cn.qgapp.data.http.entity.DicInfoEntity;
import sinolight.cn.qgapp.presenter.KDBDicActivityPresenter;
import sinolight.cn.qgapp.utils.ImageUtil;
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
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_kdb_dic_detail)
    Toolbar mTbKdbDicDetail;
    @BindView(R.id.tv_kdb_dic_detail_title)
    TextView mTvKdbDicDetailTitle;
    @BindView(R.id.tv_kdb_dic_detail_cn_name)
    TextView mTvKdbDicDetailCnName;
    @BindView(R.id.tv_kdb_dic_detail_en_name)
    TextView mTvKdbDicDetailEnName;
    @BindView(R.id.tv_kdb_dic_detail_source)
    TextView mTvKdbDicDetailSource;
    @BindView(R.id.tv_kdb_dic_detail_info)
    TextView mTvKdbDicDetailInfo;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, KDBDicDetailActivity.class);
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

    @Override
    public void showRefreshing(boolean enable) {
        if (enable) {
            mLoadingRoot.setVisibility(View.VISIBLE);
        } else {
            mLoadingRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void init2Show(DicInfoEntity dicData) {
        // Show part one
        mTvTitle.setText(dicData.getName());
        mTvKdbDicDetailTitle.setText(dicData.getName());
        String formatCNname = getString(R.string.text_cn_name_format);
        mTvKdbDicDetailCnName.setText(String.format(formatCNname, dicData.getName()));
        String formatENname = getString(R.string.text_en_name_format);
        mTvKdbDicDetailEnName.setText(String.format(formatENname, dicData.getEnname()));
        String formatRESname = getString(R.string.text_resource_format);
        mTvKdbDicDetailSource.setText(String.format(formatRESname, dicData.getSource()));

//        if (dicData.isPicflag()) {
//            int width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.kdb_dic_detail_cover_width) /
//                    App.getContext().getResources().getDisplayMetrics().density);
//            int height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.kdb_dic_detail_cover_height) /
//                    App.getContext().getResources().getDisplayMetrics().density);
//            ImageUtil.frescoShowImageByUri(
//                    KDBDicDetailActivity.this,
//                    dicData.getPicpath(),
//                    mIvKdbDicDetail,
//                    width,
//                    height
//            );
//        }
        // Show part two
        String formatContent = getString(R.string.text_two_empty_format);
        mTvKdbDicDetailInfo.setText(String.format(formatContent, dicData.getContent()));

        this.showRefreshing(false);
    }

    @Override
    public void showErrorToast(int msgId) {
        String msg = getString(msgId);
        this.showStrToast(msg);
    }


    @OnClick({R.id.im_back_arrow, R.id.iv_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_collect:
                mPresenter.collectRes(AppContants.DataBase.Res.RES_DIC);
                break;
        }
    }

    @Override
    public void setCollectState(boolean enable) {
        if (enable) {
            ivCollect.setImageDrawable(getDrawable(R.drawable.ic_icon_collected));
        } else {
            ivCollect.setImageDrawable(getDrawable(R.drawable.icon_collect));
        }
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
