package com.shenhesoft.examsapp.util;

import android.text.TextUtils;
import android.util.SparseArray;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.shenhesoft.examsapp.adapter.DownloadListAdapter;
import com.shenhesoft.examsapp.data.DownloadDataSource;
import com.shenhesoft.examsapp.data.db.DownloadTaskModel;
import com.shenhesoft.examsapp.data.local.DownloadLocalDataSource;
import com.shenhesoft.examsapp.ui.activity.DownloadActivity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author mashanshui
 * @date 2018/5/9
 * @desc TODO
 */
public class TasksManager {
    private final static class HolderClass {
        private final static TasksManager INSTANCE = new TasksManager();
    }

    public static TasksManager getImpl() {
        return TasksManager.HolderClass.INSTANCE;
    }

    private DownloadDataSource dbController;
    private List<DownloadTaskModel> modelList;

    private TasksManager() {
        dbController = new DownloadLocalDataSource();
        modelList = dbController.getAllDownloadTask();
        initDemo();
    }

    private void initDemo() {
        String[] BIG_FILE_URLS = {
                // 5m
                "http://mirror.internode.on.net/pub/test/5meg.test5",
                // 6m
                "http://download.chinaunix.net/down.php?id=10608&ResourceID=5267&site=1",
                // 8m
                "http://7xjww9.com1.z0.glb.clouddn.com/Hopetoun_falls.jpg",
                // 10m
                "http://dg.101.hk/1.rar",
                // 342m
                "http://180.153.105.144/dd.myapp.com/16891/E2F3DEBB12A049ED921C6257C5E9FB11.apk",
                // 10m
                "http://mirror.internode.on.net/pub/test/10meg.test4",
                // 20m
                "http://www.pc6.com/down.asp?id=72873",
                // 22m
                "http://113.207.16.84/dd.myapp.com/16891/2E53C25B6BC55D3330AB85A1B7B57485.apk?mkey=5630b43973f537cf&f=cf87&fsname=com.htshuo.htsg_3.0.1_49.apk&asr=02f1&p=.apk",
                // 206m
                "http://down.tech.sina.com.cn/download/d_load.php?d_id=49535&down_id=1&ip=42.81.45.159"
        };
        if (modelList.size() <= 0) {
            final int demoSize = BIG_FILE_URLS.length;
            for (int i = 0; i < demoSize; i++) {
                final String url = BIG_FILE_URLS[i];
                addTask(url);
            }
        }
    }

    private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();

    public void addTaskForViewHolder(final BaseDownloadTask task) {
        taskSparseArray.put(task.getId(), task);
    }

    public void removeTaskForViewHolder(final int id) {
        taskSparseArray.remove(id);
    }

    public void updateViewHolder(final int id, final DownloadListAdapter.TaskItemViewHolder holder) {
        final BaseDownloadTask task = taskSparseArray.get(id);
        if (task == null) {
            return;
        }
        task.setTag(holder);
    }

    public void releaseTask() {
        taskSparseArray.clear();
    }

    private FileDownloadConnectListener listener;

    private void registerServiceConnectionListener(final WeakReference<DownloadActivity>
                                                           activityWeakReference) {
        if (listener != null) {
            FileDownloader.getImpl().removeServiceConnectListener(listener);
        }

        listener = new FileDownloadConnectListener() {

            @Override
            public void connected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }

                activityWeakReference.get().postNotifyDataChanged();
            }

            @Override
            public void disconnected() {
                if (activityWeakReference == null
                        || activityWeakReference.get() == null) {
                    return;
                }

                activityWeakReference.get().postNotifyDataChanged();
            }
        };

        FileDownloader.getImpl().addServiceConnectListener(listener);
    }

    private void unregisterServiceConnectionListener() {
        FileDownloader.getImpl().removeServiceConnectListener(listener);
        listener = null;
    }

    public void onCreate(final WeakReference<DownloadActivity> activityWeakReference) {
        if (!FileDownloader.getImpl().isServiceConnected()) {
            FileDownloader.getImpl().bindService();
            registerServiceConnectionListener(activityWeakReference);
        }
    }

    public void onDestroy() {
        unregisterServiceConnectionListener();
        releaseTask();
    }

    public boolean isReady() {
        return FileDownloader.getImpl().isServiceConnected();
    }

    public DownloadTaskModel get(final int position) {
        return modelList.get(position);
    }

    public DownloadTaskModel getById(final int id) {
        for (DownloadTaskModel model : modelList) {
            if (model.getId() == id) {
                return model;
            }
        }

        return null;
    }

    /**
     * @param status Download Status
     * @return has already downloaded
     * @see FileDownloadStatus
     */
    public boolean isDownloaded(final int status) {
        return status == FileDownloadStatus.completed;
    }

    public int getStatus(final int id, String path) {
        return FileDownloader.getImpl().getStatus(id, path);
    }

    public long getTotal(final int id) {
        return FileDownloader.getImpl().getTotal(id);
    }

    public long getSoFar(final int id) {
        return FileDownloader.getImpl().getSoFar(id);
    }

    public int getTaskCounts() {
        return modelList.size();
    }

    public DownloadTaskModel addTask(final String url) {
        return addTask(url, createPath(url));
    }

    public DownloadTaskModel addTask(final String url, final String path) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
            return null;
        }

        final int id = FileDownloadUtils.generateId(url, path);
        DownloadTaskModel model = getById(id);
        if (model != null) {
            return model;
        }
        final DownloadTaskModel newModel = dbController.addDownloadTask("name",path, url);
        if (newModel != null) {
            modelList.add(newModel);
        }

        return newModel;
    }

    public String createPath(final String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        return FileDownloadUtils.getDefaultSaveFilePath(url);
    }
}
