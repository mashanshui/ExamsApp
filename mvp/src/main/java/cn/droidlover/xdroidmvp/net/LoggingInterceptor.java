package cn.droidlover.xdroidmvp.net;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import java.io.IOException;

import cn.droidlover.xdroidmvp.R;
import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @author mashanshui
 * @date 2018/5/22
 * @desc 拦截器，打印请求值和返回值
 */
public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "LoggingInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间
        Log.i("request", String.format("发送请求 %s on %s%n%s ",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

//        RequestBody requestBody = request.body();
//        Buffer buffer = new Buffer();
//        requestBody.writeTo(buffer);
//        String oldParamsJson = buffer.readUtf8();
//        Log.e("info", "intercept: " + oldParamsJson);

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        String responseString = responseBody.string();
//        Log.i("logging", String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
//                response.request().url(),
//                responseBody.string(),
//                (t2 - t1) / 1e6d,
//                response.headers()));
//        Gson gson = new Gson();
//        Type type = new TypeToken<Map<String, Object>>() {}.getType();
//        Map<String, Object> map = gson.fromJson(responseBody.string(), type);
//        int status = (int) map.get("status");
//        if (status==0) {
//        }
        String result = responseString.substring(0, 30);
        if (result.contains("<!DOCTYPE html>")) {
            SharedPref.getInstance(ActivityCollector.getTopActivity()).putNowString("userpassword", "");
            showConfirmDialog();
        }
        XLog.d2("response", "JSON：" + responseString + "响应时间：" + (t2 - t1) / 1e6d);
        return response;
    }

    private void showConfirmDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.logout_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "登录失效");
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Intent intent =ActivityCollector.getTopActivity().getPackageManager().getLaunchIntentForPackage(ActivityCollector.getTopActivity().getPackageName());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                ActivityCollector.getTopActivity().startActivity(intent);
                                ActivityCollector.finishAllActivity();
                                baseNiceDialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(300)
                .setHeight(150)
                .setOutCancel(false)
                .show(((AppCompatActivity) (ActivityCollector.getTopActivity())).getSupportFragmentManager());
    }
}
