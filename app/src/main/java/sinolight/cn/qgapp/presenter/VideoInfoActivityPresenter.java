package sinolight.cn.qgapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.google.android.exoplayer2.SimpleExoPlayer;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.AppHelper;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.http.HttpManager;
import sinolight.cn.qgapp.data.http.callback.OnResultCallBack;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.data.http.subscriber.HttpSubscriber;
import sinolight.cn.qgapp.utils.KDBResDataMapper;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.view.IVideoInfoActivityView;

/**
 * Created by xns on 2017/6/29.
 * VideoInfo Presenter
 */

public class VideoInfoActivityPresenter extends BasePresenter<IVideoInfoActivityView, HttpManager> {
    private static final String TAG = "VideoListActivityPresenter";
    private Context mContext;

    // VideoParentID
    private String videoID;
    private DBResVideoEntity videoData;
    private SimpleExoPlayer mPlayer;

    private HttpSubscriber<DBResVideoEntity> mVideoObserver = new HttpSubscriber<>(
            new OnResultCallBack<DBResVideoEntity>() {

                @Override
                public void onSuccess(DBResVideoEntity dbResVideoEntity) {
                    if (dbResVideoEntity != null) {
                        videoData = dbResVideoEntity;
                        showSuccess();
                    }
                }

                @Override
                public void onError(int code, String errorMsg) {
                    L.d(TAG, "mVideoObserver code:" + code + ",errorMsg:" + errorMsg);
                    showError();
                }
            });

    private void showSuccess() {
        showWithData();
    }

    private void showError() {
        view().showRefreshing(false);
        showErrorToast(R.string.attention_data_refresh_error);
    }

    private void showWithData() {
        view().initVideo(videoData);
    }

    public VideoInfoActivityPresenter(IVideoInfoActivityView view, Context context) {
        this.mContext = context;
        bindView(view);
        setModel(HttpManager.getInstance());
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void clear() {
        if (mVideoObserver != null) {
            mVideoObserver.unSubscribe();
        }

        mPlayer.release();
        KDBResDataMapper.reset();
        unbindView();
    }

    private void closeRefreshing() {
        view().showRefreshing(false);
    }

    private void showErrorToast(int resId) {
        view().showToast(resId);
        view().showRefreshing(false);
    }

    public void init2Show() {
        resetState();
        // load video set data
        getData();
    }

    private void getData() {
        model.getVideoInfoNoCache(
                mVideoObserver,
                AppHelper.getInstance().getCurrentToken(),
                videoID
        );
    }

    /**
     * 重置状态
     */
    @Override
    protected void resetState() {
        super.resetState();
    }

    public void checkoutIntent(Intent intent) {
        if (intent == null) {
            view().showRefreshing(false);
        } else {
            videoID = intent.getStringExtra(AppContants.Video.VIDEO_ID);
            view().showRefreshing(true);
            // load data
            this.getData();
        }
    }

    /**
     * 视频已经加载完毕
     * @param player
     */
    public void videoOnPrepared(SimpleExoPlayer player) {
        view().showPlayBtn(true);
        this.closeRefreshing();
        mPlayer = player;
    }

    public void playVideo() {
        view().showPlayBtn(false);

    }
}
