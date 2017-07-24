package sinolight.cn.qgapp.data.http.api;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sinolight.cn.qgapp.data.bean.DataBaseBean;
import sinolight.cn.qgapp.data.http.entity.ArticleEntity;
import sinolight.cn.qgapp.data.http.entity.BannerEntity;
import sinolight.cn.qgapp.data.http.entity.BookEntity;
import sinolight.cn.qgapp.data.http.entity.BookInfoEntity;
import sinolight.cn.qgapp.data.http.entity.DBResTypeEntity;
import sinolight.cn.qgapp.data.http.entity.DicInfoEntity;
import sinolight.cn.qgapp.data.http.entity.NewBookEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.entity.RecommendEntity;
import sinolight.cn.qgapp.data.http.entity.ResArticleEntity;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.data.http.entity.ResStandardEntity;
import sinolight.cn.qgapp.data.http.entity.ResWordEntity;
import sinolight.cn.qgapp.data.http.entity.ResultEntity;
import sinolight.cn.qgapp.data.http.entity.StandardEntity;
import sinolight.cn.qgapp.data.http.entity.TokenEntity;
import sinolight.cn.qgapp.data.http.entity.VCodeEntity;


public interface ApiService {
    /**
     * 获取验证码
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("account/doVCode")
    Observable<ResultEntity<VCodeEntity>> getCode(
            @Field("time") String time
    );

    /**
     * 注册接口
     * @param name
     * @param email
     * @param pwd
     * @param repwd
     * @param vcode
     * @return
     */
    @FormUrlEncoded
    @POST("account/regist")
    Observable<ResultEntity<TokenEntity>> register(
            @Field("username") String name,
            @Field("email") String email,
            @Field("pwd") String pwd,
            @Field("repwd") String repwd,
            @Field("vcode") String vcode
    );

    /**
     * 登录接口
     * @param name
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("account/logOn")
    Observable<ResultEntity<TokenEntity>> login(
            @Field("username") String name,
            @Field("pwd") String pwd
    );

    /**
     * 首页轮播图片
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("home/doRotationChart")
    Observable<ResultEntity<List<BannerEntity>>> getHomeBanner(
            @Field("token") String token
    );

    /**
     * 首页-热门图集
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("home/doHotPics")
    Observable<ResultEntity<List<BannerEntity>>> getHotPics(
            @Field("token") String token
    );

    /**
     * 首页-最新标准
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("home/doNewestStdData")
    Observable<ResultEntity<List<StandardEntity>>> getNewestStdData(
            @Field("token") String token
    );

    /**
     * 首页-推荐词条
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("home/doRecommendEntry")
    Observable<ResultEntity<List<RecommendEntity>>> getRecommendWords(
            @Field("token") String token
    );

    /**
     * 首页-新书来袭
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("home/doNewsBooks")
    Observable<ResultEntity<List<NewBookEntity>>> getNewsBooks(
            @Field("token") String token
    );

    /**
     * 首页-热门文章
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("home/doNewsArticle")
    Observable<ResultEntity<List<ArticleEntity>>> getNewsArticle(
            @Field("token") String token
    );

    /**
     * 知识库-九大行业知识库列表
     * @param token
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("db/doIndustryDB")
    Observable<ResultEntity<PageEntity<List<DataBaseBean>>>> getKnowledgeDataBase(
            @Field("token") String token,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 某一知识库-六大资源列表分类树
     * @param token
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("theme/doThemeInfo")
    Observable<ResultEntity<List<DBResTypeEntity>>> getKDBResType(
            @Field("token") String token,
            @Field("type") String type
    );

    /**
     * 知识库-某一知识库-图书列表
     * @param token
     * @param token
     * @param dbid
     * @param themeType
     * @param key
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("db/doBookList")
    Observable<ResultEntity<PageEntity<List<BookEntity>>>> getKDBBookList(
            @Field("token") String token,
            @Field("dbid") String dbid,
            @Field("themeType") String themeType,
            @Field("key") String key,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 知识库-某一知识库-标准列表
     * @param token
     * @param token
     * @param dbid
     * @param themeType
     * @param key
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("db/doStdList")
    Observable<ResultEntity<PageEntity<List<ResStandardEntity>>>> getKDBStdList(
            @Field("token") String token,
            @Field("dbid") String dbid,
            @Field("themeType") String themeType,
            @Field("key") String key,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 知识库/资源库-某一知识库-文章or知识库/百科库-行业分析列表
     * @param token
     * @param dbid
     * @param themeType
     * @param key
     * @param type:0 文章，1 行业分析
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("db/doIndustryAnalysisList")
    Observable<ResultEntity<PageEntity<List<ResArticleEntity>>>> getKDBIndustryAnalysisList(
            @Field("token") String token,
            @Field("dbid") String dbid,
            @Field("themeType") String themeType,
            @Field("key") String key,
            @Field("type") int type,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 知识库-图片列表信息
     * @param token
     * @param dbid
     * @param themeType
     * @param key
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("db/doPicInfo")
    Observable<ResultEntity<PageEntity<List<ResImgEntity>>>> getKDBPicList(
            @Field("token") String token,
            @Field("dbid") String dbid,
            @Field("themeType") String themeType,
            @Field("key") String key,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 识库/资源库-某一知识库-词典（推荐词条/全部词条）列表
     * @param token
     * @param dbid
     * @param type:0 推荐词条，1 全部词条
     * @param key
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("db/doEntryList")
    Observable<ResultEntity<PageEntity<List<ResWordEntity>>>> getKDBWordList(
            @Field("token") String token,
            @Field("dbid") String dbid,
            @Field("key") String key,
            @Field("type") int type,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 知识库-图书详情信息
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("db/doBookInfo")
    Observable<ResultEntity<BookInfoEntity>> getKDBBookInfo(
            @Field("token") String token,
            @Field("id") String id
    );

    /**
     * 知识库-词条详情信息
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("db/doEntryInfo")
    Observable<ResultEntity<DicInfoEntity>> getKDBEntryInfo(
            @Field("token") String token,
            @Field("id") String id
    );
}
