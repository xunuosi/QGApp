package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.dagger.component.DaggerVideoInfoActivityComponent;
import sinolight.cn.qgapp.dagger.module.VideoInfoActivityModule;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.presenter.VideoInfoActivityPresenter;
import sinolight.cn.qgapp.utils.CommonUtil;
import sinolight.cn.qgapp.views.view.IVideoInfoActivityView;

import static com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL;

/**
 * Created by xns on 2017/8/2.
 * Video info
 */

public class VideoInfoActivity extends BaseActivity implements IVideoInfoActivityView, Player.EventListener {
    private static final String TAG = "VideoInfoActivity";
    @Inject
    Context mContext;
    @Inject
    VideoInfoActivityPresenter mPresenter;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tb_video_info)
    Toolbar mTbVideoInfo;
    @BindView(R2.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R2.id.videoView_video_info)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R2.id.root_video_play)
    ConstraintLayout mRootVideoPlay;
    @BindView(R.id.iv_collect)
    ImageView mIvCollect;

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private DefaultTrackSelector trackSelector;
    private Handler mainHandler;
    private SimpleExoPlayer player;
    private DataSource.Factory mediaDataSourceFactory;
    private MediaSource mMediaSource;

    private boolean shouldAutoPlay = true;
    private boolean onResumePlay;
    private int resumeWindow;
    private long resumePosition;

    public static Intent getCallIntent(Context context) {
        return new Intent(context, VideoInfoActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
        this.clearResumePosition();
        mPresenter.checkoutIntent(getIntent());
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Show the controls on any key event.
        simpleExoPlayerView.showController();
        // If the event was not handled then see if the player view can handle it as a media key event.
        return super.dispatchKeyEvent(event) || simpleExoPlayerView.dispatchMediaKeyEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_video_info;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mTbVideoInfo.setVisibility(View.GONE);
            // Dynamic layout
            ViewGroup.LayoutParams layoutParams = simpleExoPlayerView.getLayoutParams();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            simpleExoPlayerView.setLayoutParams(layoutParams);
            simpleExoPlayerView.showController();
            // hide status bar
            changeStatusBarState(false);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mTbVideoInfo.setVisibility(View.VISIBLE);
            // Dynamic layout
            ViewGroup.LayoutParams layoutParams = simpleExoPlayerView.getLayoutParams();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 228, getResources().getDisplayMetrics());
            simpleExoPlayerView.setLayoutParams(layoutParams);
            simpleExoPlayerView.showController();
            // show status bar
            changeStatusBarState(true);
        }
    }

    private void changeStatusBarState(boolean enable) {
        if (enable) { // show status bar
            mRootVideoPlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else { // hide status bar
            mRootVideoPlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void initViews() {

    }

    private void initializePlayer() {
        boolean needNewPlayer = player == null;
        if (needNewPlayer) {
            mainHandler = new Handler();
            // init Track 选择MediaSource中的轨道（Track）交由TrackRenderer负责渲染,这里包括音频轨道和视频轨道。
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
            // init Player
            player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
            player.addListener(this);
            player.setPlayWhenReady(shouldAutoPlay);
            // Bind the player to the view.
            simpleExoPlayerView.setPlayer(player);
            simpleExoPlayerView.setResizeMode(RESIZE_MODE_FILL);

            mediaDataSourceFactory = buildDataSourceFactory(true);
        }
        if (onResumePlay) {
            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
            }
            player.prepare(mMediaSource, !haveResumePosition, false);
        }
    }

    /**
     * Returns a new DataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return ((App) getApplication())
                .buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerVideoInfoActivityComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .videoInfoActivityModule(new VideoInfoActivityModule(this))
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
    public void initVideo(DBResVideoEntity videoData) {
        mTvTitle.setText(videoData.getName());
        setCollectState(videoData.isfavor());
        Uri uri = Uri.parse(videoData.getVideo());
        mMediaSource = new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(), mainHandler, null);
        player.prepare(mMediaSource);
        mPresenter.videoOnPrepared(player);
    }

    @Override
    public void setCollectState(boolean enable) {
        if (enable) {
            mIvCollect.setImageDrawable(getDrawable(R.drawable.ic_icon_collected));
        } else {
            mIvCollect.setImageDrawable(getDrawable(R.drawable.icon_collect));
        }
    }

    @Override
    public void showStrToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumePlay = true;
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (CommonUtil.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Before API Level 24 there is no guarantee of onStop being called.
        if (CommonUtil.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition())
                : C.TIME_UNSET;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_collect, R.id.exo_full})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_collect:
                mPresenter.collectRes(AppContants.DataBase.Res.RES_VIDEO);
                break;
            case R.id.exo_full:
                changeScreenDirection();
                break;
        }
    }

    private void changeScreenDirection() {
        int orient = getRequestedOrientation();
        if (orient == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
