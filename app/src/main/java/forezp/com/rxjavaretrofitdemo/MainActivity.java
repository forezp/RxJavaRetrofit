package forezp.com.rxjavaretrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import forezp.com.rxjavaretrofitdemo.Presenter.ZhihuPresenter;
import forezp.com.rxjavaretrofitdemo.bean.NewsTimeLine;
import forezp.com.rxjavaretrofitdemo.view.IGetZhihuNewsView;

public class MainActivity extends AppCompatActivity implements IGetZhihuNewsView {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv) ;
        ZhihuPresenter zhihuPresenter=new ZhihuPresenter(this);
        zhihuPresenter.getNews(this);

    }

    @Override
    public void getSuccess(NewsTimeLine newsTimeLine) {
        tv.setText(newsTimeLine.toString());
    }
}
