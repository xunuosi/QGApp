package sinolight.cn.qgapp.views.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.dagger.component.DaggerVideoInfoActivityComponent;
import sinolight.cn.qgapp.dagger.module.VideoInfoActivityModule;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.presenter.VideoInfoActivityPresenter;
import sinolight.cn.qgapp.views.view.IVideoInfoActivityView;

/**
 * Created by xns on 2017/8/2.
 * Video info
 */

public class VideoInfoActivity extends BaseActivity implements IVideoInfoActivityView {
    @Inject
    Context mContext;
    @Inject
    VideoInfoActivityPresenter mPresenter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tb_video_info)
    Toolbar mTbVideoInfo;
    @BindView(R.id.videoView_video_info)
    VideoView mVideoViewVideoInfo;
    @BindView(R.id.loading_root)
    RelativeLayout mLoadingRoot;
    @BindView(R.id.iv_video_info_play)
    SimpleDraweeView mIvVideoInfoPlay;

    private MediaController mMediaController;

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
    protected void onStart() {
        super.onStart();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_video_info;
    }

    @Override
    protected void initViews() {
        mMediaController = new MediaController(VideoInfoActivity.this);
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

        mVideoViewVideoInfo.setVideoPath(videoData.getVideo());
        // 为VideoView指定MediaController
        mVideoViewVideoInfo.setMediaController(mMediaController);
        // 为MediaController指定控制的VideoView
        mMediaController.setMediaPlayer(mVideoViewVideoInfo);

        mVideoViewVideoInfo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mPresenter.videoOnPrepared(mp);
            }
        });
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
        if (mVideoViewVideoInfo.isPlaying()) {
            mVideoViewVideoInfo.pause();
        }
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
}
