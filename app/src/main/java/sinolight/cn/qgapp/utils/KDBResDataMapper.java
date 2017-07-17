package sinolight.cn.qgapp.utils;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;
import sinolight.cn.qgapp.data.bean.KDBResData;
import sinolight.cn.qgapp.data.http.entity.BookEntity;

/**
 * Created by xns on 2017/7/5.
 * 首页数据转换成KDBResData的转换工具类
 */

public class KDBResDataMapper {
    private static SparseArray<List<KDBResData>> mKDBResDataMap = new SparseArray<>();


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

    public static SparseArray<List<KDBResData>> getKDBResDataMap() {
        return mKDBResDataMap;
    }

    public static void reset() {
        L.d("xns", "reset");
        mKDBResDataMap.clear();
    }
}
