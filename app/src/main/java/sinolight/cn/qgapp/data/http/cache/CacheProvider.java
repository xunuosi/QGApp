package sinolight.cn.qgapp.data.http.cache;



import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import sinolight.cn.qgapp.data.http.entity.BannerEntity;
import sinolight.cn.qgapp.data.http.entity.ResultEntity;
import sinolight.cn.qgapp.data.http.entity.StandardEntity;

public interface CacheProvider {

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<ResultEntity<List<BannerEntity>>> getHomeBanner(
            Observable<ResultEntity<List<BannerEntity>>> oRepos,
            EvictProvider evictDynamicKey);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<ResultEntity<List<BannerEntity>>> getHotPics(
            Observable<ResultEntity<List<BannerEntity>>> oRepos,
            EvictProvider evictDynamicKey);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<ResultEntity<List<StandardEntity>>> getNewestStdData(
            Observable<ResultEntity<List<StandardEntity>>> oRepos,
            EvictProvider evictDynamicKey);
}