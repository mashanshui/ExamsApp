package com.shenhesoft.examsapp.present;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.notification.BaseNotificationItem;
import com.liulishuo.filedownloader.notification.FileDownloadNotificationHelper;
import com.liulishuo.filedownloader.notification.FileDownloadNotificationListener;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.shenhesoft.examsapp.MyApplication;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.InteractiveListAdapter;
import com.shenhesoft.examsapp.data.InteractiveDataSource;
import com.shenhesoft.examsapp.data.remote.InteractiveRemoteDataSource;
import com.shenhesoft.examsapp.network.model.InteractiveModel;
import com.shenhesoft.examsapp.ui.MainActivity;
import com.shenhesoft.examsapp.view.InteractiveView;

import java.io.File;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author mashanshui
 * @date 2018/5/30
 * @desc TODO
 */
public class InteractivePresent extends XPresent<InteractiveView> {
    private InteractiveDataSource interactiveDataSource;

    public InteractivePresent() {
        interactiveDataSource = new InteractiveRemoteDataSource();
    }

    public void loadData(BGARefreshLayout bgaRefreshLayout, String articleId) {
        interactiveDataSource.loadData(bgaRefreshLayout,articleId, new InteractiveDataSource.LoadDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<InteractiveModel> allResults) {
                List<InteractiveModel> interactiveModels = allResults.getRows();
                dealData(interactiveModels);
                getV().updateData(interactiveModels);
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    private void dealData(List<InteractiveModel> rows) {
        for (int i = 0; i < rows.size(); i++) {
            InteractiveModel model = rows.get(i);
            if (i % 2 != 0) {
                model.setInteractiveType(InteractiveListAdapter.TEACHER);
            }
        }
    }

    public void uploadWriting(String filePath, final String id) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("articleAttach", file.getName(), requestFile);
        interactiveDataSource.uploadWriting(body, id, file.getName(), new InteractiveDataSource.UploadCallBack() {
            @Override
            public void onSuccess() {
                IToast.showShort("上传成功");
                getV().beginRefresh();
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    public void downloadWriting(String url, String path) {
        FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new NotificationListener(new FileDownloadNotificationHelper<NotificationItem>()))
                .start();
    }

    private class NotificationListener extends FileDownloadNotificationListener {

        private static final String TAG = "NotificationListener";

        public NotificationListener(FileDownloadNotificationHelper helper) {
            super(helper);
        }

        @Override
        protected BaseNotificationItem create(BaseDownloadTask task) {
            getV().setBtnDownloadText("下载中");
            return new NotificationItem(task.getId(), "下载中", "附件下载");
        }

        @Override
        public void destroyNotification(BaseDownloadTask task) {
            super.destroyNotification(task);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            getV().setBtnDownloadText("查看附件");
            IToast.showShort("下载完成");
            getV().setDownloadId(0);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            IToast.showShort("下载失败");
            getV().setDownloadId(0);
        }
    }

    public static class NotificationItem extends BaseNotificationItem {

        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        private NotificationItem(int id, String title, String desc) {
            super(id, title, desc);
            Intent[] intents = new Intent[2];
            intents[0] = Intent.makeMainActivity(new ComponentName(MyApplication.context, MainActivity.class));
            intents[1] = new Intent();

            this.pendingIntent = PendingIntent.getActivities(MyApplication.context, 0, intents,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new NotificationCompat.Builder(FileDownloadHelper.getAppContext());

            builder.setDefaults(Notification.DEFAULT_LIGHTS)
                    .setOngoing(true)
                    .setPriority(NotificationCompat.PRIORITY_MIN)
                    .setContentTitle(getTitle())
                    .setContentText(desc)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.logo);
        }

        @Override
        public void show(boolean statusChanged, int status, boolean isShowProgress) {

            String desc = getDesc();
            switch (status) {
                case FileDownloadStatus.pending:
                    desc += " pending";
                    break;
                case FileDownloadStatus.started:
                    desc += " started";
                    break;
                case FileDownloadStatus.progress:
                    desc += " progress";
                    break;
                case FileDownloadStatus.retry:
                    desc += " retry";
                    break;
                case FileDownloadStatus.error:
                    desc += " error";
                    break;
                case FileDownloadStatus.paused:
                    desc += " paused";
                    break;
                case FileDownloadStatus.completed:
                    desc += " completed";
                    break;
                case FileDownloadStatus.warn:
                    desc += " warn";
                    break;
                default:
                    break;
            }

            builder.setContentTitle(getTitle()).setContentText(desc);
            if (statusChanged) {
                builder.setTicker(desc);
            }
            builder.setProgress(getTotal(), getSofar(), !isShowProgress);
            getManager().notify(getId(), builder.build());
        }

    }
}
