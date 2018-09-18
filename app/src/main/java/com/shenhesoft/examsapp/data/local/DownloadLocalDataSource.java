package com.shenhesoft.examsapp.data.local;

import android.text.TextUtils;

import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.shenhesoft.examsapp.data.DownloadDataSource;
import com.shenhesoft.examsapp.data.db.DownloadTaskModel;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/9
 * @desc TODO
 */
public class DownloadLocalDataSource implements DownloadDataSource {

    @Override
    public List<DownloadTaskModel> getAllDownloadTask() {
        return DataSupport.findAll(DownloadTaskModel.class);
    }

    @Override
    public DownloadTaskModel addDownloadTask(String name, String path, String url) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }
        // have to use FileDownloadUtils.generateId to associate TasksManagerModel with FileDownloader
        final int id = FileDownloadUtils.generateId(url, path);
        DownloadTaskModel downloadTaskModel = new DownloadTaskModel();
        downloadTaskModel.setId(id);
        downloadTaskModel.setName(name);
        downloadTaskModel.setPath(path);
        downloadTaskModel.setUrl(url);
        downloadTaskModel.save();
        return downloadTaskModel;
    }

}
