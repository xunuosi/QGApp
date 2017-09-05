package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.dagger.component.DaggerReadActivityComponent;
import sinolight.cn.qgapp.dagger.module.ReadActivityModule;
import sinolight.cn.qgapp.data.http.entity.ReaderEntity;
import sinolight.cn.qgapp.presenter.ReadActivityPresenter;
import sinolight.cn.qgapp.utils.ImgTagHandler;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IReadActivityView;
import sinolight.cn.qgapp.views.widget.popmenu.PopWindowUtil;


/**
 * Created by xns on 2017/8/2.
 * ReadActivity
 */

public class ReadActivity extends BaseActivity implements IReadActivityView, PopWindowUtil.PopActionListener,
        ImgTagHandler.OnImgClickListener {
    private static final String TAG = "ReadActivity";
    @Inject
    Context mContext;
    @Inject
    ReadActivityPresenter mPresenter;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tb_read_info)
    Toolbar mTbReadInfo;
    @BindView(R2.id.tv_read_content)
    TextView mTvReadContent;
    @BindView(R2.id.tv_read_footer)
    TextView mTvReadFooter;
    @BindView(R2.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R2.id.iv_collect)
    ImageView ivCollect;
    @BindView(R2.id.iv_font)
    ImageView mIvFont;

    private PopWindowUtil popWindow;


    public static Intent getCallIntent(Context context) {
        return new Intent(context, ReadActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.checkoutIntent(getIntent(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
        if (popWindow != null) {
            popWindow.recycle();
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_read;
    }

    @Override
    protected void initViews() {
        // Make textView can response onClick event
        mTvReadContent.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerReadActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .readActivityModule(new ReadActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        this.showStrToast(msg);
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
    public void showReadContent(Spanned spanned) {
        mTvReadContent.setText(spanned);
    }

    @Override
    public void showData(ReaderEntity readData) {
        mTvTitle.setText(readData.getTitle());
        this.setCollectState(readData.isfavor());
    }


    @Override
    public void showFooter(String name) {
        mTvReadFooter.setText(name);
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
    public void showCollectBtn(boolean enable) {
        if (enable) {
            ivCollect.setVisibility(View.VISIBLE);
        } else {
            ivCollect.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_collect, R.id.iv_font})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_collect:
                mPresenter.collectRes();
                break;
            case R.id.iv_font:
                popActionMenu();
                break;
        }
    }

    private void popActionMenu() {
        popWindow = new PopWindowUtil(ReadActivity.this, R.layout.layout_pop_menu, mIvFont);
        popWindow.setListener(this);
        popWindow.showAsDropDown(mIvFont, -200, 0);
    }

    @Override
    public void onItemClick(View view, int index) {
        switch (index) {
            case 0:
                changeFont(14);
                break;
            case 1:
                changeFont(18);
                break;
            case 2:
                changeFont(20);
                break;
        }
    }

    private void changeFont(int dp) {
        CharSequence text = mTvReadContent.getText();
        Spannable span = new SpannableString(text);
        span.setSpan(new AbsoluteSizeSpan(dp, true), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvReadContent.setText(span);
        // Dismiss popWindow
        popWindow.dismiss();
    }

    @Override
    public void onItemAndAdapterClick(View view, int index, int adapterPosition) {

    }

    @Override
    public void onImgOnClick(View view, String imgUrl) {
//        L.d(TAG, "url:" + imgUrl);
        startPictureActivity(view, imgUrl);
    }

    private void startPictureActivity(View view, String url) {
        Intent intent = PictureDisplayActivity.getCallIntent(mContext);
        intent.putExtra(AppContants.Img.IMG_URL, url);
        //android V4包的类,用于两个activity转场时的缩放效果实现
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                ReadActivity.this, view, PictureDisplayActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(ReadActivity.this, intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
    }
}
