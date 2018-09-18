package com.shenhesoft.examsapp.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.data.LocalVideoDataSource;
import com.shenhesoft.examsapp.data.db.LocalVideo;
import com.shenhesoft.examsapp.data.local.LocalVideoLocalDataSource;
import com.shenhesoft.examsapp.network.LessonRetrofit;
import com.shenhesoft.examsapp.network.LessonService;
import com.shenhesoft.examsapp.network.model.LessonsModel;
import com.shenhesoft.examsapp.network.model.LessonsSection;
import com.shenhesoft.examsapp.network.model.LessonsVideo;
import com.shenhesoft.examsapp.util.FileUtil;
import com.shenhesoft.examsapp.util.event.PlayVideoEvent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.net.HttpConstant;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.NetworkUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author mashanshui
 * @date 2018/5/16
 * @desc TODO
 */
public class LessonsListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static boolean WIFI_TIP_DIALOG_SHOW = true;
    public static final int LESSONS_LIST_1 = 0;
    public static final int LESSONS_LIST_2 = 1;
    public static final int LESSONS_LIST_3 = 2;
    private FragmentManager fragmentManager;
    private LessonService lessonService;
    private LocalVideoDataSource localVideoDataSource;
    private static final int PREPARE = 0;
    private static final int START = 1;
    private static final int PAUSE = 2;
    private static final int COMPLETE = 3;
    private static final int ERROR = 4;
    private SparseIntArray downloadTaskIdList;
    private TextView lastSelectView;
    private SparseArray<ViewHolder> taskSparseArray;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public LessonsListAdapter(List<MultiItemEntity> data, FragmentManager fragmentManager) {
        super(data);
        this.fragmentManager = fragmentManager;
        downloadTaskIdList = new SparseIntArray();
        taskSparseArray = new SparseArray<>();
        lessonService = HttpRequestUtil.getRetrofitClient(LessonService.class.getName());
        localVideoDataSource = new LocalVideoLocalDataSource();
        addItemType(LESSONS_LIST_1, R.layout.recycler_list_lessons_item1);
        addItemType(LESSONS_LIST_2, R.layout.recycler_list_lessons_item2);
        addItemType(LESSONS_LIST_3, R.layout.recycler_list_lessons_item3);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        //禁止recyclerview复用，在显示下载进度时由于view的复用问题会让没有下载的列表也显示进度，暂时的方法是取消复用机制
        //当然这会损失一部分的性能，但没有找到更好的解决方法之前只能这样
        helper.setIsRecyclable(false);
        switch (helper.getItemViewType()) {
            case LESSONS_LIST_1:
                final LessonsModel listBean1 = (LessonsModel) item;
                helper.setText(R.id.tv_message, listBean1.getProductTitle())
                        .setImageResource(R.id.iv_indicate_image, listBean1.isExpanded() ? R.drawable.more_unfold_dark : R.drawable.more_dark);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBusUtils.sendEvent(new Event(EventBusUtils.EventCode.LESSONS_INTRO, listBean1.getId()));
                        int pos = helper.getAdapterPosition();
                        if (listBean1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case LESSONS_LIST_2:
                final LessonsSection listBean2 = (LessonsSection) item;
                helper.setText(R.id.tv_message, listBean2.getCourseName())
                        .setImageResource(R.id.iv_indicate_image, listBean2.isExpanded() ? R.drawable.more_unfold_light : R.drawable.more_light);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (listBean2.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case LESSONS_LIST_3:
                final LessonsVideo listBean3 = (LessonsVideo) item;
                helper.setText(R.id.tv_message, listBean3.getVideoName())
                        .addOnClickListener(R.id.download_button);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isDownloadComplete(listBean3.getVideoId())) {
                            IToast.showShort("请先下载视频");
                            return;
                        }
                        if (lastSelectView != null && lastSelectView == helper.getView(R.id.tv_message)) {
                            return;
                        }
                        if (lastSelectView != null && lastSelectView != helper.getView(R.id.tv_message)) {
                            lastSelectView.setTextColor(Color.BLACK);
                        }
                        helper.setTextColor(R.id.tv_message, mContext.getResources().getColor(R.color.colorPrimary));
                        lastSelectView = helper.getView(R.id.tv_message);
                        List<LocalVideo> localVideoList = localVideoDataSource.getLocalVideoByFileName(listBean3.getVideoId());
                        //上传视频播放信息，用于统计
                        uploadMessage(listBean3.getVideoId());
                        EventBusUtils.sendEvent(new Event(EventBusUtils.EventCode.PLAY_VIDEO, new PlayVideoEvent(localVideoList.get(0).getFilepath(), listBean3.getCourseName())));
                    }
                });
                final NumberProgressBar numberProgressBar = helper.getView(R.id.number_progress_bar);
                numberProgressBar.setMax(100);
                final ImageView btnDownlaod = helper.getView(R.id.download_button);
                final ViewHolder tag = new ViewHolder(btnDownlaod, numberProgressBar);
                byte b = 0;
                if (isExitDownloadTask(listBean3.getVideoId())) {
                    List<LocalVideo> localVideoList = localVideoDataSource.getLocalVideoByFileName(listBean3.getVideoId());
                    LocalVideo localVideo = localVideoList.get(0);
                    b = FileDownloader.getImpl().getStatus(localVideo.getTaskId(), localVideo.getFilepath());
                    if (b == FileDownloadStatus.progress) {
                        tag.setTaskId(localVideo.getTaskId());
                        addTaskForArray(localVideo.getTaskId(), tag);
                    }
                }
                refreshFileStatus(listBean3, btnDownlaod, b);

                btnDownlaod.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RxPermissions rxPermissions = new RxPermissions(ActivityCollector.getTopActivity());
                        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) {
                                        if (aBoolean) {
                                            switch ((int) btnDownlaod.getTag()) {
                                                case PREPARE:
                                                    if (NetworkUtil.getNetworkState(mContext) != NetworkUtil.INTERNET_WIFI && WIFI_TIP_DIALOG_SHOW) {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                                        builder.setMessage("您当前正在使用移动网络，继续下载将消耗流量");
                                                        builder.setPositiveButton("继续下载", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                                numberProgressBar.setVisibility(View.VISIBLE);
                                                                getDownloadUrl(helper.getAdapterPosition(), listBean3.getVideoId(), tag);
                                                                WIFI_TIP_DIALOG_SHOW = false;
                                                            }
                                                        });
                                                        builder.setNegativeButton("停止下载", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                            @Override
                                                            public void onCancel(DialogInterface dialog) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                        builder.create().show();
                                                    } else {
                                                        numberProgressBar.setVisibility(View.VISIBLE);
                                                        getDownloadUrl(helper.getAdapterPosition(), listBean3.getVideoId(), tag);
                                                    }
                                                    break;
                                                case START:
                                                    if (downloadTaskIdList.get(helper.getAdapterPosition()) != 0) {
                                                        FileDownloader.getImpl().pause(downloadTaskIdList.get(helper.getAdapterPosition()));
                                                    }
                                                    break;
                                                case PAUSE:
                                                    getDownloadUrl(helper.getAdapterPosition(), listBean3.getVideoId(), tag);
                                                    break;
                                                case COMPLETE:
                                                    showConfirmDialog(listBean3, btnDownlaod);
                                                    break;
                                                case ERROR:
                                                    refreshFileStatus(listBean3, btnDownlaod, (byte) 0);
                                                    break;
                                                default:
                                                    break;
                                            }
                                        } else {
                                            showKonwnDialog();
                                        }
                                    }
                                });
                    }
                });
                break;
            default:
                break;
        }
    }

    private void addTaskForArray(int taskId, ViewHolder tag) {
        taskSparseArray.put(taskId, tag);
    }

    private void updateDownloadStatus(String videoId) {

    }

    private void refreshFileStatus(LessonsVideo listBean3, ImageView btnDownlaod, byte status) {
        boolean b = isDownloadComplete(listBean3.getVideoId());
        if (b) {
            btnDownlaod.setTag(COMPLETE);
            btnDownlaod.setImageResource(R.drawable.delete_download);
        } else {
            btnDownlaod.setTag(PREPARE);
            btnDownlaod.setImageResource(R.drawable.start_download);
        }
        if (status == FileDownloadStatus.progress) {
            btnDownlaod.setTag(START);
            btnDownlaod.setImageResource(R.drawable.pause_download);
        }
    }

    private boolean isDownloadComplete(String videoId) {
        List<LocalVideo> localVideoList = localVideoDataSource.getLocalVideoByFileName(videoId);
        return !localVideoList.isEmpty() && localVideoList.get(0).getIsComplete() == LocalVideo.Complete;
    }

    private boolean isExitDownloadTask(String videoId) {
        List<LocalVideo> localVideoList = localVideoDataSource.getLocalVideoByFileName(videoId);
        return !localVideoList.isEmpty() && localVideoList.get(0).getIsComplete() == LocalVideo.InComplete;
    }


    private void getDownloadUrl(final int adapterPosition, final String videoId, final ViewHolder tag) {
        if (TextUtils.isEmpty(videoId)) {
            IToast.showShort("视频不存在");
            return;
        }
        Observable<RequestResults> observable = lessonService.getUrlById(videoId);
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                BaseDownloadTask baseDownloadTask = createDownloadTask((String) data.getObj(), videoId);
                int taskId = baseDownloadTask.start();
                tag.setTaskId(taskId);
                addTaskForArray(taskId, tag);
                downloadTaskIdList.put(adapterPosition, taskId);
                if (baseDownloadTask.isReusedOldFile()) {
                    IToast.showShort("已经下载完成");
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    private BaseDownloadTask createDownloadTask(String url, String videoId) {
        if (TextUtils.isEmpty(url)) {
            IToast.showShort("视频地址不存在");
            return null;
        }
        String downloadUrl = HttpConstant.BASE_IP + url;
        return FileDownloader.getImpl().create(downloadUrl)
                .setPath(FileUtil.getDownloadVideoPath(mContext) + File.separator + FileUtil.getFileName(downloadUrl))
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadLargeFileListener() {

                    private ViewHolder checkCurrentHolder(final BaseDownloadTask task) {
                        if (taskSparseArray.get(task.getId()) == null || taskSparseArray.get(task.getId()).getTaskId() != task.getId()) {
                            return null;
                        }
                        return taskSparseArray.get(task.getId());
                    }

                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        ViewHolder tag = checkCurrentHolder(task);
                        if (tag == null) {
                            return;
                        }
                        tag.btnDownload.setTag(PREPARE);
                        LocalVideo localVideo = new LocalVideo();
                        localVideo.setTaskId(task.getId());
                        localVideo.setDownloadUrl(task.getUrl());
                        localVideo.setFilepath(task.getTargetFilePath());
                        localVideo.setFileName(task.getFilename());
                        localVideo.setIsComplete(LocalVideo.InComplete);
                        localVideoDataSource.addLocalVideo(localVideo);
                        tag.pending(task);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        ViewHolder tag = checkCurrentHolder(task);
                        if (tag == null) {
                            return;
                        }
                        tag.btnDownload.setTag(START);
                        tag.updateProgress(soFarBytes, totalBytes, task.getSpeed());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        ViewHolder tag = checkCurrentHolder(task);
                        if (tag == null) {
                            return;
                        }
                        tag.btnDownload.setTag(PAUSE);
                        tag.paused(task);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        ViewHolder tag = checkCurrentHolder(task);
                        if (tag == null) {
                            return;
                        }
                        tag.btnDownload.setTag(COMPLETE);
                        //下载完成后保存信息到数据库
                        LocalVideo localVideo = new LocalVideo();
                        localVideo.setTaskId(task.getId());
                        localVideo.setDownloadUrl(task.getUrl());
                        localVideo.setFilepath(task.getTargetFilePath());
                        localVideo.setFileName(task.getFilename());
                        localVideo.setIsComplete(LocalVideo.Complete);
                        localVideoDataSource.modifyLocalVideo(localVideo);
                        tag.completed(task);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        ViewHolder tag = checkCurrentHolder(task);
                        if (tag == null) {
                            return;
                        }
                        tag.btnDownload.setTag(ERROR);
                        tag.error(task);
                        IToast.showShort("下载失败");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                });
    }


    private void uploadMessage(String videoId) {
        Observable<RequestResults> observable = lessonService.videoStatics(LessonRetrofit.getInstance().videoStatics(videoId));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(false, new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    private static class ViewHolder {
        private ImageView btnDownload;
        private NumberProgressBar numberProgressBar;
        private int taskId;

        public ViewHolder(ImageView btnProgress, NumberProgressBar numberProgressBar) {
            this.btnDownload = btnProgress;
            this.numberProgressBar = numberProgressBar;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        private void updateSpeed(float percent) {
            numberProgressBar.setVisibility(View.VISIBLE);
            numberProgressBar.setProgress((int) (percent * 100));
        }

        public void pending(BaseDownloadTask task) {
            btnDownload.setImageResource(R.drawable.pause_download);
        }

        private void updateProgress(final long sofar, final long total, final int speed) {
            float percent = sofar / (float) total;
            updateSpeed(percent);
        }

        public void paused(BaseDownloadTask task) {
            btnDownload.setImageResource(R.drawable.start_download);
        }

        private void completed(BaseDownloadTask task) {
            updateSpeed(1);
            btnDownload.setImageResource(R.drawable.delete_download);
            numberProgressBar.setVisibility(View.GONE);
        }

        public void error(BaseDownloadTask task) {
            btnDownload.setImageResource(R.drawable.start_download);
        }
    }

    private void showConfirmDialog(final LessonsVideo listBean3, final ImageView btnDownlaod) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_confirm_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(com.othershe.nicedialog.ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "删除视频");
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<LocalVideo> localVideoList = localVideoDataSource.getLocalVideoByFileName(listBean3.getVideoId());
                                FileUtil.deleteFile(localVideoList.get(0).getFilepath());
                                localVideoDataSource.deleteLocalVideoByFileName(listBean3.getVideoId());
                                refreshFileStatus(listBean3, btnDownlaod, (byte) 0);
                                baseNiceDialog.dismiss();
                            }
                        });
                        viewHolder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(300)
                .setHeight(150)
                .show(fragmentManager);
    }

    private void showKonwnDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_single_confirm)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(com.othershe.nicedialog.ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "没有权限无法下载");
                        viewHolder.setText(R.id.tv_confirm, "我知道了");
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(300)
                .setHeight(150)
                .show(fragmentManager);
    }
}
