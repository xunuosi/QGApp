package sinolight.cn.qgapp.utils;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.BookEntity;
import sinolight.cn.qgapp.data.http.entity.MaterialEntity;
import sinolight.cn.qgapp.data.http.entity.ResArticleEntity;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.data.http.entity.ResStandardEntity;
import sinolight.cn.qgapp.data.http.entity.ResWordEntity;

/**
 * Created by xns on 2017/7/5.
 * 首页数据转换成KDBResData的转换工具类
 */

public class KDBResDataMapper {
    private static SparseArray<List<KDBResData>> mKDBResDataMap = new SparseArray<>();

    public static KDBResData transformTitleData(String bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<String> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    private static KDBResData transformMaterialData(MaterialEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<MaterialEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformMaterialDatas(List<MaterialEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (MaterialEntity bean : beans) {
                resDataCollection.add(transformMaterialData(bean,adapterType,isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }


    private static KDBResData transformBookData(BookEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<BookEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformBookDatas(List<BookEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (BookEntity bean : beans) {
                resDataCollection.add(transformBookData(bean,adapterType,isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformStandData(ResStandardEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<ResStandardEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformStandDatas(List<ResStandardEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (ResStandardEntity bean : beans) {
                resDataCollection.add(transformStandData(bean,adapterType,isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformArticleData(ResArticleEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<ResArticleEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformArticleDatas(List<ResArticleEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (ResArticleEntity bean : beans) {
                if (bean.getCover() != null) {
                    resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_ARTICLE_ICON, isSpan));
                } else {
                    resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_ARTICLE, isSpan));
                }
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    public static List<KDBResData> transformIndustryDatas(List<ResArticleEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (ResArticleEntity bean : beans) {
                if (bean.getCover() != null) {
                    resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_ARTICLE_ICON, isSpan));
                } else {
                    resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_ARTICLE, isSpan));
                }
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformImgData(ResImgEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<ResImgEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformImgDatas(List<ResImgEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (ResImgEntity bean : beans) {
                resDataCollection.add(transformImgData(bean, adapterType, isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformDicData(ResWordEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<ResWordEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformDicDatas(List<ResWordEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (ResWordEntity bean : beans) {
                resDataCollection.add(transformDicData(bean, adapterType, isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    public static SparseArray<List<KDBResData>> getKDBResDataMap() {
        return mKDBResDataMap;
    }

    public static void reset() {
        L.d("xns", "reset");
        mKDBResDataMap.clear();
    }
}
