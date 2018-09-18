package com.shenhesoft.examsapp.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shenhesoft.examsapp.AppConstant;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author mashanshui
 * @date 2018/6/4
 * @desc 微信支付
 */
public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信
        msgApi.registerApp(AppConstant.WeChatAppId);
    }
}
