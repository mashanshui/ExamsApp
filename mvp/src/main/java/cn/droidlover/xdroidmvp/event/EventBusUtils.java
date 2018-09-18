package cn.droidlover.xdroidmvp.event;


import org.greenrobot.eventbus.EventBus;

/**
 * @author 马山水
 * @date 2018/3/15
 * @desc TODO
 */

public class EventBusUtils {
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }

    public static void reomveStickyEvents(String className) {
        try {
            EventBus.getDefault().removeStickyEvent(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过code码区分事件类型
     */
    public static final class EventCode {
        /**
         * 视频的播放速度调节
         */
        public static final int PLAY_SPEED = 0;

        /**
         * 主题改变
         */
        public static final int THEME_MODE = 1;
        /**
         * 做题，下一题
         */
        public static final int NEXT_ANSWER = 2;

        /**
         * 播放视频
         */
        public static final int PLAY_VIDEO = 3;

        /**
         * 搜索题目
         */
        public static final int SEARCH_QUESTION = 4;

        /**
         * pdf链接
         */
        public static final int LESSONS_PDF_URL = 5;

        /**
         * 课件简介
         */
        public static final int LESSONS_INTRO = 6;

        /**
         * 退出视频页面
         */
        public static final int EXIT_VIDEO = 7;
    }
}
