package sinolight.cn.qgapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.R;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.views.holder.DBResHotVideoHolder;

/**
 * Created by xns on 2017/7/26.
 * 视频列表的Adapter
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_VIDEO_SET = 300;
    public static final int TYPE_VIDEO_LIST = 301;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<KDBResData> mData;

    public VideoAdapter(Context context, List<KDBResData> resData) {
        this.mContext = context;
        this.mData = new ArrayList<>();
        mData.addAll(resData);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_VIDEO_SET:
                holder = new DBResHotVideoHolder(mInflater.inflate(R.layout.item_db_res_hot_pic, parent, false));
                break;
            case TYPE_VIDEO_LIST:
                holder = new DBResHotVideoHolder(mInflater.inflate(R.layout.item_db_res_hot_pic, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_VIDEO_SET:
                ((DBResHotVideoHolder) holder).setData(mData.get(position), AppContants.Video.TYPE_SET_VIDEO);
                break;
            case TYPE_VIDEO_LIST:
                ((DBResHotVideoHolder) holder).setData(mData.get(position), AppContants.Video.TYPE_LIST_VIDEO);
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
