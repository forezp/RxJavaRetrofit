package forezp.com.rxjavaretrofitdemo.api;



import forezp.com.rxjavaretrofitdemo.bean.NewsTimeLine;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public interface ZhihuApi {

    @GET("news/latest")
    Observable<NewsTimeLine> getLatestNews();
}
