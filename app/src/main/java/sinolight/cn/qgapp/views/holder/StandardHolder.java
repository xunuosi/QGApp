package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.utils.ImageUtil;

/**
 * Created by xns on 2017/7/6.
 * 最新标准
 */

public class StandardHolder extends RecyclerView.ViewHolder {
    private HomeData mHomeData;
    private int width;
    private int height;
    @BindView(R.id.iv_item_standard)
    SimpleDraweeView mIvItemStandard;
    @BindView(R.id.tv_hf_stand_item_title)
    TextView mTvHfStandItemTitle;
    @BindView(R.id.tv_hf_stand_item_num)
    TextView mTvHfStandItemNum;
    @BindView(R.id.tv_hf_stand_item_time)
    TextView mTvHfStandItemTime;
    @BindView(R.id.hf_standard_root)
    ConstraintLayout mHfStandardRoot;

    public StandardHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.hf_item_standard_img_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.hf_item_standard_img_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(HomeData homeData) {
        mHomeData = homeData;
        if (mHomeData.getId() != null) {
            bindData();
        }
    }

    private void bindData() {
        ImageUtil.frescoShowImageByResId(App.getContext(),
                mHomeData.getResId(),
                mIvItemStandard,
                width,
                height);
        mTvHfStandItemTitle.setText(mHomeData.getTitle());
        mTvHfStandItemNum.setText(mHomeData.getStdno());
        mTvHfStandItemTime.setText(mHomeData.getImdate());
    }
}
