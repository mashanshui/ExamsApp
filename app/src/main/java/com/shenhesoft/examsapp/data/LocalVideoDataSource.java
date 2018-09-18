package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.data.db.LocalVideo;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/6/5
 * @desc TODO
 */
public interface LocalVideoDataSource {
    void addLocalVideo(LocalVideo localVideo);

    void deleteLocalVideoByFileName(String fileName);

    void modifyLocalVideo(LocalVideo localVideo);

    List<LocalVideo> getAllLocalVideo();

    List<LocalVideo> getLocalVideoByDownloadUrl(String downloadUrl);

    List<LocalVideo> getLocalVideoByFileName(String filePath);

    List<LocalVideo> getLocalVideoByTaskId(int taskId);

}
