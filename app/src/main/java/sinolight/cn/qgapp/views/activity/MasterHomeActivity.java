package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.adapter.MasterAdapter;
import sinolight.cn.qgapp.dagger.component.DaggerMasterHomeComponent;
import sinolight.cn.qgapp.dagger.module.MasterHomeActivityModule;
import sinolight.cn.qgapp.presenter.MasterHomeActivityPresenter;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.view.IMasterHomeActivityView;

/**
 * Created by xns on 2017/8/8.
 * 专家首页
 */

public class MasterHomeActivity extends BaseActivity implements IMasterHomeActivityView, OnRefreshListener {
    private static final String TAG = "MasterHomeActivity";
    @Inject
    Context mContext;
    @Inject
    MasterHomeActivityPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_master_home)
    Toolbar mTbMasterHome;
    @BindView(R.id.iv_master_home_banner)
    SimpleDraweeView mIvMasterHomeBanner;
    @BindView(R.id.et_master_home_search)
    EditText mEtMasterHomeSearch;
    @BindView(R.id.rv_master_home)
    RecyclerView mRvMasterHome;
    @BindView(R.id.swipe_master_home)
    SwipeToLoadLayout mSwipeMasterHome;
    private LinearLayoutManager mLayoutManager;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, MasterHomeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_master_home;
    }

    @Override
    protected void initViews() {
        mTvTitle.setText(R.string.text_master_store);

        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvMasterHome.setLayoutManager(mLayoutManager);
        mRvMasterHome.setHasFixedSize(true);
        mRvMasterHome.addItemDecoration(new LinearDivider(mContext));
        // Handle conflict about recyclerView and SwipeView
        mRvMasterHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int firstVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                }
                if (firstVisibleItemPosition == 0) {
                    mSwipeMasterHome.setRefreshEnabled(true);
                } else {
                    mSwipeMasterHome.setRefreshEnabled(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mSwipeMasterHome.setOnRefreshListener(this);
        mSwipeMasterHome.setRefreshing(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerMasterHomeComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .masterHomeActivityModule(new MasterHomeActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showToast(int msgId) {
        String msg = getString(msgId);
        this.showToastStr(msg);
    }

    @Override
    public void showListView(MasterAdapter adapter) {
        if (mRvMasterHome != null && mRvMasterHome.getAdapter() == null) {
            mRvMasterHome.setAdapter(adapter);
        }
    }

    @Override
    public void showToastStr(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRefreshing(boolean enable) {
        if (enable) {
            mSwipeMasterHome.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeMasterHome.setRefreshing(true);
                }
            });
        } else {
            mSwipeMasterHome.setRefreshing(false);
        }
    }

    @Override
    public void showTopBanner(String cover) {
        int width = ScreenUtil.getScreenWidth2Dp(mContext);
        int height = (int) (getResources().getDimensionPixelOffset(R.dimen.master_home_banner_height) /
                getResources().getDisplayMetrics().density);

        ImageUtil.frescoShowImageByUri(
                mContext,
                cover,
                mIvMasterHomeBanner,
                width,
                height
        );
    }

    @Override
    public void hideKeyboard(boolean enable) {
        if (enable) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_collect, R.id.iv_master_home_search, R.id.tv_master_home_getall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_collect:
                break;
            case R.id.iv_master_home_search:
                mPresenter.searchData(mEtMasterHomeSearch.getText().toString().trim());
                break;
            case R.id.tv_master_home_getall:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mEtMasterHomeSearch.setText(null);
        mPresenter.init2Show();
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
