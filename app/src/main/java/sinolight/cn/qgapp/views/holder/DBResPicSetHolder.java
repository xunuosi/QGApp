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
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.adapter.VideoAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.DBResPicEntity;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.activity.DBResourceActivity;


/**
 * Created by xns on 2017/7/17.
 * 资源库图集/视频集的Holder
 */

public class DBResPicSetHolder<T> extends RecyclerView.ViewHolder {
    private static final String TAG = "DBResPicSetHolder";

    @BindView(R.id.iv_pic_set)
    SimpleDraweeView mIvPicSet;
    @BindView(R.id.tv_pic_set_title)
    TextView mTvPicSetTitle;
    @BindView(R.id.root_pic_set)
    ConstraintLayout mRootPicSet;
    private T mData;
    private int dataType;

    public DBResPicSetHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(KDBResData data, int dataType) {
        if (data != null) {
            this.dataType = dataType;
            this.mData = (T) data.getData();
            switch (dataType) {
                case VideoAdapter.TYPE_VIDEO_SET:
                    bindVideoSetData();
                    break;
                case CommonTitleAdapter.TYPE_PIC_SET:
                    bindPicSetData();
                    break;
            }
        }
    }

    private void bindPicSetData() {
        DBResPicEntity picEntity = (DBResPicEntity) mData;
        mTvPicSetTitle.setText(picEntity.getName());
        int width = ScreenUtil.getScreenWidth2Dp(App.getContext()) - 32;
        int height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.video_set_img_height) /
                App.getContext().getResources().getDisplayMetrics().density);
        // Set corner
        ImageUtil.frescoShowCorner(
                mIvPicSet,
                10.0f,
                R.color.color_collect_divider,
                1.0f
        );
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                picEntity.getCover(),
                mIvPicSet,
                width,
                height
        );
    }

    private void bindVideoSetData() {
        DBResVideoEntity videoEntity = (DBResVideoEntity) mData;
        mTvPicSetTitle.setText(videoEntity.getName());
    }

    @OnClick({R.id.root_pic_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root_pic_set:
                gotoAllListActivity();
                break;
        }
    }

    /**
     * 查看全部时跳转到指定的列表页面
     */
    private void gotoAllListActivity() {
        switch (dataType) {
            case CommonTitleAdapter.TYPE_PIC_SET:
                gotoPicListActivity();
                break;
            case CommonTitleAdapter.TYPE_VIDEO_TITLE:
                gotoVideoListActivity();
                break;
        }
    }

    private void gotoVideoListActivity() {

    }

    private void gotoPicListActivity() {
        DBResPicEntity picEntity = (DBResPicEntity) mData;
        Intent callIntent = DBResourceActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.DataBase.KEY_ID, picEntity.getId());
        callIntent.putExtra(AppContants.DataBase.KEY_RES_TYPE, AppContants.DataBase.Res.RES_IMG);
        callIntent.putExtra(AppContants.DataBase.KEY_TYPE, "");
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }
}
