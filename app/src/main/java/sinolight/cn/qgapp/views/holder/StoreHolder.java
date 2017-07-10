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
 * Created by admin on 2017/7/5.3
 * 类库Holder
 */

public class StoreHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "StoreHolder";
    private HomeData homeData;
    private int width;
    private int height;
    @BindView(R.id.iv_store)
    SimpleDraweeView ivStore;
    @BindView(R.id.tv_store)
    TextView tvStore;
    @BindView(R.id.store_hf_head_root)
    ConstraintLayout storeHfHeadRoot;

    public StoreHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        this.width = (int) App.getContext().getResources().getDimension(R.dimen.hf_store_image_size);
        this.height = (int) App.getContext().getResources().getDimension(R.dimen.hf_store_image_size);
    }

    public void setData(HomeData homeData) {
        this.homeData = homeData;
        bindData();
    }

    private void bindData() {
        ImageUtil.frescoShowImageByResId(App.getContext(),
                homeData.getResId(),
                ivStore,
                width,
                height);
        tvStore.setText(homeData.getTitle());
    }
}
