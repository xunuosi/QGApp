package sinolight.cn.qgapp.views.holder;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.data.http.entity.NewBookEntity;
import sinolight.cn.qgapp.utils.ImageUtil;
import sinolight.cn.qgapp.views.activity.KDBBookDetailActivity;

/**
 * Created by xns on 2017/7/5.
 * 首页顶部轮播图片
 */

public class NewBooksHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "NewBooksHolder";
    private HomeData mHomeData;
    private int width;
    private int height;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<NewBookEntity> datas;
    private NewBookAdapter mAdapter;
    private Context mContext;
    @BindView(R.id.rv_nb)
    RecyclerView mRvNb;
    @BindView(R.id.hf_item_nb_root)
    ConstraintLayout mHfItemNbRoot;

    public NewBooksHolder(View layout) {
        super(layout);
        ButterKnife.bind(this, itemView);

        mLayoutManager = new LinearLayoutManager(App.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRvNb.setLayoutManager(mLayoutManager);
        mRvNb.setHasFixedSize(true);
        mContext = App.getContext();
        width = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.hf_item_nb_width) /
                App.getContext().getResources().getDisplayMetrics().density);
        height = (int) (App.getContext().getResources().getDimensionPixelOffset(R.dimen.hf_item_nb_iv_height) /
                App.getContext().getResources().getDisplayMetrics().density);
    }

    public void setData(HomeData data) {
        mHomeData = data;
        transformData();

    }

    private void transformData() {
        datas = mHomeData.getDatas();
        if (datas == null || datas.isEmpty()) {
            for (int i = 0; i < 6; i++) {
                NewBookEntity bean = new NewBookEntity();
                datas.add(bean);
            }
        }
        bindData();
    }

    private void bindData() {
        mAdapter = new NewBookAdapter();
        mRvNb.setAdapter(mAdapter);
    }

    private class NewBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater mInflater;

        public NewBookAdapter() {
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NBItemHolder holder = null;
            holder = new NBItemHolder(mInflater.inflate(R.layout.item_new_book, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((NBItemHolder) holder).bindData(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }
    }

    public class NBItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_nb)
        SimpleDraweeView mIvItemNb;
        @BindView(R.id.tv_item_nb)
        TextView mTvItemNb;
        @BindView(R.id.item_hf_new_book_root)
        ConstraintLayout mItemHfNewBookRoot;
        private NewBookEntity mData;

        public NBItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(NewBookEntity bean) {
            mData = bean;
            if (bean.getId() != null) {
                ImageUtil.frescoShowImageByUri(mContext, bean.getCover(), mIvItemNb, width, height);
                mTvItemNb.setText(bean.getTitle());
            } else {
                ImageUtil.frescoShowImageByResId(mContext, R.drawable.ic_book_holder, mIvItemNb, width, height);
            }
        }

        @OnClick({R.id.item_hf_new_book_root})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.item_hf_new_book_root:
                    // Go to Book Info read
                    gotoBookInfo();
                    break;
            }
        }

        private void gotoBookInfo() {
            Intent callIntent = KDBBookDetailActivity.getCallIntent(App.getContext());
            callIntent.putExtra(AppContants.Resource.RES_ID, mData.getId());
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(callIntent);
        }
    }
}
