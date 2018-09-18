package com.shenhesoft.examsapp.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.shenhesoft.examsapp.PushConstant;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.ui.activity.PushActivity;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.InteractiveActivity;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;

import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.Kits;

//使用自定义的NotificationHandler，来结合友盟统计处理消息通知
//参考http://bbs.umeng.com/thread-11112-1-1.html
public class CustomNotificationHandler extends UmengNotificationClickHandler {

    private Context context;
    private boolean isForegroundApp;

    public CustomNotificationHandler(Context context) {
        this.context = context;
    }

    private static final String TAG = "NotificationHandler";

    @Override
    public void dismissNotification(Context context, UMessage msg) {
        Log.e(TAG, "dismissNotification: ");
        super.dismissNotification(context, msg);
    }

    @Override
    public void launchApp(Context context, UMessage msg) {
        Log.e(TAG, "launchApp: " + msg.custom + msg.extra.get("data"));
        isForegroundApp = Kits.Package.isTopActivity(context, "com.shenhesoft.examsapp");
        super.launchApp(context, msg);
        if (isForegroundApp || ActivityCollector.getActivitySize() > 0) {
            goActivity(msg.custom, msg.extra.get("data"));
        } else {
            setPushData(msg.custom, msg.extra.get("data"));
        }
    }

    @Override
    public void openActivity(Context context, UMessage msg) {
        Log.e(TAG, "openActivity: " + msg.custom + msg.extra.get("data"));
        super.openActivity(context, msg);
    }

    @Override
    public void openUrl(Context context, UMessage msg) {
        Log.e(TAG, "openUrl: ");
        super.openUrl(context, msg);
    }

    @Override
    public void dealWithCustomAction(Context context, UMessage msg) {
        Log.e(TAG, "dealWithCustomAction: " + msg.custom + msg.extra.get("data"));
        super.dealWithCustomAction(context, msg);
    }

    @Override
    public void autoUpdate(Context context, UMessage msg) {
        Log.e(TAG, "autoUpdate: ");
        super.autoUpdate(context, msg);
    }

    private void setPushData(String pushStatus, String pushData) {
        SharedPref.getInstance(context).putString(PushConstant.PushStatus, pushStatus);
        SharedPref.getInstance(context).putString(PushConstant.PushData, pushData);
    }

    private void goActivity(String custom, String data) {
        switch (custom) {
            case "1":
                break;
            case "2":
                ProductModel productModel = new ProductModel();
                productModel.setOrderId(data);
                EventBus.getDefault().postSticky(productModel);
                Intent intent = new Intent(context, InteractiveActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case "3":
                ProductModel productModel2 = new ProductModel();
                productModel2.setOrderId(data);
                EventBus.getDefault().postSticky(productModel2);
                Intent intent2 = new Intent(context, InteractiveActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
                break;
            case "4":
                Intent intent3 = new Intent(context, PushActivity.class);
                intent3.putExtra("data", data);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent3);
                break;
            default:
                break;
        }
    }

}
