package com.shenhesoft.examsapp.data.db;

import org.litepal.crud.DataSupport;

/**
 * @author mashanshui
 * @date 2018/6/5
 * @desc TODO
 */
public class LocalVideo extends DataSupport {
    public static final int Complete = 1;
    public static final int InComplete = 2;
    private String userId;
    private int taskId;
    private String downloadUrl;
    private String fileName;
    private String filepath;
    private int isComplete;

    public int getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
