package cn.droidlover.xdroidmvp.net;

/**
 * @author 马山水
 * @date 2018/3/22
 * @desc 封装Retrofit的类，单例模式
 */

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请求的配置文件类
 */
public class RetrofitConfig<T> {
    private Retrofit retrofitBook;
    //连接,读取，写入时间限制
    private static int CONNECT_TIMEOUT_MIL = 30000;
    private static int READ_TIMEOUT_MIL = 15000;
    private static int WRITE_TIMEOUT_MIL = 15000;
    private OkHttpClient okHttpClient;


    private RetrofitConfig() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("拦截器拦截到日志信息:", "Retrofit---------->" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new TokenInterceptor())
                .addInterceptor(new LoggingInterceptor())
//                .addInterceptor(loggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT_MIL, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MIL, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT_MIL, TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool())
                .build();
    }

    private static class SingletonHolder {
        private static final RetrofitConfig<Object> retrofitObject = new RetrofitConfig<>();
    }

    public static RetrofitConfig<Object> getInstance() {
        return SingletonHolder.retrofitObject;
    }


    /**
     * 获取连接OkHttp连接器
     *
     * @return
     */
    public Retrofit getRetrofit() {
        if (retrofitBook == null) {
            retrofitBook = new Retrofit.Builder()
                    .baseUrl(HttpConstant.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitBook;
    }

    /**
     * 开始一个请求任务
     *
     * @param observable 被观察对象（即一个网络请求任务）
     * @param observer   观察者（对任务执行的全方位监听、线程控制、数据处理、异常处理）
     */
    public void statrPostTask(Observable observable, HttpObserver observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void statrPostTaskFunc(Observable observable, HttpObserver observer) {
        observable.map(observer.getFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}