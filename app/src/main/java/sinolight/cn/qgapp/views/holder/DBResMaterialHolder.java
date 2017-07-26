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
import sinolight.cn.qgapp.data.http.entity.MaterialEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.utils.L;

/**
 * Created by xns on 2017/7/17.
 * 资源库标题holder
 */

public class DBResMaterialHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "DBResMaterialHolder";
    SimpleDraweeView mIvResdbMaterial;
    @BindView(R.id.tv_resdb_material_title)
    TextView mTvResdbMaterialTitle;
    @BindView(R.id.tv_resdb_material_info)
    TextView mTvResdbMaterialInfo;
    @BindView(R.id.tv_resdb_material_from)
    TextView mTvResdbMaterialFrom;
    @BindView(R.id.root_resdb_material)
    ConstraintLayout mRootResdbMaterial;
    private MaterialEntity mData;
    private int width;
    private int height;

    public DBResMaterialHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.resdb_materail_cover_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.resdb_materail_cover_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(KDBResData<MaterialEntity> data) {
        if (data != null) {
            mData = data.getData();
            bindData();
        }
    }

    private void bindData() {
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                mData.getCover(),
                mIvResdbMaterial,
                width,
                height
        );

        mTvResdbMaterialTitle.setText(mData.getName());
        mTvResdbMaterialInfo.setText(mData.getRemark());
        mTvResdbMaterialFrom.setText(formatStr(R.string.text_from_format, mData.getSource()));
    }

    private String formatStr(int baseStrId, String child) {
        String local = App.getContext().getString(baseStrId);
        return String.format(local, child);
    }

    @OnClick({R.id.root_resdb_material})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root_resdb_material:
                L.d(TAG, "onClick:" + mData.getName());
                break;
        }
    }
}
