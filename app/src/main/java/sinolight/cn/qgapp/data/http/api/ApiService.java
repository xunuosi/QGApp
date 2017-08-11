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
import sinolight.cn.qgapp.data.http.entity.ChapterEntity;
import sinolight.cn.qgapp.data.http.entity.CookContentEntity;
import sinolight.cn.qgapp.data.http.entity.CookEntity;
import sinolight.cn.qgapp.data.http.entity.DBResArticleEntity;
import sinolight.cn.qgapp.data.http.entity.DBResPicEntity;
import sinolight.cn.qgapp.data.http.entity.DBResTypeEntity;
import sinolight.cn.qgapp.data.http.entity.DBResVideoEntity;
import sinolight.cn.qgapp.data.http.entity.DicInfoEntity;
import sinolight.cn.qgapp.data.http.entity.EBookEntity;
import sinolight.cn.qgapp.data.http.entity.MasterEntity;
import sinolight.cn.qgapp.data.http.entity.MaterialEntity;
import sinolight.cn.qgapp.data.http.entity.NewBookEntity;
import sinolight.cn.qgapp.data.http.entity.PageEntity;
import sinolight.cn.qgapp.data.http.entity.ReaderEntity;
import sinolight.cn.qgapp.data.http.entity.RecommendEntity;
import sinolight.cn.qgapp.data.http.entity.ResArticleEntity;
import sinolight.cn.qgapp.data.http.entity.ResImgEntity;
import sinolight.cn.qgapp.data.http.entity.ResStandardEntity;
import sinolight.cn.qgapp.data.http.entity.ResWordEntity;
import sinolight.cn.qgapp.data.http.entity.ResultEntity;
import sinolight.cn.qgapp.data.http.entity.StandardEntity;
import sinolight.cn.qgapp.data.http.entity.StdInfoEntity;
import sinolight.cn.qgapp.data.http.entity.TokenEntity;
import sinolight.cn.qgapp.data.http.entity.UserEntity;
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
     * 资源库-图集列表
     * @param token
     * @param key
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("Res/DoPicList")
    Observable<ResultEntity<PageEntity<List<DBResPicEntity>>>> getKDBPicList(
            @Field("token") String token,
            @Field("key") String key,
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
    Observable<ResultEntity<PageEntity<List<ResImgEntity>>>> getKDBPicInfo(
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
     * @param type:0 推荐词条，1 全部词条, 2 菜谱
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
            @Field("themeType") String themeType,
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
     * 知识库-标准详情信息
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("db/doStdInfo")
    Observable<ResultEntity<StdInfoEntity>> getKDBStdInfo(
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

    /**
     * 个人中心-个人资料
     * @param token
     * @param user
     * @return
     */
    @FormUrlEncoded
    @POST("account/doUserInfo")
    Observable<ResultEntity<UserEntity>> getUserInfo(
            @Field("token") String token,
            @Field("user") String user
    );

    /**
     * 个人中心-修改密码
     * @param token
     * @param oldPwd
     * @param newPwd
     * @param reNewPwd
     * @param user
     * @return
     */
    @FormUrlEncoded
    @POST("account/changePwd")
    Observable<ResultEntity<Object>> changePwd(
            @Field("token") String token,
            @Field("oldPwd") String oldPwd,
            @Field("newPwd") String newPwd,
            @Field("reNewPwd") String reNewPwd,
            @Field("user") String user

    );

    /**
     * 资源库-热门素材
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("res/doHotMenu")
    Observable<ResultEntity<List<MaterialEntity>>> getHotMenu(
            @Field("token") String token
    );

    /**
     * 资源库-热门文章
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("res/doHotArticle")
    Observable<ResultEntity<List<DBResArticleEntity>>> getHotArticle(
            @Field("token") String token
    );

    /**
     * 资源库-热门图片
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("res/doHotPic")
    Observable<ResultEntity<List<DBResPicEntity>>> getHotPic(
            @Field("token") String token
    );

    /**
     * 资源库-热门视频
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("res/doHotVideo")
    Observable<ResultEntity<List<DBResVideoEntity>>> getHotVideo(
            @Field("token") String token
    );

    /**
     * 资源库-视频集列表
     * @param token
     * @param key
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("res/doVideoLdbList")
    Observable<ResultEntity<PageEntity<List<DBResVideoEntity>>>> getVideoLdbList(
            @Field("token") String token,
            @Field("key") String key,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 资源库-视频列表
     * @param token
     * @param dbid:视频集id
     * @param key
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("res/doVideoList")
    Observable<ResultEntity<PageEntity<List<DBResVideoEntity>>>> getVideoList(
            @Field("token") String token,
            @Field("dbid") String dbid,
            @Field("key") String key,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 知识库-视频详情信息
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("res/doVideoInfo")
    Observable<ResultEntity<DBResVideoEntity>> getVideoInfo(
            @Field("token") String token,
            @Field("id") String id
    );

    /**
     * 知识库/资源库-图书/标准的章节目录
     * @param token
     * @param pid:图书/标准id
     * @param type:1-图书，2-标准
     * @return
     */
    @FormUrlEncoded
    @POST("db/doCatalogList")
    Observable<ResultEntity<List<ChapterEntity>>> getCatalogList(
            @Field("token") String token,
            @Field("pid") String pid,
            @Field("type") String type
    );

    /**
     * 知识库-在线阅读
     * @param token
     * @param restype
     * @param resID
     * @param chapterid
     * @return
     */
    @FormUrlEncoded
    @POST("db/doReader")
    Observable<ResultEntity<ReaderEntity>> doReader(
            @Field("token") String token,
            @Field("restype") String restype,
            @Field("resid") String resID,
            @Field("chapterid") String chapterid
    );

    /**
     * EBook List
     * @param token
     * @param key
     * @param type
     * @param order
     * @param themeType
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("ebook/doeBookList")
    Observable<ResultEntity<PageEntity<List<EBookEntity>>>> getEBookList(
            @Field("token") String token,
            @Field("key") String key,
            @Field("type") String type,
            @Field("order") String order,
            @Field("themeType") String themeType,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 资源库-菜谱详情信息
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("res/doMenuInfo")
    Observable<ResultEntity<CookEntity<CookContentEntity>>> getCookInfo(
            @Field("token") String token,
            @Field("id") String id
    );

    /**
     * 行业专家-特别推荐专家
     * @param token
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("expert/doTopExpert")
    Observable<ResultEntity<MasterEntity>> getMasterTop(
            @Field("token") String token,
            @Field("key") String key
    );

    /**
     * 行业专家-新锐专家
     * @param token
     * @param key
     * @return
     */
    @FormUrlEncoded
    @POST("expert/doHotExpert")
    Observable<ResultEntity<List<MasterEntity>>> getMasterHotList(
            @Field("token") String token,
            @Field("key") String key
    );

    /**
     * 行业专家列表
     * @param token
     * @param key
     * @param type
     * @param page
     * @param size
     * @return
     */
    @FormUrlEncoded
    @POST("expert/doExpertList")
    Observable<ResultEntity<PageEntity<List<MasterEntity>>>> getMasterList(
            @Field("token") String token,
            @Field("key") String key,
            @Field("type") String type,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 行业专家详情信息
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("expert/doExpertInfo")
    Observable<ResultEntity<MasterEntity>> getMasterInfo(
            @Field("token") String token,
            @Field("id") String id
    );
}
