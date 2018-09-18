package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.data.db.DownloadTaskModel;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/9
 * @desc TODO
 */
public interface DownloadDataSource {
    List<DownloadTaskModel> getAllDownloadTask();

    DownloadTaskModel addDownloadTask(String name, String path, String url);

}
