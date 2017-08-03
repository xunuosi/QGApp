package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerChapterActivityComponent;
import sinolight.cn.qgapp.dagger.module.ChapterActivityModule;
import sinolight.cn.qgapp.presenter.ChapterActivityPresenter;
import sinolight.cn.qgapp.views.view.IChapterActivityView;

/**
 * Created by xns on 2017/8/3.
 * 章节界面
 */

public class ChapterActivity extends BaseActivity implements IChapterActivityView {
    @Inject
    Context context;
    @Inject
    ChapterActivityPresenter mPresenter;
    @BindView(R.id.tv_chapter_title)
    TextView mTvChapterTitle;
    @BindView(R.id.tool_bar_chapter)
    Toolbar mToolBarChapter;
    @BindView(R.id.rv_chapter)
    RecyclerView mRvChapter;
    private LinearLayoutManager mLayoutManager;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, ChapterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        mPresenter.checkIntent(getIntent());
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_chapter;
    }

    @Override
    protected void initViews() {
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRvChapter.setLayoutManager(mLayoutManager);
        mRvChapter.addItemDecoration(new ChapterActivity.LinearDivider(ChapterActivity.this));
        mRvChapter.setHasFixedSize(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerChapterActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .chapterActivityModule(new ChapterActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        this.showToastStr(msg);
    }

    @Override
    public void showListView(KDBResAdapter adapter, String title) {
        if (mRvChapter != null && mRvChapter.getAdapter() == null) {
            mRvChapter.setAdapter(adapter);
            mTvChapterTitle.setText(title);
        }
    }

    @Override
    public void showToastStr(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iv_chapter_back)
    public void onViewClicked() {
        finish();
    }

    private class LinearDivider extends Y_DividerItemDecoration {
        private Context context;

        public LinearDivider(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public Y_Divider getDivider(int itemPosition) {
            Y_Divider divider = null;
            divider = new Y_DividerBuilder()
                    .setLeftSideLine(false, ContextCompat.getColor(context, R.color.color_transparent_all), 0.5f, 0, 0)
                    .setBottomSideLine(true, ContextCompat.getColor(context, R.color.color_bottom_divider), 0.5f, 0, 0)
                    .setRightSideLine(false, ContextCompat.getColor(context, R.color.color_transparent_all), 0.5f, 0, 0)
                    .setTopSideLine(false, ContextCompat.getColor(context, R.color.color_transparent_all), 0.5f, 0, 0)
                    .create();
            return divider;
        }
    }
}
