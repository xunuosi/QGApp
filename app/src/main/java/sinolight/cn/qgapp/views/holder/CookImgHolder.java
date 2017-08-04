package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;

/**
 * Created by xns on 2017/7/6.
 * 热门文章
 */

public class CookImgHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_item_cook_info_image)
    SimpleDraweeView mIvItemCookInfoImage;
    @BindView(R.id.root_item_cook_info_image)
    ConstraintLayout mRootItemCookInfoImage;

    public CookImgHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(HomeData homeData) {

    }

    private void bindData() {

    }

}
