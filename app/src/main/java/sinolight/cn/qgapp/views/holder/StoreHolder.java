package sinolight.cn.qgapp.views.holder;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.views.activity.BaiKeActivity;
import sinolight.cn.qgapp.views.activity.DBResourceActivity;
import sinolight.cn.qgapp.views.activity.EBookActivity;
import sinolight.cn.qgapp.views.activity.MasterHomeActivity;

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

    @OnClick({R.id.store_hf_head_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.store_hf_head_root:
                onClickStoreType(homeData.getHomeType());
                break;
        }
    }

    private void onClickStoreType(AppContants.HomeStore.Type homeType) {
        switch (homeType) {
            case TYPE_DB_KNOWLEDGE:
                onClickKnowledge();
                break;
            case TYPE_DB_RES:
                onClickRes();
                break;
            case TYPE_DB_BAIKE:
                onClickBaiKe();
                break;
            case TYPE_DB_STANDARD:
                onClickStandard();
                break;
            case TYPE_EBOOK:
                onClickEBook();
                break;
            case TYPE_MASTER:
                onClickMaster();
                break;
        }
    }

    private void onClickBaiKe() {
        Intent callIntent = BaiKeActivity.getCallIntent(App.getContext());
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }

    private void onClickMaster() {
        Intent callIntent = MasterHomeActivity.getCallIntent(App.getContext());
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }

    private void onClickStandard() {
        Intent callIntent = DBResourceActivity.getCallIntent(App.getContext());
        callIntent.putExtra(AppContants.DataBase.KEY_TYPE, AppContants.DataBase.Type.DB_STANDARD);
        callIntent.putExtra(AppContants.DataBase.KEY_RES_TYPE, AppContants.DataBase.Res.RES_STANDARD);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }

    private void onClickEBook() {
        Intent callIntent = EBookActivity.getCallIntent(App.getContext());
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(callIntent);
    }

    private void onClickRes() {
        EventBus.getDefault().post(AppContants.HomeStore.Type.TYPE_DB_RES);
    }

    private void onClickKnowledge() {
        EventBus.getDefault().post(AppContants.HomeStore.Type.TYPE_DB_KNOWLEDGE);
    }
}
