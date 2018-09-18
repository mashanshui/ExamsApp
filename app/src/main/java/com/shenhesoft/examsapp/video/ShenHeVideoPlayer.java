package com.shenhesoft.examsapp.video;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.shenhesoft.examsapp.R;

import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author mashanshui
 * @date 2018/4/27
 * @desc TODO
 */
public class ShenHeVideoPlayer extends JZVideoPlayerStandard {

    public TextView videoSpeed;
    public float playSpeed = 1.0f;

    public ShenHeVideoPlayer(Context context) {
        super(context);
    }

    public ShenHeVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        videoSpeed = findViewById(R.id.video_speed);
        videoSpeed.setOnClickListener(this);
    }

    @Override
    public void showWifiDialog() {
//        super.showWifiDialog();
        //不做处理
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == cn.jzvd.R.id.fullscreen) {
            if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                //click quit fullscreen
            } else {
                //click goto fullscreen
            }
        } else if (i == cn.jzvd.R.id.video_speed) {
            String speed = videoSpeed.getText().toString().trim();
            if ("倍速".equals(speed)) {
                videoSpeed.setText("1.2X");
                playSpeed = 1.2f;
            } else if ("1.2X".equals(speed)) {
                videoSpeed.setText("1.6X");
                playSpeed = 1.6f;
            } else if ("1.6X".equals(speed)) {
                videoSpeed.setText("2.0X");
                playSpeed = 2f;
            } else if ("2.0X".equals(speed)) {
                videoSpeed.setText("倍速");
                playSpeed = 1f;
            }
            EventBusUtils.sendEvent(new Event(EventBusUtils.EventCode.PLAY_SPEED, playSpeed));
            // 更新播放状态
            onStatePreparingChangingUrl(0, getCurrentPositionWhenPlaying());
        } else if (i == cn.jzvd.R.id.back && currentScreen != SCREEN_WINDOW_FULLSCREEN) {
            EventBusUtils.sendEvent(new Event(EventBusUtils.EventCode.EXIT_VIDEO));
        } else if (i == cn.jzvd.R.id.start || i == cn.jzvd.R.id.retry_btn) {
            if (TextUtils.isEmpty((CharSequence) getCurrentUrl())) {
                IToast.showShort("请选择视频后再进行播放");
            }
        }
    }

    @Override
    public void setUp(Object[] dataSourceObjects, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(dataSourceObjects, defaultUrlMapIndex, screen, objects);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            videoSpeed.setVisibility(VISIBLE);
        } else {
            videoSpeed.setVisibility(GONE);
        }
    }

    @Override
    public int getLayoutId() {
        return cn.jzvd.R.layout.jz_layout_standard;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    @Override
    public void startVideo() {
        super.startVideo();
        //每次重新播放前都重置播放速度
        EventBusUtils.sendEvent(new Event(EventBusUtils.EventCode.PLAY_SPEED, playSpeed));
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
    }

    @Override
    public void onStatePreparing() {
        if (TextUtils.isEmpty((CharSequence) getCurrentUrl())) {
            return;
        }
        super.onStatePreparing();
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
    }

    @Override
    public void onStateError() {
        super.onStateError();
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
    }

    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
    }

    @Override
    public void onError(int what, int extra) {
        if (TextUtils.isEmpty((CharSequence) getCurrentUrl())) {
            return;
        }
        super.onError(what, extra);
    }

    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
    }

    @Override
    public void startWindowTiny() {
        super.startWindowTiny();
    }
}
