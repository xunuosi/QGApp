package sinolight.cn.qgapp.data.http.api;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import sinolight.cn.qgapp.data.http.entity.ResultEntity;
import sinolight.cn.qgapp.data.http.entity.VCodeEntity;


public interface ApiService {

    @FormUrlEncoded
    @POST("account/doVCode")
    Observable<ResultEntity<VCodeEntity>> getCode(
            @Field("time") String time
    );

}
