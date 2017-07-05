package sinolight.cn.qgapp.data.http.cache;



import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import sinolight.cn.qgapp.data.http.entity.BannerEntity;
import sinolight.cn.qgapp.data.http.entity.ResultEntity;

public interface CacheProvider {

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<ResultEntity<BannerEntity>> getHomeBanner(
            Observable<ResultEntity<BannerEntity>> oRepos,
            EvictProvider evictDynamicKey);

}