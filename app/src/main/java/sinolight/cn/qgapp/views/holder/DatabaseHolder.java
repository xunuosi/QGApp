package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.DataBaseBean;
import sinolight.cn.qgapp.utils.ImageUtil;

/**
 * Created by xns on 2017/7/6.
 * 九大行业数据库
 */

public class DatabaseHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_item_kf)
    SimpleDraweeView mIvItemKf;
    @BindView(R.id.tv_item_kf_title)
    TextView mTvItemKfTitle;
    @BindView(R.id.tv_item_kf_info)
    TextView mTvItemKfInfo;
    @BindView(R.id.item_kf_root)
    ConstraintLayout mItemKfRoot;
    private DataBaseBean baseBean;
    private int width;
    private int height;

    public DatabaseHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        this.width = (int) App.getContext().getResources().getDimension(R.dimen.kf_item_img_width);
        this.height = (int) App.getContext().getResources().getDimension(R.dimen.kf_item_img_height);
    }

    public void setData(DataBaseBean baseBean) {
        this.baseBean = baseBean;
        bindData();
    }

    private void bindData() {
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                baseBean.getCover(),
                mIvItemKf,
                width,height
        );
        mTvItemKfTitle.setText(baseBean.getName());
        mTvItemKfInfo.setText(baseBean.getAbs());
    }

    @OnClick(R.id.item_kf_root)
    public void onViewClicked() {
        Toast.makeText(App.getContext(), baseBean.getName() + "onClick", Toast.LENGTH_SHORT).show();
    }
}
