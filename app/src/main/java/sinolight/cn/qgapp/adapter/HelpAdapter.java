package sinolight.cn.qgapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.HelpDataEntity;
import sinolight.cn.qgapp.views.holder.DatabaseHolder;
import sinolight.cn.qgapp.views.holder.HelpHolder;

/**
 * Created by xns on 2017/7/10.
 * Help's Adapter
 */

public class HelpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<HelpDataEntity> mList;
    private LayoutInflater mInflater;

    public HelpAdapter(Context context, List<HelpDataEntity> list) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        holder = new DatabaseHolder(mInflater.inflate(R.layout.item_help, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HelpHolder)holder).setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<HelpDataEntity> data) {
        if (mList != null) {
            mList.clear();
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }
}
