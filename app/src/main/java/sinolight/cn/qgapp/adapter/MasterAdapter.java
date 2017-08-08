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
import sinolight.cn.qgapp.views.holder.MasterHolder;

/**
 * Created by xns on 2017/7/26.
 * 专家界面的Adapter
 */

public class MasterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_MASTER_LIST = 0;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<KDBResData> mData;

    public MasterAdapter(Context context, List<KDBResData> resData) {
        this.mContext = context;
        this.mData = new ArrayList<>();
        mData.addAll(resData);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_MASTER_LIST:
                holder = new MasterHolder(mInflater.inflate(R.layout.item_master, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_MASTER_LIST:
                ((MasterHolder) holder).setData(mData.get(position));
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
