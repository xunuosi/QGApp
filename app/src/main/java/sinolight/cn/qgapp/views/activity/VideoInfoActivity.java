package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
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
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.dagger.component.DaggerVideoInfoActivityComponent;
import sinolight.cn.qgapp.dagger.module.VideoInfoActivityModule;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.presenter.VideoInfoActivityPresenter;
import sinolight.cn.qgapp.views.view.IVideoInfoActivityView;

/**
 * Created by xns on 2017/8/2.
 * Video info
 */

public class VideoInfoActivity extends BaseActivity implements IVideoInfoActivityView,ExoPlayer.EventListener {
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
    @BindView(R2.id.iv_video_info_play)
    SimpleDraweeView mIvVideoInfoPlay;
    @BindView(R2.id.videoView_video_info)
    SimpleExoPlayerView simpleExoPlayerView;

    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private DefaultTrackSelector trackSelector;
    private Handler mainHandler;
    private SimpleExoPlayer player;
    private DataSource.Factory mediaDataSourceFactory;
    private MediaSource mMediaSource;


    public static Intent getCallIntent(Context context) {
        return new Intent(context, VideoInfoActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_video_info;
    }

    @Override
    protected void initViews() {
        initializePlayer();
    }

    private void initializePlayer() {
        mainHandler = new Handler();
        // init Track 选择MediaSource中的轨道（Track）交由TrackRenderer负责渲染,这里包括音频轨道和视频轨道。
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
        // init Player
        player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
        // Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);

        mediaDataSourceFactory = buildDataSourceFactory(true);
    }

    /**
     * Returns a new DataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *     DataSource factory.
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
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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
        Uri uri = Uri.parse(videoData.getVideo());
        mMediaSource = new ExtractorMediaSource(uri, mediaDataSourceFactory, new DefaultExtractorsFactory(), mainHandler, null);
        player.prepare(mMediaSource);
        mPresenter.videoOnPrepared(player);
    }

    @Override
    public void showPlayBtn(boolean enable) {
        if (enable) {
            mIvVideoInfoPlay.setVisibility(View.VISIBLE);
        } else {
            mIvVideoInfoPlay.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @OnClick({R.id.im_back_arrow, R.id.iv_collect, R.id.iv_video_info_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back_arrow:
                finish();
                break;
            case R.id.iv_collect:
                break;
            case R.id.iv_video_info_play:
                mPresenter.playVideo();
                break;
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
