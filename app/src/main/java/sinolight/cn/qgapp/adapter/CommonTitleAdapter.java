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
import sinolight.cn.qgapp.data.http.entity.DBResPicEntity;
import sinolight.cn.qgapp.views.holder.DBResHotArticleHolder;
import sinolight.cn.qgapp.views.holder.DBResHotVideoHolder;
import sinolight.cn.qgapp.views.holder.DBResMaterialHolder;
import sinolight.cn.qgapp.views.holder.DBResPicHolder;
import sinolight.cn.qgapp.views.holder.DBResPicSetHolder;
import sinolight.cn.qgapp.views.holder.DBResTitleHolder;

/**
 * Created by xns on 2017/7/26.
 * 带通用标题的Adapter
 */

public class CommonTitleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_MATERIAL_TITLE = 300;
    public static final int TYPE_MATERIAL = 301;
    public static final int TYPE_ARTICLE_TITLE = 302;
    public static final int TYPE_ARTICLE = 303;
    public static final int TYPE_PIC_TITLE = 304;
    public static final int TYPE_PIC = 305;
    public static final int TYPE_PIC_SET = 306;
    public static final int TYPE_VIDEO_TITLE = 307;
    public static final int TYPE_VIDEO = 308;


    private Context mContext;
    private LayoutInflater mInflater;
    private List<KDBResData> mData;

    public CommonTitleAdapter(Context context, List<KDBResData> resData) {
        this.mContext = context;
        this.mData = new ArrayList<>();
        mData.addAll(resData);
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_MATERIAL_TITLE:
            case TYPE_ARTICLE_TITLE:
            case TYPE_PIC_TITLE:
            case TYPE_VIDEO_TITLE:
                holder = new DBResTitleHolder(mInflater.inflate(R.layout.item_dbres_common_title, parent, false));
                break;
            case TYPE_MATERIAL:
                holder = new DBResMaterialHolder(mInflater.inflate(R.layout.item_material, parent, false));
                break;
            case TYPE_ARTICLE:
                holder = new DBResHotArticleHolder(mInflater.inflate(R.layout.item_db_res_hot_article, parent, false));
                break;
            case TYPE_PIC:
                holder = new DBResPicHolder(mInflater.inflate(R.layout.item_db_res_hot_pic, parent, false));
                break;
            case TYPE_VIDEO:
                holder = new DBResHotVideoHolder(mInflater.inflate(R.layout.item_db_res_hot_pic, parent, false));
                break;
            case TYPE_PIC_SET:
                holder = new DBResPicSetHolder<DBResPicEntity>(mInflater.inflate(R.layout.item_db_res_pic_set, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_MATERIAL_TITLE:
                ((DBResTitleHolder) holder).setData(mData.get(position), TYPE_MATERIAL_TITLE);
                break;
            case TYPE_MATERIAL:
                ((DBResMaterialHolder) holder).setData(mData.get(position));
                break;
            case TYPE_ARTICLE_TITLE:
                ((DBResTitleHolder) holder).setData(mData.get(position), TYPE_ARTICLE_TITLE);
                break;
            case TYPE_ARTICLE:
                ((DBResHotArticleHolder) holder).setData(mData.get(position));
                break;
            case TYPE_PIC_TITLE:
                ((DBResTitleHolder) holder).setData(mData.get(position), TYPE_PIC_TITLE);
                break;
            case TYPE_PIC:
                ((DBResPicHolder) holder).setData(mData.get(position));
                break;
            case TYPE_PIC_SET:
                ((DBResPicSetHolder) holder).setData(mData.get(position), CommonTitleAdapter.TYPE_PIC_SET);
                break;
            case TYPE_VIDEO_TITLE:
                ((DBResTitleHolder) holder).setData(mData.get(position), TYPE_VIDEO_TITLE);
                break;
            case TYPE_VIDEO:
                ((DBResHotVideoHolder) holder).setData(mData.get(position), AppContants.Video.TYPE_HOT_VIDEO);
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
