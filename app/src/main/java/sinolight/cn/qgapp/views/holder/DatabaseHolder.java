package sinolight.cn.qgapp.views.holder;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.DataBaseBean;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.views.activity.DBaseDetailActivity;

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
    private String dbId;
    private String dbName;
    private AppContants.DataBase.Type dbType;

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

        dbId = baseBean.getId();
        dbName = baseBean.getName();
        dbType = getDbType(dbName);
    }

    private AppContants.DataBase.Type getDbType(String dbName) {
        AppContants.DataBase.Type type = null;
        if (dbName.contains("食品")) {
            type = AppContants.DataBase.Type.DB_FOOD;
            return type;
        } else if (dbName.contains("工艺美术")) {
            type = AppContants.DataBase.Type.DB_ART;
            return type;
        } else if (dbName.contains("造纸")) {
            type = AppContants.DataBase.Type.DB_PAPER;
            return type;
        } else if (dbName.contains("皮革")) {
            type = AppContants.DataBase.Type.DB_LEATHER;
            return type;
        } else if (dbName.contains("家具")) {
            type = AppContants.DataBase.Type.DB_FURNITURE;
            return type;
        } else if (dbName.contains("包装")) {
            type = AppContants.DataBase.Type.DB_PACK;
            return type;
        } else if (dbName.contains("服装")) {
            type = AppContants.DataBase.Type.DB_CLOTHING;
            return type;
        } else if (dbName.contains("机电")) {
            type = AppContants.DataBase.Type.DB_ELECTROMECHANICAL;
            return type;
        } else if (dbName.contains("衡器")) {
            type = AppContants.DataBase.Type.DB_WEIGHING;
            return type;
        }

        return null;
    }

    @OnClick(R.id.item_kf_root)
    public void onViewClicked() {
        Intent callIntent = DBaseDetailActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.DataBase.KEY_ID, dbId);
        callIntent.putExtra(AppContants.DataBase.KEY_NAME, dbName);
        callIntent.putExtra(AppContants.DataBase.KEY_TYPE, dbType);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }
}
