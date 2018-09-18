package com.shenhesoft.examsapp.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.umeng.message.UmengMessageService;

import org.android.agoo.common.AgooConstants;

public class UmengNotificationService extends UmengMessageService {
    private static final String TAG = "UmengNotificationServic";
    @Override
    public void onMessage(Context context, Intent intent) {
        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.e(TAG, "onMessage: "+message );
//        Intent intent1 = new Intent();
//        intent1.setClass(context, MyNotificationService.class);
//        intent1.putExtra("UmengMsg", message);
//        context.startService(intent1);
    }
}
