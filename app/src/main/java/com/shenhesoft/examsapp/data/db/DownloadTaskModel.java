package com.shenhesoft.examsapp.data.db;

import org.litepal.crud.DataSupport;

/**
 * @author mashanshui
 * @date 2018/5/8
 * @desc 下载列表的实体类
 */
public class DownloadTaskModel extends DataSupport{
    private int taskId;
    private String name;
    private String url;
    private String path;

    public int getId() {
        return taskId;
    }

    public void setId(int id) {
        this.taskId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
