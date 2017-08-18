package sinolight.cn.qgapp.views.holder;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.R2;
import sinolight.cn.qgapp.data.bean.CollectEvent;
import sinolight.cn.qgapp.data.bean.EventAction;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.utils.ScreenUtil;
import sinolight.cn.qgapp.views.activity.ResPicDetailActivity;

/**
 * Created by xns on 2017/7/19.
 * 图片资源的Holder
 */

public class ResImgHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ResImgHolder";

    @BindView(R2.id.iv_db_res_img)
    SimpleDraweeView mIvDbResImg;
    @BindView(R2.id.tv_db_res_img_title)
    TextView mTvDbResImgTitle;
    @BindView(R2.id.iv_db_res_img_collect)
    ImageView mIvDbResImgCollect;
    @BindView(R2.id.tv_db_res_img_abs)
    TextView mTvDbResImgAbs;
    @BindView(R2.id.db_res_img_root)
    ConstraintLayout mDbResImgRoot;

    private int width;
    private int height;
    private ResImgEntity mData;

    public ResImgHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        width = ScreenUtil.getScreenWidth2Dp(App.getContext());
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.db_res_img_icon_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(KDBResData<ResImgEntity> data) {
        mData = data.getData();
        if (mData != null) {
            bindData();
        }
    }

    private void bindData() {
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                mData.getCover(),
                mIvDbResImg,
                width,
                height
        );

        mTvDbResImgTitle.setText(mData.getName());
        mTvDbResImgAbs.setText(mData.getAbs());
    }

    @OnClick({R2.id.iv_db_res_img_collect, R2.id.db_res_img_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_db_res_img_collect:
                onCollect();
                break;
            case R.id.db_res_img_root:
                gotoPicDetailActivity();
                break;
        }
    }

    private void gotoPicDetailActivity() {
        Intent callIntent = ResPicDetailActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.Resource.RES_ID, mData.getId());
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }

    /**
     * Collect action
     */
    private void onCollect() {
        setCollectState(true);
        CollectEvent event = new CollectEvent();
        event.setId(mData.getId());
        event.setResType(AppContants.DataBase.Res.RES_IMG);
        EventBus.getDefault().post(event);
    }

    private void setCollectState(boolean enable) {
        if (enable) {
            mIvDbResImgCollect.setImageDrawable(App.getContext().getDrawable(R.drawable.ic_icon_collected));
        } else {
            mIvDbResImgCollect.setImageDrawable(App.getContext().getDrawable(R.drawable.icon_collect));
        }
    }
}
