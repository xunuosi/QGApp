package sinolight.cn.qgapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.views.holder.CookHeadHolder;
import sinolight.cn.qgapp.views.holder.CookImgHolder;
import sinolight.cn.qgapp.views.holder.CookTextHolder;
import sinolight.cn.qgapp.views.holder.CookTitleHolder;

/**
 * Created by xns on 2017/7/26.
 * 菜谱界面的Adapter
 */

public class CookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_IMG = 2;
    public static final int TYPE_TEXT = 3;


    private Context mContext;
    private LayoutInflater mInflater;
    private List<KDBResData> mData;

    public CookAdapter(Context context, List<KDBResData> resData) {
        this.mContext = context;
        this.mData = new ArrayList<>();
        mData.addAll(resData);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEAD:
                holder = new CookHeadHolder(mInflater.inflate(R.layout.item_cook_head, parent, false));
                break;
            case TYPE_TITLE:
                holder = new CookTitleHolder(mInflater.inflate(R.layout.item_cook_title, parent, false));
                break;
            case TYPE_IMG:
                holder = new CookImgHolder(mInflater.inflate(R.layout.item_cook_info_image, parent, false));
                break;
            case TYPE_TEXT:
                holder = new CookTextHolder(mInflater.inflate(R.layout.item_cook_info_text, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEAD:
                ((CookHeadHolder) holder).setData(mData.get(position));
                break;
            case TYPE_TITLE:
                ((CookTitleHolder) holder).setData(mData.get(position));
                break;
            case TYPE_IMG:
                ((CookImgHolder) holder).setData(mData.get(position));
                break;
            case TYPE_TEXT:
                ((CookTextHolder) holder).setData(mData.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<KDBResData> data) {
        if (mData != null) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

}
