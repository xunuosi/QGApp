package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerKDBBookActivityComponent;
import sinolight.cn.qgapp.dagger.module.KDBBookActivityModule;
import sinolight.cn.qgapp.data.http.entity.BookInfoEntity;
import sinolight.cn.qgapp.presenter.KDBBookActivityPresenter;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.views.view.IKDBBookDetailActivityView;

/**
 * Created by xns on 2017/7/24.
 * 知识库图书详情界面
 */

public class KDBBookDetailActivity extends BaseActivity implements IKDBBookDetailActivityView {
    private static final String TAG = "KDBBookDetailActivity";

    @Inject
    Context mContext;
    @Inject
    KDBBookActivityPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_kdb_book_detail)
    Toolbar mTbKdbBookDetail;
    @BindView(R.id.iv_kdb_book_cover)
    SimpleDraweeView mIvKdbBookCover;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R.id.tv_kdb_book_title)
    TextView mTvKdbBookTitle;
    @BindView(R.id.tv_kdb_book_author)
    TextView mTvKdbBookAuthor;
    @BindView(R.id.ratingBar_kdb_book_detail)
    RatingBar mRatingBarKdbBookDetail;
    @BindView(R.id.tv_kdb_book_score)
    TextView mTvKdbBookScore;
    @BindView(R.id.tv_kdb_book_pub_name)
    TextView mTvKdbBookPubName;
    @BindView(R.id.tv_kdb_book_pub_time)
    TextView mTvKdbBookPubTime;
    @BindView(R.id.tl_kdb_book_detail)
    TabLayout mTlKdbBookDetail;
    @BindView(R.id.vp_kdb_book_detail)
    ViewPager mVpKdbBookDetail;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, KDBBookDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.init2show(getIntent());
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_kdb_book_detail;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
       DaggerKDBBookActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .kDBBookActivityModule(new KDBBookActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_collect, R.id.btn_kdb_book_read})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_collect:
                break;
            case R.id.btn_kdb_book_read:
                break;
        }
    }

    @Override
    public void showRefreshing(boolean enable) {

    }

    @Override
    public void showErrorToast(int msgId) {
        String msg = getString(msgId);
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init2Show(BookInfoEntity bookData) {
        // Part One to show
        mTvTitle.setText(bookData.getName());

        int width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.kdb_book_detail_cover_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        int height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.kdb_book_detail_cover_height) /
                App.getContext().getResources().getDisplayMetrics().density);
        ImageUtil.frescoShowImageByUri(
                mContext,
                bookData.getCover(),
                mIvKdbBookCover,
                width,
                height
                );
        mTvKdbBookTitle.setText(bookData.getName());

    }
}
