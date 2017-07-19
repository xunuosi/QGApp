package sinolight.cn.qgapp.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.views.holder.ResArticleHolder;
import sinolight.cn.qgapp.views.holder.ResArticleWithPicHolder;
import sinolight.cn.qgapp.views.holder.ResBookHolder;
import sinolight.cn.qgapp.views.holder.ResImgHolder;
import sinolight.cn.qgapp.views.holder.ResStandardHolder;

/**
 * Created by xns on 2017/7/17..
 * 知识库资源列表的Adapter
 */

public class KDBResAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_BOOK = 0;
    public static final int TYPE_STANDARD = 1;
    public static final int TYPE_ARTICLE_ICON = 2;
    // 无图片时候的类型
    public static final int TYPE_ARTICLE = 3;
    public static final int TYPE_IMG = 4;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<KDBResData> mData;

    public KDBResAdapter(Context context, List<KDBResData> resData) {
        this.mContext = context;
        this.mData = new ArrayList<>();
        mData.addAll(resData);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_BOOK:
                holder = new ResBookHolder(mInflater.inflate(R.layout.item_db_res_book, parent, false));
                break;
            case TYPE_STANDARD:
                holder = new ResStandardHolder(mInflater.inflate(R.layout.item_db_res_standard, parent, false));
                break;
            case TYPE_ARTICLE:
                holder = new ResArticleHolder(mInflater.inflate(R.layout.item_hot_article, parent, false));
                break;
            case TYPE_ARTICLE_ICON:
                holder = new ResArticleWithPicHolder(mInflater.inflate(R.layout.item_icon_article, parent, false));
                break;
            case TYPE_IMG:
                holder = new ResImgHolder(mInflater.inflate(R.layout.item_db_res_img, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            switch (getItemViewType(position)) {
                case TYPE_BOOK:
                    ((ResBookHolder) holder).setData(mData.get(position));
                    break;
                case TYPE_STANDARD:
                    ((ResStandardHolder) holder).setData(mData.get(position));
                    break;
                case TYPE_ARTICLE:
                    ((ResArticleHolder) holder).setData(mData.get(position));
                    break;
                case TYPE_ARTICLE_ICON:
                    ((ResArticleWithPicHolder) holder).setData(mData.get(position));
                    break;
                case TYPE_IMG:
                    ((ResImgHolder) holder).setData(mData.get(position));
                    break;
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    public void setData(List<KDBResData> data) {
        if (mData != null) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

}
