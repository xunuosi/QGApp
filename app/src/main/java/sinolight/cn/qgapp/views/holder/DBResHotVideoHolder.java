package sinolight.cn.qgapp.views.holder;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.views.activity.VideoInfoActivity;
import sinolight.cn.qgapp.views.activity.VideoListActivity;

/**
 * Created by xns on 2017/7/17.
 * 资源库视频holder
 */

public class DBResHotVideoHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "DBResMaterialHolder";
    @BindView(R.id.iv_resdb_pic)
    SimpleDraweeView mIvResdbPic;
    @BindView(R.id.tv_resdb_pic_title)
    TextView mTvResdbPicTitle;
    @BindView(R.id.tv_resdb_pic_info)
    TextView mTvResdbPicInfo;
    @BindView(R.id.tv_resdb_pic_from)
    TextView mTvResdbPicFrom;
    @BindView(R.id.root_db_res_hot_pic)
    ConstraintLayout mRootDbResHotPic;
    private DBResVideoEntity mData;
    private int width;
    private int height;
    private int type;

    public DBResHotVideoHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.resdb_materail_cover_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.resdb_materail_cover_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(KDBResData<DBResVideoEntity> data, int type) {
        if (data != null) {
            mData = data.getData();
            this.type = type;
            bindData();
        }
    }

    private void bindData() {
        switch (type) {
            case AppContants.Video.TYPE_HOT_VIDEO:
                bindHotVideoHot();
                break;
            case AppContants.Video.TYPE_SET_VIDEO:
                bindVideoSetData();
                break;
            case AppContants.Video.TYPE_LIST_VIDEO:
                bindVideoListData();
                break;
        }
    }

    private void bindVideoListData() {
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                mData.getCover(),
                mIvResdbPic,
                width,
                height
        );

        mTvResdbPicTitle.setText(mData.getName());
        mTvResdbPicInfo.setText(mData.getAbs());
        // hide text source
        mTvResdbPicFrom.setVisibility(View.GONE);
    }

    private void bindVideoSetData() {
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                mData.getCover(),
                mIvResdbPic,
                width,
                height
        );
        mTvResdbPicTitle.setText(mData.getName());
        mTvResdbPicInfo.setText(mData.getAbs());
        // hide text source
        mTvResdbPicFrom.setVisibility(View.GONE);
    }

    private void bindHotVideoHot() {
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                mData.getCover(),
                mIvResdbPic,
                width,
                height
        );

        mTvResdbPicTitle.setText(mData.getName());
        mTvResdbPicInfo.setText(mData.getAbs());
        // hide text source
        mTvResdbPicFrom.setVisibility(View.GONE);
    }

    private String formatStr(int baseStrId, String child) {
        String local = App.getContext().getString(baseStrId);
        return String.format(local, child);
    }

    @OnClick({R.id.root_db_res_hot_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root_db_res_hot_pic:
                onClickAction();
                break;
        }
    }

    private void onClickAction() {
        switch (type) {
            case AppContants.Video.TYPE_SET_VIDEO:
                onClickVideoSet();
                break;
            case AppContants.Video.TYPE_HOT_VIDEO:
            case AppContants.Video.TYPE_LIST_VIDEO:
                onClickPlayVideo();
                break;
        }
    }

    private void onClickPlayVideo() {
        Intent callIntent = VideoInfoActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.Video.VIDEO_ID, mData.getId());
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }

    /**
     * 点击视频集合
     */
    private void onClickVideoSet() {
        Intent callIntent = VideoListActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.Video.SET_ID, mData.getId());
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }
}
