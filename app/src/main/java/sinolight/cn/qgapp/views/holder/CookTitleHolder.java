package sinolight.cn.qgapp.views.holder;

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

public class CookTitleHolder extends RecyclerView.ViewHolder {
    private String content;
    @BindView(R.id.tv_item_cook_info)
    TextView mTvItemCookInfo;

    public CookTitleHolder(View layout) {
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
        if (content != null && !content.isEmpty()) {
            mTvItemCookInfo.setText(content);
        } else {
            mTvItemCookInfo.setText(R.string.text_step);
        }
    }

}
