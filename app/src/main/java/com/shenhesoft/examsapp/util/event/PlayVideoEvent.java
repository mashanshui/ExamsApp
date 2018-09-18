package com.shenhesoft.examsapp.util.event;

/**
 * @author mashanshui
 * @date 2018/6/9
 * @desc TODO
 */
public class PlayVideoEvent {
    private String videoPath;
    private String videoName;

    public PlayVideoEvent(String videoPath, String videoName) {
        this.videoPath = videoPath;
        this.videoName = videoName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
