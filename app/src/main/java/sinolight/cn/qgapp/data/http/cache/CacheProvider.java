package sinolight.cn.qgapp.data.http.cache;



import android.app.Activity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import sinolight.cn.qgapp.data.http.entity.ArticleEntity;
import sinolight.cn.qgapp.data.http.entity.BannerEntity;
import sinolight.cn.qgapp.data.http.entity.NewBookEntity;
import sinolight.cn.qgapp.data.http.entity.RecommendEntity;
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

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<ResultEntity<List<RecommendEntity>>> getRecommendWords(
            Observable<ResultEntity<List<RecommendEntity>>> oRepos,
            EvictProvider evictDynamicKey);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<ResultEntity<List<NewBookEntity>>> getNewsBooks(
            Observable<ResultEntity<List<NewBookEntity>>> oRepos,
            EvictProvider evictDynamicKey);

    @LifeCache(duration = 5, timeUnit = TimeUnit.MINUTES)
    Observable<ResultEntity<List<ArticleEntity>>> getNewsArticle(
            Observable<ResultEntity<List<ArticleEntity>>> oRepos,
            EvictProvider evictDynamicKey);
}