package cn.droidlover.xdroidmvp.net;

import java.io.IOException;

import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author mashanshui
 * @date 2018/4/18
 * @desc TODO
 */
public class TokenInterceptor implements Interceptor {
    private static final String TAG = "TokenInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = SharedPref.getInstance(ActivityCollector.getTopActivity()).getString("jsessionid", "");
        Request originalRequest = chain.request();
        // get new request, add request header
        Request updateRequest = originalRequest.newBuilder()
                .header("jsessionid", token)
                .build();
//        Log.e(TAG, "intercept: -------------------------" +token);
        return chain.proceed(updateRequest);
    }
}
