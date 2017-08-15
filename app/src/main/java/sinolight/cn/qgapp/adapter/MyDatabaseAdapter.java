package sinolight.cn.qgapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.DataBaseBean;
import sinolight.cn.qgapp.views.holder.MyDatabaseHolder;

/**
 * Created by xns on 2017/7/10.
 * 行业知识库的Adapter
 */

public class MyDatabaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<DataBaseBean> mData;
    private LayoutInflater mInflater;

    public MyDatabaseAdapter(Context context, List<DataBaseBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        holder = new MyDatabaseHolder(mInflater.inflate(R.layout.item_my_database, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyDatabaseHolder) holder).setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<DataBaseBean> data) {
        if (mData != null) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }
}
