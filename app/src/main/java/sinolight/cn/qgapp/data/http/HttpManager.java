package sinolight.cn.qgapp.data.http;

import android.content.Context;
import android.util.Log;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.internal.RxCache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sinolight.cn.qgapp.AppContants;
import sinolight.cn.qgapp.data.http.api.ApiService;
import sinolight.cn.qgapp.data.http.cache.CacheProvider;
import sinolight.cn.qgapp.data.http.entity.ResultEntity;
import sinolight.cn.qgapp.data.http.entity.TokenEntity;
import sinolight.cn.qgapp.data.http.entity.VCodeEntity;
import sinolight.cn.qgapp.data.http.exception.ApiException;
import sinolight.cn.qgapp.data.http.parser.GsonTSpeaker;

public class HttpManager {
    public static final String TAG=HttpManager.class.getSimpleName();
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit mRetrofit;
    private ApiService mApiService;
    private final CacheProvider cacheProvider;
    private static Context mContext;
    private volatile static HttpManager instance;

    private HttpManager() {
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("HttpManager",message);
            }
        });
        loggingInterceptor.setLevel(level);
        //拦截请求和响应日志并输出，其实有很多封装好的日志拦截插件，大家也可以根据个人喜好选择。
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor);

        OkHttpClient okHttpClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AppContants.BASE_URL)
                .client(okHttpClient)
                .build();

        cacheProvider = new RxCache.Builder()
                .persistence(mContext.getFilesDir(), new GsonTSpeaker())
                .using(CacheProvider.class);

        mApiService = mRetrofit.create(ApiService.class);
    }

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public static void init(Context context){
        mContext=context;
    }

    private <T> void toSubscribe(Observable<ResultEntity<T>> o, Observer<T> s) {
        o.subscribeOn(Schedulers.io())
                .map(new Function<ResultEntity<T>, T>() {
                    @Override
                    public T apply(@NonNull ResultEntity<T> response) throws Exception {
                        int code= response.getCode();
                        if (code != AppContants.SUCCESS_CODE) {
                            throw new ApiException(code, response.getMessage());
                        } else {
                            return response.getResult();
                        }
                    }
                })
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

//    public void getDatasWithCache(Observer<TestBean> subscriber, int pno, int ps, String dtype, boolean update) {
//        toSubscribe(cacheProvider.getDatas(mApiService.getDatas(pno, ps,dtype),new EvictProvider(update)), subscriber);
//    }
//    public void getDatasNoCache(Observer<TestBean> subscriber, int pno, int ps, String dtype) {
//        toSubscribe(mApiService.getDatas(pno, ps,dtype), subscriber);
//    }

    public void getCode(Observer<VCodeEntity> subscriber, String time) {
        toSubscribe(mApiService.getCode(time), subscriber);
    }

    public void register(Observer<TokenEntity> subscriber, String name, String email, String pwd, String repwd, String vcode) {
        toSubscribe(mApiService.register(name, email, pwd, repwd, vcode), subscriber);
    }

    public void login(Observer<TokenEntity> subscriber, String name, String pwd) {
        toSubscribe(mApiService.login(name, pwd), subscriber);
    }
}
