package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.utils.ImageUtil;

/**
 * Created by xns on 2017/7/6.
 * 热门文章
 */

public class CookImgHolder extends RecyclerView.ViewHolder {
    private String cover;
    private int width;
    private int height;
    @BindView(R.id.iv_item_cook_info_image)
    SimpleDraweeView mIvItemCookInfoImage;
    @BindView(R.id.root_item_cook_info_image)
    ConstraintLayout mRootItemCookInfoImage;

    public CookImgHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.cook_info_image_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.cook_info_image_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(KDBResData<String> data) {
        if (data != null) {
            cover = data.getData();
            bindData();
        }
    }

    private void bindData() {
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                cover,
                mIvItemCookInfoImage,
                width,
                height
        );
    }

}
