package sinolight.cn.qgapp.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.utils.HomeDataMapper;
import sinolight.cn.qgapp.views.holder.HomeBannerHolder;
import sinolight.cn.qgapp.views.holder.StoreHolder;

/**
 * Created by admin on 2017/7/4.
 * 首页适配器
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 首页顶端Banner
    public static final int TYPE_BANNER = 0;
    // 热门图集Banner
    public static final int TYPE_BANNER_IMAGES = 2;
    // 推荐词条Banner
    public static final int TYPE_BANNER_WORDS = 3;
    // 六大库分类
    public static final int TYPE_STORE = 1;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<HomeData> homeDatas;
    private List<HomeData> bannerList;
    private List<HomeData> storeList;

    public HomeAdapter(Context context, List<HomeData> dataMap) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.homeDatas = dataMap;
        bannerList = HomeDataMapper.getHomeDataMap().get(TYPE_BANNER);
        storeList = HomeDataMapper.getHomeDataMap().get(TYPE_STORE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_BANNER:
                holder = new HomeBannerHolder(mInflater.inflate(R.layout.item_banner, parent, false));
                break;
            case TYPE_STORE:
                holder = new StoreHolder(mInflater.inflate(R.layout.item_store, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_BANNER:
                ((HomeBannerHolder) holder).setData(homeDatas.get(position));
                break;
            case TYPE_STORE:
                ((StoreHolder) holder).setData(homeDatas.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return homeDatas == null ? 0 : homeDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return homeDatas.get(position).getItemType();
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    if (isFullSpanType(adapter.getItemViewType(position))) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    private boolean isFullSpanType(int type) {
        return type == TYPE_BANNER;
    }

}
