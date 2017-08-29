package sinolight.cn.qgapp.utils;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.adapter.CommonTitleAdapter;
import sinolight.cn.qgapp.adapter.CookAdapter;
import sinolight.cn.qgapp.adapter.KDBResAdapter;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.bean.SimpleCookInfo;
import sinolight.cn.qgapp.data.http.entity.BookEntity;
import sinolight.cn.qgapp.data.http.entity.ChapterEntity;
import sinolight.cn.qgapp.data.http.entity.CollectEntity;
import sinolight.cn.qgapp.data.http.entity.CookContentEntity;
import sinolight.cn.qgapp.data.http.entity.CookEntity;
import sinolight.cn.qgapp.data.http.entity.DBResArticleEntity;
import sinolight.cn.qgapp.data.http.entity.DBResPicEntity;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.data.http.entity.EBookEntity;
import sinolight.cn.qgapp.data.http.entity.MasterEntity;
import sinolight.cn.qgapp.data.http.entity.MaterialEntity;
import sinolight.cn.qgapp.data.http.entity.ResArticleEntity;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.data.http.entity.ResStandardEntity;
import sinolight.cn.qgapp.data.http.entity.ResWordEntity;
import sinolight.cn.qgapp.data.http.entity.StandardEntity;

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

    private static KDBResData transformCookData(String bean, int adapterType, boolean isSpan) {
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

    private static List<KDBResData> transformCookDatas(List<CookContentEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (CookContentEntity bean : beans) {
                resDataCollection.add(transformCookData(bean.getName(), CookAdapter.TYPE_TITLE, isSpan));
                for (String content : bean.getContent()) {
                    // If it is img, so it contains ".jpg"
                    if (content.contains(".jpg")) {
                        resDataCollection.add(transformCookData(content, CookAdapter.TYPE_IMG, isSpan));
                    } else {
                        resDataCollection.add(transformCookData(content, CookAdapter.TYPE_TEXT, isSpan));
                    }
                }
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    public static List<KDBResData> transformCookInfo(CookEntity<CookContentEntity> bean,
                                                     int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (bean != null) {
            resDataCollection = new ArrayList<>();
            resDataCollection.add(transformSimpleCookInfo(transform2SimpleCookInfo(bean), adapterType, isSpan));
            resDataCollection.addAll(transformCookDatas(bean.getData(), 0, isSpan));

            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static SimpleCookInfo transform2SimpleCookInfo(CookEntity bean) {
        SimpleCookInfo info = new SimpleCookInfo();
        info.setId(bean.getId());
        info.setCover(bean.getCover());
        info.setHascover(bean.isHascover());
        info.setName(bean.getName());
        info.setSource(bean.getSource());
        return info;
    }

    private static KDBResData transformSimpleCookInfo(SimpleCookInfo bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<SimpleCookInfo> resData = new KDBResData<>();
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
                resDataCollection.add(transformMaterialData(bean, adapterType, isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformMasterData(MasterEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<MasterEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformMasterDatas(List<MasterEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (MasterEntity bean : beans) {
                resDataCollection.add(transformMasterData(bean, adapterType, isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformChapterData(ChapterEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<ChapterEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformChapterDatas(List<ChapterEntity> beans, String pid, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (ChapterEntity bean : beans) {
                // Insert Pid
                bean.setPid(pid);
                resDataCollection.add(transformChapterData(bean, adapterType, isSpan));
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
                resDataCollection.add(transformBookData(bean, adapterType, isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformEBookData(EBookEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<EBookEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformEBookDatas(List<EBookEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (EBookEntity bean : beans) {
                resDataCollection.add(transformEBookData(bean, adapterType, isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformResWord2MaterialBean(ResWordEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<MaterialEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        MaterialEntity materialBean = new MaterialEntity();
        materialBean.setId(bean.getId());
        materialBean.setCover(bean.getCover());
        materialBean.setName(bean.getName());
        materialBean.setSource(bean.getSource());
        materialBean.setRemark(bean.getRemark());
        resData.setData(materialBean);
        return resData;
    }

    public static List<KDBResData> transformResWord2Material(List<ResWordEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (ResWordEntity bean : beans) {
                resDataCollection.add(transformResWord2MaterialBean(bean, adapterType, isSpan));
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
                resDataCollection.add(transformStandData(bean, adapterType, isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformHotArticleData(DBResArticleEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<DBResArticleEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformHotArticleDatas(List<DBResArticleEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (DBResArticleEntity bean : beans) {
                resDataCollection.add(transformHotArticleData(bean, adapterType, isSpan));
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
//                if (bean.getCover() != null) {
//                    resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_ARTICLE_ICON, isSpan));
//                } else {
//                    resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_ARTICLE, isSpan));
//                }
                resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_ARTICLE, isSpan));
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
//                if (bean.getCover() != null) {
//                    resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_INDUSTRY_ICON, isSpan));
//                } else {
//                    resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_INDUSTRY, isSpan));
//                }
                resDataCollection.add(transformArticleData(bean, KDBResAdapter.TYPE_INDUSTRY, isSpan));
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

    private static KDBResData transformPicData(DBResPicEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<DBResPicEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformPicDatas(List<DBResPicEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (DBResPicEntity bean : beans) {
                resDataCollection.add(transformPicData(bean, adapterType, isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    private static KDBResData transformVideoData(DBResVideoEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final KDBResData<DBResVideoEntity> resData = new KDBResData<>();
        resData.setItemType(adapterType);
        resData.setSpan(isSpan);
        resData.setLocal(false);
        resData.setData(bean);
        return resData;
    }

    public static List<KDBResData> transformVideoDatas(List<DBResVideoEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            for (DBResVideoEntity bean : beans) {
                resDataCollection.add(transformVideoData(bean, adapterType, isSpan));
            }
            mKDBResDataMap.put(adapterType, resDataCollection);
            return resDataCollection;
        }
        return null;
    }

    public static List<KDBResData> transformCollectDatas(List<CollectEntity> beans, int adapterType, boolean isSpan) {
        List<KDBResData> resDataCollection;
        if (beans != null && !beans.isEmpty()) {
            resDataCollection = new ArrayList<>();
            switch (adapterType) {
                case KDBResAdapter.TYPE_BOOK:
                    for (CollectEntity bean : beans) {
                        BookEntity transformBean = new BookEntity();
                        transformBean.setId(bean.getId());
                        transformBean.setAbs(bean.getAbs());
                        transformBean.setAuthor(bean.getAuthor());
                        transformBean.setCover(bean.getCover());
                        transformBean.setName(bean.getName());
                        transformBean.setDate(bean.getDate());
                        resDataCollection.add(transformBookData(transformBean, adapterType, isSpan));
                    }
                    break;
                case KDBResAdapter.TYPE_STANDARD:
                    for (CollectEntity bean : beans) {
                        ResStandardEntity transformBean = new ResStandardEntity();
                        transformBean.setId(bean.getId());
                        transformBean.setName(bean.getName());
                        transformBean.setCover(bean.getCover());
                        transformBean.setStdno(bean.getIsbn());
                        transformBean.setImdate(bean.getDate());
                        resDataCollection.add(transformStandData(transformBean, adapterType, isSpan));
                    }
                    break;
                case KDBResAdapter.TYPE_IMG:
                    for (CollectEntity bean : beans) {
                        ResImgEntity transformBean = new ResImgEntity();
                        transformBean.setId(bean.getId());
                        transformBean.setName(bean.getName());
                        transformBean.setCover(bean.getCover());
                        resDataCollection.add(transformImgData(transformBean, adapterType, isSpan));
                    }
                    break;
                case KDBResAdapter.TYPE_ARTICLE:
                    for (CollectEntity bean : beans) {
                        ResArticleEntity transformBean = new ResArticleEntity();
                        transformBean.setId(bean.getId());
                        transformBean.setName(bean.getName());
                        transformBean.setCover(bean.getCover());
                        transformBean.setAuthor(bean.getAuthor());
                        transformBean.setAbs(bean.getAbs());
                        transformBean.setSource(bean.getSource());
                        if (transformBean.getCover() != null) {
                            resDataCollection.add(transformArticleData(transformBean, KDBResAdapter.TYPE_ARTICLE_ICON, isSpan));
                        } else {
                            resDataCollection.add(transformArticleData(transformBean, KDBResAdapter.TYPE_ARTICLE, isSpan));
                        }
                    }
                    break;
                case KDBResAdapter.TYPE_WORD:
                    for (CollectEntity bean : beans) {
                        ResWordEntity transformBean = new ResWordEntity();
                        transformBean.setId(bean.getId());
                        transformBean.setName(bean.getName());
                        transformBean.setCover(bean.getCover());
                        transformBean.setRemark(bean.getAbs());
                        transformBean.setSource(bean.getSource());
                        resDataCollection.add(transformDicData(transformBean, adapterType, isSpan));
                    }
                    break;
                case KDBResAdapter.TYPE_INDUSTRY:
                    for (CollectEntity bean : beans) {
                        ResArticleEntity transformBean = new ResArticleEntity();
                        transformBean.setId(bean.getId());
                        transformBean.setName(bean.getName());
                        transformBean.setCover(bean.getCover());
                        transformBean.setAuthor(bean.getAuthor());
                        transformBean.setAbs(bean.getAbs());
                        transformBean.setSource(bean.getSource());
                        if (transformBean.getCover() != null) {
                            resDataCollection.add(transformArticleData(transformBean, KDBResAdapter.TYPE_INDUSTRY_ICON, isSpan));
                        } else {
                            resDataCollection.add(transformArticleData(transformBean, KDBResAdapter.TYPE_INDUSTRY, isSpan));
                        }
                    }
                    break;
                case CommonTitleAdapter.TYPE_MATERIAL:
                    for (CollectEntity bean : beans) {
                        MaterialEntity transformBean = new MaterialEntity();
                        transformBean.setId(bean.getId());
                        transformBean.setName(bean.getName());
                        transformBean.setCover(bean.getCover());
                        transformBean.setRemark(bean.getAbs());
                        transformBean.setSource(bean.getSource());
                        resDataCollection.add(transformMaterialData(transformBean, adapterType, isSpan));
                    }
                    break;
                case CommonTitleAdapter.TYPE_VIDEO:
                    for (CollectEntity bean : beans) {
                        DBResVideoEntity transformBean = new DBResVideoEntity();
                        transformBean.setId(bean.getId());
                        transformBean.setName(bean.getName());
                        transformBean.setCover(bean.getCover());
                        transformBean.setSource(bean.getSource());
                        transformBean.setAbs(bean.getAbs());
                        resDataCollection.add(transformVideoData(transformBean, adapterType, isSpan));
                    }
                    break;
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
