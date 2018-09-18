package com.shenhesoft.examsapp.present;

import android.content.Context;

import com.shenhesoft.examsapp.ui.activity.VideoPlayActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * @author mashanshui
 * @date 2018/5/7
 * @desc TODO
 */
public class VideoPlayPresent extends XPresent<VideoPlayActivity> {

    private Context context;

    public VideoPlayPresent(Context context) {
        this.context = context;
    }
}
