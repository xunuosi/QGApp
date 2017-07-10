package sinolight.cn.qgapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.DataBaseBean;
import sinolight.cn.qgapp.views.holder.DatabaseHolder;

/**
 * Created by xns on 2017/7/10.
 * 行业知识库的Adapter
 */

public class KnowledgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<DataBaseBean> mList;
    private LayoutInflater mInflater;

    public KnowledgeAdapter(Context context, List<DataBaseBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        holder = new DatabaseHolder(mInflater.inflate(R.layout.item_database, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DatabaseHolder) holder).setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


}
