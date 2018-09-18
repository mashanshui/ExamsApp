package com.shenhesoft.examsapp;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.shenhesoft.examsapp.ui.MainActivity;
import com.shenhesoft.examsapp.util.CustomNotificationHandler;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import me.jessyan.autosize.AutoSizeConfig;

/**
 * @author mashanshui
 * @date 2018/5/7
 * @desc TODO
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Utils.init(context);
        LitePal.initialize(context);
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());

//        if (processName == null && processName.equals(packageName)) {
//            //主进程
//        }
        initBugly(packageName, processName);
        initPush();
        initDownload();
        initX5browser();
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    private void initBugly(String packageName, String processName) {
        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        CrashReport.initCrashReport(getApplicationContext(), "470cf8bfa1", false);
        Beta.largeIconId = R.mipmap.logo;
        Beta.smallIconId = R.mipmap.logo;
        Beta.canShowUpgradeActs.add(MainActivity.class);
        Beta.enableNotification = true;
        Beta.canShowApkInfo = false;
        Beta.enableHotfix = false;
        BuglyStrategy buglyStrategy = new BuglyStrategy();
        buglyStrategy.setUploadProcess(processName == null || processName.equals(packageName));
        Bugly.init(getApplicationContext(), "470cf8bfa1", true, buglyStrategy);
    }

    {
        PlatformConfig.setWeixin(AppConstant.WeChatAppId, AppConstant.WeChatAppSecret);
    }


    private void initX5browser() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initDownload() {
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000)
                        .readTimeout(15_000)
                ))
                .commit();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initPush() {
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, PushConstant.UMENG_SECRET);
        MiPushRegistar.register(context, PushConstant.XIAOMI_ID, PushConstant.XIAOMI_KEY);
        MeizuRegister.register(context, PushConstant.MEIZU_ID, PushConstant.MEIZU_KEY);
        HuaWeiRegister.register(context);
        UMConfigure.setLogEnabled(false);
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e(TAG, "onSuccess: " + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "onFailure: " + s + " " + s1);
            }
        });
        CustomNotificationHandler notificationHandler = new CustomNotificationHandler(context);
        mPushAgent.setNotificationClickHandler(notificationHandler);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
