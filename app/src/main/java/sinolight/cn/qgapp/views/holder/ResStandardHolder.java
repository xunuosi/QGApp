package sinolight.cn.qgapp.views.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.ResStandardEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.L;

/**
 * Created by xns on 2017/7/6.
 * 最新标准
 */

public class ResStandardHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "ResStandardHolder";
    private ResStandardEntity data;
    private int width;
    private int height;
    private String standardNum;
    private String exTime;

    @BindView(R.id.iv_db_res_item_standard)
    SimpleDraweeView mIvDbResItemStandard;
    @BindView(R.id.tv_db_res_item_title)
    TextView mTvDbResItemTitle;
    @BindView(R.id.tv_db_res_item_num)
    TextView mTvDbResItemNum;
    @BindView(R.id.tv_db_res_item_time)
    TextView mTvDbResItemTime;
    @BindView(R.id.db_res_standard_root)
    ConstraintLayout mDbResStandardRoot;

    public ResStandardHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        ButterKnife.bind(this, layout);
        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.hf_item_standard_img_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.hf_item_standard_img_height) /
                App.getContext().getResources().getDisplayMetrics().density);

    }

    public void setData(KDBResData<ResStandardEntity> data) {
        this.data = data.getData();
        bindData();
    }

    private void bindData() {
        ImageUtil.frescoShowImageByUri(App.getContext(),
                data.getCover(),
                mIvDbResItemStandard,
                width,
                height);
        mTvDbResItemTitle.setText(data.getName());

        String stanFormat = App.getContext().getString(R.string.text_standard_num);
        standardNum = String.format(stanFormat, data.getStdno());
        mTvDbResItemNum.setText(standardNum);

        String exTimeFormat = App.getContext().getString(R.string.text_e_time);
        exTime = String.format(exTimeFormat, data.getImdate());
        mTvDbResItemTime.setText(exTime);
    }

    @OnClick({R.id.db_res_standard_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.db_res_standard_root:
                L.d(TAG, "onClick:" + data.getName());
                break;
        }
    }
}
