package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerActivityComponent;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.ScreenUtil;

/**
 * Created by xns on 2017/7/10.
 * 知识库详情界面
 */

public class DBaseDetailActivity extends BaseActivity {
    private static final String TAG = "DBaseDetailActivity";
    @BindView(R.id.tv_db_detail_title)
    TextView mTvDbDetailTitle;
    @BindView(R.id.et_db_detail_search)
    EditText mEtDbDetailSearch;
    @BindView(R.id.iv_db_detail_banner)
    SimpleDraweeView mIvDbDetailBanner;

    private String dbId;
    private String dbName;
    private AppContants.DataBase.Type dbType;

    @Inject
    Context mContext;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, DBaseDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            dbId = intent.getStringExtra(AppContants.DataBase.KEY_ID);
            dbName = intent.getStringExtra(AppContants.DataBase.KEY_NAME);
            dbType = (AppContants.DataBase.Type) intent.getSerializableExtra(AppContants.DataBase.KEY_TYPE);
            showView();
        }
    }

    private void showView() {
        switch (dbType) {
            case DB_FOOD:
                showBanner(R.drawable.db_food_banner);
                break;
            case DB_ART:
                showBanner(R.drawable.db_art_banner);
                break;
            case DB_PAPER:
                showBanner(R.drawable.db_paper_banner);
                break;
            case DB_LEATHER:
                showBanner(R.drawable.db_leather_banner);
                break;
            case DB_FURNITURE:
                showBanner(R.drawable.db_furniture_banner);
                break;
            case DB_PACK:
                showBanner(R.drawable.db_package_banner);
                break;
            case DB_CLOTHING:
                showBanner(R.drawable.db_clothing_banner);
                break;
            case DB_ELECTROMECHANICAL:
                showBanner(R.drawable.db_electricale_banner);
                break;
            case DB_WEIGHING:
                showBanner(R.drawable.db_weighter_banner);
                break;
        }
        mTvDbDetailTitle.setText(dbName);
    }

    private void showBanner(int imgId) {
        int width = ScreenUtil.getScreenWidth2Dp(mContext);
        int height = (int) App.getContext().getResources().getDimension(R.dimen.db_detail_img_height);
        ImageUtil.frescoShowImageByResId(
                mContext,
                imgId,
                mIvDbDetailBanner,
                width,
                height);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_db_detail;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build()
                .inject(this);
    }

    @OnClick({R.id.iv_db_detail_back, R.id.iv_db_detail_search, R.id.tv_db_detail_book, R.id.tv_db_detail_article, R.id.tv_db_detail_dictionary, R.id.tv_db_detail_standard, R.id.tv_db_detail_img, R.id.tv_db_detail_analysis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_db_detail_back:
                finish();
                break;
            case R.id.iv_db_detail_search:
                break;
            case R.id.tv_db_detail_book:
                gotoResActivity(AppContants.DataBase.Res.RES_BOOK);
                break;
            case R.id.tv_db_detail_article:
                gotoResActivity(AppContants.DataBase.Res.RES_ARTICLE);
                break;
            case R.id.tv_db_detail_dictionary:
                gotoResActivity(AppContants.DataBase.Res.RES_DIC);
                break;
            case R.id.tv_db_detail_standard:
                gotoResActivity(AppContants.DataBase.Res.RES_STANDARD);
                break;
            case R.id.tv_db_detail_img:
                gotoResActivity(AppContants.DataBase.Res.RES_IMG);
                break;
            case R.id.tv_db_detail_analysis:
                gotoResActivity(AppContants.DataBase.Res.RES_INDUSTRY);
                break;
        }
    }

    private void gotoResActivity(AppContants.DataBase.Res resType) {
        Intent intent = DBResourceActivity.getCallIntent(mContext);
        intent.putExtra(AppContants.DataBase.KEY_ID, dbId);
        intent.putExtra(AppContants.DataBase.KEY_RES_TYPE, resType);
        intent.putExtra(AppContants.DataBase.KEY_TYPE, dbType.getType());
        startActivity(intent);
    }
}
