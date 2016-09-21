package forezp.com.rxjavaretrofitdemo.api;




import java.io.File;
import java.util.concurrent.TimeUnit;

import forezp.com.rxjavaretrofitdemo.MyApp;

import forezp.com.rxjavaretrofitdemo.utils.NetUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Werb on 2016/8/18.
 * Werb is Wanbo.
 * Contact Me : werbhelius@gmail.com
 * retrofit instance
 */
public class ApiRetrofit {

    public ZhihuApi ZhihuApiService;

    public static final String ZHIHU_BASE_URL = "http://news-at.zhihu.com/api/4/";


    public ZhihuApi getZhihuApiService() {
        return ZhihuApiService;
    }



    ApiRetrofit() {
        //cache url
        File httpCacheDirectory = new File(MyApp.mContext.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache).build();

        Retrofit retrofit_zhihu = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        ZhihuApiService = retrofit_zhihu.create(ZhihuApi.class);

    }

    //cache
//    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR=new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//
//            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
//            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
//            cacheBuilder.maxStale(365, TimeUnit.DAYS);
//            CacheControl cacheControl = cacheBuilder.build();
//
//            Request request = chain.request();
//            if (!StateUtils.isNetworkAvailable(MyApp.mContext)) {
//                request = request.newBuilder()
//                        .cacheControl(cacheControl)
//                        .build();
//
//            }
//            Response originalResponse = chain.proceed(request);
//            if (StateUtils.isNetworkAvailable(MyApp.mContext)) {
//                int maxAge = 0; // read from cache
//                return originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public ,max-age=" + maxAge)
//                        .build();
//            } else {
//                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//                return originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
//
//
//    }
    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {

        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        Request request = chain.request();
        if (!NetUtils.isNetworkAvailable(MyApp.mContext)) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();

        }
        Response originalResponse = chain.proceed(request);
        if (NetUtils.isNetworkAvailable(MyApp.mContext)) {
            int maxAge = 0; // read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };
}
