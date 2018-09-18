package com.shenhesoft.examsapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.shenhesoft.examsapp.MyApplication;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.data.db.DownloadTaskModel;
import com.shenhesoft.examsapp.util.TasksManager;

import java.io.File;

/**
 * @author mashanshui
 * @date 2018/5/8
 * @desc 展示正在下载的列表的适配器
 */
public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.TaskItemViewHolder> {
    private FileDownloadListener taskDownloadListener = new FileDownloadSampleListener() {

        private TaskItemViewHolder checkCurrentHolder(final BaseDownloadTask task) {
            final TaskItemViewHolder tag = (TaskItemViewHolder) task.getTag();
            if (tag.id != task.getId()) {
                return null;
            }
            return tag;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.pending(task, soFarBytes, totalBytes);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloading(FileDownloadStatus.pending, soFarBytes
                    , totalBytes);
            tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
        }

        @Override
        protected void started(BaseDownloadTask task) {
            super.started(task);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloading(FileDownloadStatus.connected, soFarBytes
                    , totalBytes);
            tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloading(FileDownloadStatus.progress, soFarBytes
                    , totalBytes);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            super.error(task, e);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
                    , task.getLargeFileTotalBytes());
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.paused(task, soFarBytes, totalBytes);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
            tag.taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            super.completed(task);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloaded();
            TasksManager.getImpl().removeTaskForViewHolder(task.getId());
        }
    };
    private View.OnClickListener taskActionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() == null) {
                return;
            }

            TaskItemViewHolder holder = (TaskItemViewHolder) v.getTag();

            CharSequence action = ((TextView) v).getText();
            if (action.equals(v.getResources().getString(R.string.pause))) {
                // to pause
                FileDownloader.getImpl().pause(holder.id);
            } else if (action.equals(v.getResources().getString(R.string.start))) {
                // to start
                // to start
                final DownloadTaskModel model = TasksManager.getImpl().get(holder.position);
                final BaseDownloadTask task = FileDownloader.getImpl().create(model.getUrl())
                        .setPath(model.getPath())
                        .setCallbackProgressTimes(100)
                        .setListener(taskDownloadListener);

                TasksManager.getImpl()
                        .addTaskForViewHolder(task);

                TasksManager.getImpl()
                        .updateViewHolder(holder.id, holder);

                task.start();
            } else if (action.equals(v.getResources().getString(R.string.delete))) {
                // to delete
                new File(TasksManager.getImpl().get(holder.position).getPath()).delete();
                holder.taskActionBtn.setEnabled(true);
                holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
            }
        }
    };

    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TaskItemViewHolder holder = new TaskItemViewHolder(
                LayoutInflater.from(
                        parent.getContext())
                        .inflate(R.layout.recycler_list_download, parent, false));

        holder.taskActionBtn.setOnClickListener(taskActionOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(TaskItemViewHolder holder, int position) {
        final DownloadTaskModel model = TasksManager.getImpl().get(position);

        holder.update(model.getId(), position);
        holder.taskActionBtn.setTag(holder);
        holder.taskNameTv.setText(model.getName());

        TasksManager.getImpl()
                .updateViewHolder(holder.id, holder);

        holder.taskActionBtn.setEnabled(true);


        if (TasksManager.getImpl().isReady()) {
            final int status = TasksManager.getImpl().getStatus(model.getId(), model.getPath());
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else if (!new File(model.getPath()).exists() &&
                    !new File(FileDownloadUtils.getTempPath(model.getPath())).exists()) {
                // not exist file
                holder.updateNotDownloaded(status, 0, 0);
            } else if (TasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                holder.updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                holder.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else {
                // not start
                holder.updateNotDownloaded(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            }
        } else {
            holder.taskStatusTv.setText(R.string.tasks_manager_demo_status_loading);
            holder.taskActionBtn.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return TasksManager.getImpl().getTaskCounts();
    }

    public static class TaskItemViewHolder extends RecyclerView.ViewHolder {
        public TaskItemViewHolder(View itemView) {
            super(itemView);
            assignViews();
        }

        private View findViewById(final int id) {
            return itemView.findViewById(id);
        }

        /**
         * viewHolder position
         */
        private int position;
        /**
         * download id
         */
        private int id;

        public void update(final int id, final int position) {
            this.id = id;
            this.position = position;
        }

        public void updateDownloaded() {
            taskPb.setMax(1);
            taskPb.setProgress(1);

            taskStatusTv.setText(R.string.tasks_manager_demo_status_completed);
            taskActionBtn.setText(R.string.delete);
        }

        public void updateNotDownloaded(final int status, final long sofar, final long total) {
            if (sofar > 0 && total > 0) {
                final float percent = sofar
                        / (float) total;
                taskPb.setMax(100);
                taskPb.setProgress((int) (percent * 100));
            } else {
                taskPb.setMax(1);
                taskPb.setProgress(0);
            }

            switch (status) {
                case FileDownloadStatus.error:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_error);
                    break;
                case FileDownloadStatus.paused:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_paused);
                    break;
                default:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_not_downloaded);
                    break;
            }
            taskActionBtn.setText(R.string.start);
        }

        public void updateDownloading(final int status, final long sofar, final long total) {
            final float percent = sofar
                    / (float) total;
            taskPb.setMax(100);
            taskPb.setProgress((int) (percent * 100));

            switch (status) {
                case FileDownloadStatus.pending:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_pending);
                    break;
                case FileDownloadStatus.started:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_started);
                    break;
                case FileDownloadStatus.connected:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_connected);
                    break;
                case FileDownloadStatus.progress:
                    taskStatusTv.setText(R.string.tasks_manager_demo_status_progress);
                    break;
                default:
                    taskStatusTv.setText(MyApplication.context.getString(
                            R.string.tasks_manager_demo_status_downloading, status));
                    break;
            }

            taskActionBtn.setText(R.string.pause);
        }

        private TextView taskNameTv;
        private TextView taskStatusTv;
        private ProgressBar taskPb;
        private Button taskActionBtn;

        private void assignViews() {
            taskNameTv = (TextView) findViewById(R.id.tv_task_name);
            taskStatusTv = (TextView) findViewById(R.id.tv_task_status);
            taskPb = (ProgressBar) findViewById(R.id.pb_task_progress);
            taskActionBtn = (Button) findViewById(R.id.bt_task_operate);
        }

    }
}

