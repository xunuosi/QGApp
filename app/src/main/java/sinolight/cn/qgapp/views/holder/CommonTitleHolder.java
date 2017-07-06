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
 * 分类顶端的标题
 */

public class CommonTitleHolder extends RecyclerView.ViewHolder {
    private HomeData mHomeData;
    private int size;

    @BindView(R.id.iv_item_title)
    SimpleDraweeView mIvItemTitle;
    @BindView(R.id.tv_item_title)
    TextView mTvItemTitle;
    @BindView(R.id.item_title_root)
    ConstraintLayout mItemTitleRoot;

    public CommonTitleHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        size = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.hf_item_title_img_size) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(HomeData homeData) {
        mHomeData = homeData;
        bindData();
    }

    private void bindData() {
        ImageUtil.frescoShowImageByResId(App.getContext(),
                mHomeData.getResId(),
                mIvItemTitle,
                size,
                size);
        mTvItemTitle.setText(mHomeData.getTitle());
    }
}
