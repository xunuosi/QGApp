package sinolight.cn.qgapp.views.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;

/**
 * Created by xns on 2017/7/6.
 * 热门文章
 */

public class CookTitleHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_item_cook_info)
    TextView mTvItemCookInfo;

    public CookTitleHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(HomeData homeData) {

    }

    private void bindData() {

    }

}
