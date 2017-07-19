package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.L;
import sinolight.cn.qgapp.utils.ScreenUtil;

/**
 * Created by xns on 2017/7/19.
 * 图片资源的Holder
 */

public class ResImgHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ResImgHolder";

    @BindView(R.id.iv_db_res_img)
    SimpleDraweeView mIvDbResImg;
    @BindView(R.id.db_res_img_root)
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
    }

    @OnClick({R.id.db_res_img_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.db_res_img_root:
                L.d(TAG, "onClick:" + mData.getName());
                break;
        }
    }
}
