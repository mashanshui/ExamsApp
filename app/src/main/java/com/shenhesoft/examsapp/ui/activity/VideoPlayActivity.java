package com.shenhesoft.examsapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ViewTabPagerAdapter;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.VideoPlayPresent;
import com.shenhesoft.examsapp.ui.fragment.takelessons.LessonsCatalogFragment;
import com.shenhesoft.examsapp.ui.fragment.takelessons.LessonsDataFragment;
import com.shenhesoft.examsapp.ui.fragment.takelessons.LessonsIntroFragment;
import com.shenhesoft.examsapp.util.BackHandlerHelper;
import com.shenhesoft.examsapp.util.event.PlayVideoEvent;
import com.shenhesoft.examsapp.video.JZMediaIjkplayer;
import com.shenhesoft.examsapp.video.ShenHeVideoPlayer;
import com.shenhesoft.examsapp.view.VideoPlayView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author mashanshui
 * @date 2018/5/7
 * @desc 听课主页面
 */
public class VideoPlayActivity extends XActivity<VideoPlayPresent> implements VideoPlayView {
    private static final String TAG = "VideoPlayActivity";
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    QMUIViewPager viewPager;
    @BindView(R.id.fab_download)
    FloatingActionButton fabDownload;
    private ShenHeVideoPlayer shVideo;
    private JZMediaIjkplayer ijkplayer;

    private List<Fragment> fragmentList;
    private LessonsCatalogFragment catalogFragment;
    private LessonsDataFragment dataFragment;
    private LessonsIntroFragment introFragment;

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        EventBus.getDefault().removeStickyEvent(ProductModel.class);
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        fragmentList = new ArrayList<>();
        catalogFragment = new LessonsCatalogFragment();
        dataFragment = new LessonsDataFragment();
        introFragment = new LessonsIntroFragment();
        fragmentList.add(introFragment);
        fragmentList.add(catalogFragment);
        fragmentList.add(dataFragment);
        List<String> titleList = Arrays.asList("简介", "目录", "资料");
        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setSwipeable(false);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(viewPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        shVideo = findViewById(R.id.sh_video);
        ijkplayer = new JZMediaIjkplayer();
        JZVideoPlayer.setMediaInterface(ijkplayer);
        shVideo.setUp("", JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getLayoutId() {
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarDarkMode(context);
        return R.layout.activity_video_play;
    }

    @Override
    public VideoPlayPresent newP() {
        return new VideoPlayPresent(context);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void receiveEvent(Event event) {
        super.receiveEvent(event);
        if (event.getCode() == EventBusUtils.EventCode.PLAY_SPEED) {
            ijkplayer.setPlaySpeed((Float) event.getData());
        } else if (event.getCode() == EventBusUtils.EventCode.PLAY_VIDEO) {
            PlayVideoEvent videoEvent = (PlayVideoEvent) event.getData();
            if (TextUtils.isEmpty(videoEvent.getVideoPath())) {
                IToast.showShort("视频不存在，请下载");
                return;
            }
            shVideo.setUp(videoEvent.getVideoPath(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, videoEvent.getVideoName());
        } else if (event.getCode() == EventBusUtils.EventCode.EXIT_VIDEO) {
            onBackPressed();
        }
    }
}
