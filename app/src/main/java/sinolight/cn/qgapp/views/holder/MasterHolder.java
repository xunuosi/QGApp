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
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.EBookEntity;
import sinolight.cn.qgapp.data.http.entity.MasterEntity;
import sinolight.cn.qgapp.utils.ImageUtil;

/**
 * Created by xns on 2017/7/6.
 * 电子书城的Holder
 */

public class MasterHolder extends RecyclerView.ViewHolder {
    private MasterEntity mData;
    @BindView(R.id.iv_item_master)
    SimpleDraweeView mIvItemMaster;
    @BindView(R.id.tv_item_master_title)
    TextView mTvItemMasterTitle;
    @BindView(R.id.tv_item_master_field)
    TextView mTvItemMasterField;
    @BindView(R.id.tv_item_master_production)
    TextView mTvItemMasterProduction;
    @BindView(R.id.item_root_master)
    ConstraintLayout mItemRootMaster;

    public MasterHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);

    }

    public void setData(KDBResData<MasterEntity> data) {
        if (data != null && data.getData() != null) {
            mData = data.getData();
            bindData();
        }
    }

    private void bindData() {
        int width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.db_res_book_icon_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        int height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.db_res_book_icon_height) /
                App.getContext().getResources().getDisplayMetrics().density);
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                mData.getCover(),
                mIvItemMaster,
                width,
                height
        );

        mTvItemMasterTitle.setText(mData.getName());
        mTvItemMasterField.setText(formatStr(R.string.text_master_field_format, mData.getDomain()));
        mTvItemMasterProduction.setText(formatStr(R.string.text_master_findings_format, mData.getRemark()));
    }

    private String formatStr(int baseStrId, String child) {
        String local = App.getContext().getString(baseStrId);
        return String.format(local, child);
    }

    @OnClick({R.id.item_root_master})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_root_master:
                // Go to MasterInfo Activity
                gotoMasterInfoActivity();
                break;
        }
    }

    private void gotoMasterInfoActivity() {
//        Intent callIntent = ReadActivity.getCallIntent(App.getContext());
//        callIntent.putExtra(AppContants.Read.READ_NAME, mHomeData.getTitle());
//        callIntent.putExtra(AppContants.Read.READ_ID, mHomeData.getId());
//        callIntent.putExtra(AppContants.Read.CHAPTERED_ID, "");
//        callIntent.putExtra(AppContants.Read.READ_RES_TYPE, AppContants.Read.Type.TYPE_ARTICLE);
//        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        App.getContext().startActivity(callIntent);
    }
}
