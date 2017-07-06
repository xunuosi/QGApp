package sinolight.cn.qgapp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sinolight.cn.qgapp.App;
import sinolight.cn.qgapp.data.bean.HomeData;
import sinolight.cn.qgapp.data.bean.LocalDataBean;
import sinolight.cn.qgapp.data.http.entity.BannerEntity;
import sinolight.cn.qgapp.data.http.entity.StandardEntity;

/**
 * Created by xns on 2017/7/5.
 * 首页数据转换成HomeData的转换工具类
 */

public class HomeDataMapper {
    private static Map<Integer, List<HomeData>> homeDataMap = new HashMap<>();

    public static HomeData transformLocalData(LocalDataBean bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final HomeData homeData = new HomeData();
        homeData.setTitle(App.getContext().getString(bean.getText()));
        homeData.setResId(bean.getResId());
        homeData.setItemType(adapterType);
        homeData.setSpan(isSpan);
        homeData.setLocal(true);
        return homeData;
    }

    public static List<HomeData> transformLocalDatas(List<LocalDataBean> localDataBeans
            ,int adapterType, boolean isSpan) {
        List<HomeData> homedataCollection;

        if (localDataBeans != null && !localDataBeans.isEmpty()) {
            homedataCollection = new ArrayList<>();
            for (LocalDataBean bean : localDataBeans) {
                homedataCollection.add(transformLocalData(bean,adapterType,isSpan));
            }
        } else {
            homedataCollection = Collections.emptyList();
        }
        homeDataMap.put(adapterType, homedataCollection);
        return homedataCollection;
    }

    public static List<HomeData> transformBannerDatas(List<BannerEntity> beans,int adapterType, boolean isSpan) {
        List<HomeData> homeDataCollection;
        if (beans != null && !beans.isEmpty()) {
            homeDataCollection = new ArrayList<>();
            HomeData<BannerEntity> bean = new HomeData<>();
            bean.setLocal(false);
            bean.setSpan(isSpan);
            bean.setItemType(adapterType);
            bean.setDatas(beans);
            homeDataCollection.add(bean);
        } else {
            homeDataCollection = new ArrayList<>();
            HomeData<BannerEntity> bean = new HomeData<>();
            bean.setLocal(true);
            bean.setSpan(isSpan);
            bean.setItemType(adapterType);
            homeDataCollection.add(bean);
        }
        homeDataMap.put(adapterType, homeDataCollection);
        return homeDataCollection;
    }

    public static HomeData transformStandardData(StandardEntity bean, int adapterType, boolean isSpan) {
        if (bean == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final HomeData homeData = new HomeData();
        homeData.setId(bean.getId());
        homeData.setTitle(bean.getTitle());
        homeData.setUrl(bean.getCover());
        homeData.setStdno(bean.getStdno());
        homeData.setImdate(bean.getImdate());
        homeData.setItemType(adapterType);
        homeData.setSpan(isSpan);
        homeData.setLocal(false);
        return homeData;
    }

    public static List<HomeData> transformStandardDatas(List<StandardEntity> beans, int adapterType, boolean isSpan) {
        List<HomeData> homedataCollection;
        if (beans != null && !beans.isEmpty()) {
            homedataCollection = new ArrayList<>();
            for (StandardEntity bean : beans) {
                homedataCollection.add(transformStandardData(bean,adapterType,isSpan));
            }
        } else {
            homedataCollection = new ArrayList<>();
            HomeData bean = new HomeData();
            bean.setLocal(true);
            bean.setSpan(isSpan);
            bean.setItemType(adapterType);
            homedataCollection.add(bean);
        }
        homeDataMap.put(adapterType, homedataCollection);
        return homedataCollection;
    }

    public static Map<Integer, List<HomeData>> getHomeDataMap() {
        return homeDataMap;
    }

    public static void reset() {
        L.d("xns", "reset");
        homeDataMap.clear();
    }
}
