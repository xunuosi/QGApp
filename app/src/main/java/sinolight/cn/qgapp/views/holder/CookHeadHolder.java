package sinolight.cn.qgapp.views.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.bean.SimpleCookInfo;
import sinolight.cn.qgapp.data.http.entity.CookEntity;
import sinolight.cn.qgapp.utils.ImageUtil;

/**
 * Created by xns on 2017/7/6.
 * 热门文章
 */

public class CookHeadHolder extends RecyclerView.ViewHolder {
    private SimpleCookInfo content;
    @BindView(R.id.iv_cook_info_cover)
    SimpleDraweeView mIvCookInfoCover;
    @BindView(R.id.tv_cook_info_title)
    TextView mTvCookInfoTitle;
    @BindView(R.id.tv_cook_info_source)
    TextView mTvCookInfoSource;

    public CookHeadHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(KDBResData<SimpleCookInfo> data) {
        if (data != null) {
            content = data.getData();
            bindData();
        }
    }

    private void bindData() {
        mTvCookInfoTitle.setText(content.getName());
        mTvCookInfoSource.setText(content.getSource());

        int width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.cook_info_image_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        int height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.cook_info_image_height) /
                App.getContext().getResources().getDisplayMetrics().density);
        ImageUtil.frescoShowImageByUri(App.getContext(), content.getCover(), mIvCookInfoCover, width, height);
    }

}
