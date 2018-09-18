package com.shenhesoft.examsapp.data.local;

import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.MyApplication;
import com.shenhesoft.examsapp.data.LocalVideoDataSource;
import com.shenhesoft.examsapp.data.db.LocalVideo;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.droidlover.xdroidmvp.cache.SharedPref;

/**
 * @author mashanshui
 * @date 2018/6/5
 * @desc TODO
 */
public class LocalVideoLocalDataSource implements LocalVideoDataSource {
    @Override
    public void addLocalVideo(LocalVideo localVideo) {
        localVideo.setUserId(getUserID());
        localVideo.saveOrUpdate("taskId=?", String.valueOf(localVideo.getTaskId()));
    }

    @Override
    public void deleteLocalVideoByFileName(String fileName) {
        fileName = fileName + ".mp4";
        DataSupport.deleteAll(LocalVideo.class, "fileName=?", fileName);
    }

    @Override
    public void modifyLocalVideo(LocalVideo localVideo) {
        localVideo.saveOrUpdate("taskId=?", String.valueOf(localVideo.getTaskId()));
    }

    @Override
    public List<LocalVideo> getAllLocalVideo() {
        return DataSupport.findAll(LocalVideo.class);
    }

    @Override
    public List<LocalVideo> getLocalVideoByDownloadUrl(String downloadUrl) {
        return DataSupport.where("userId=?", getUserID())
                .where("downloadUrl=?", downloadUrl)
                .find(LocalVideo.class);
    }

    @Override
    public List<LocalVideo> getLocalVideoByFileName(String fileName) {
        fileName = fileName + ".mp4";
        return DataSupport.where("userId=?", getUserID())
                .where("fileName=?", fileName)
                .find(LocalVideo.class);
    }

    @Override
    public List<LocalVideo> getLocalVideoByTaskId(int taskId) {
        return DataSupport.where("userId=?", getUserID())
                .where("taskId=?", String.valueOf(taskId))
                .find(LocalVideo.class);
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    private String getUserID() {
        return SharedPref.getInstance(MyApplication.context).getString(AppConstant.UserId, "");
    }
}
