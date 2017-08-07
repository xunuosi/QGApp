package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;

/**
 * Created by xns on 2017/7/6.
 * 热门文章
 */

public class CookTextHolder extends RecyclerView.ViewHolder {
    private String content;
    @BindView(R.id.tv_item_cook_info_text)
    TextView mTvItemCookInfoText;
    @BindView(R.id.root_item_cook_info_image)
    ConstraintLayout mRootItemCookInfoImage;

    public CookTextHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
    }

    public void setData(KDBResData<String> data) {
        if (data != null) {
            content = data.getData();
            bindData();
        }
    }

    private void bindData() {
        mTvItemCookInfoText.setText(content);
    }

}
