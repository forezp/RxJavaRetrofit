package forezp.com.rxjavaretrofitdemo.Presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import forezp.com.rxjavaretrofitdemo.api.ApiFactory;
import forezp.com.rxjavaretrofitdemo.api.ZhihuApi;
import forezp.com.rxjavaretrofitdemo.bean.NewsTimeLine;
import forezp.com.rxjavaretrofitdemo.view.IGetZhihuNewsView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class ZhihuPresenter {
    private Context context;
    public static final ZhihuApi zhihuApi = ApiFactory.getZhihuApiSingleton();
    public ZhihuPresenter(Context context) {
        this.context = context;
    }

    public void getNews(IGetZhihuNewsView iGetZhihuNewsView){
        zhihuApi.getLatestNews( )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsTimeLine -> {
                    disPlayZhihuList(iGetZhihuNewsView,newsTimeLine, context);
                },this::loadError);
    }

    private void disPlayZhihuList(IGetZhihuNewsView iGetZhihuNewsView,NewsTimeLine newsTimeLine, Context context) {
        Toast.makeText(context,newsTimeLine.toString(),Toast.LENGTH_SHORT).show();
        iGetZhihuNewsView.getSuccess(newsTimeLine);
        Log.e("test",newsTimeLine.toString());
    }
    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(context, "网络不见了", Toast.LENGTH_SHORT).show();
    }


}
