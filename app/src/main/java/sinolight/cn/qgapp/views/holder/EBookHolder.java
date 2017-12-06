package sinolight.cn.qgapp.views.holder;

import android.content.Intent;
import android.net.Uri;
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
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.EBookEntity;
import sinolight.cn.qgapp.utils.ImageUtil;

/**
 * Created by xns on 2017/7/6.
 * 电子书城的Holder
 */

public class EBookHolder extends RecyclerView.ViewHolder {
    private EBookEntity mData;
    private int width;
    private int height;
    @BindView(R.id.iv_item_ebook)
    SimpleDraweeView mIvItemEbook;
    @BindView(R.id.tv_item_ebook_title)
    TextView mTvItemEbookTitle;
    @BindView(R.id.tv_item_ebook_info)
    TextView mTvItemEbookInfo;
    @BindView(R.id.tv_item_ebook_price)
    TextView mTvItemEbookPrice;
    @BindView(R.id.tv_item_ebook_price_holder)
    TextView mTvItemEbookPriceHolder;
    @BindView(R.id.item_root_ebook)
    ConstraintLayout mItemRootEbook;

    public EBookHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, layout);
        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.db_res_book_icon_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.db_res_book_icon_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(KDBResData<EBookEntity> data) {
        if (data != null) {
            mData = data.getData();
            bindData();
        }
    }

    private void bindData() {
        ImageUtil.frescoShowImageByUri(
                App.getContext(),
                mData.getCover(),
                mIvItemEbook,
                width,
                height
        );

        mTvItemEbookTitle.setText(mData.getName());
        mTvItemEbookInfo.setText(mData.getAbs());
//        mTvItemEbookPriceHolder.setText(App.getContext().getString(R.string.text_price2));
        mTvItemEbookPrice.setText(formatStr(R.string.text_price_format, mData.getPrice()));
    }

    private String formatStr(int baseStrId, String child) {
        String local = App.getContext().getString(baseStrId);
        return String.format(local, child);
    }

    @OnClick({R.id.item_root_ebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_root_ebook:
                // Go to Article read
                gotoInternet();
                break;
        }
    }

    private void gotoInternet() {
        String url = null;
        if (mData.getUrl() != null && !mData.getUrl().isEmpty()) {
            url = mData.getUrl();
        } else {
            url = "https://zgqgycbs.tmall.com/";
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getContext().startActivity(intent);
    }

}
