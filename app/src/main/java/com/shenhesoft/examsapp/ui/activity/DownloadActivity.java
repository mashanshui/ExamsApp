package com.shenhesoft.examsapp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.liulishuo.filedownloader.FileDownloader;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.DownloadListAdapter;
import com.shenhesoft.examsapp.data.db.DownloadTaskModel;
import com.shenhesoft.examsapp.present.DownloadPresent;
import com.shenhesoft.examsapp.util.TasksManager;
import com.shenhesoft.examsapp.view.DownloadView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * @author mashanshui
 * @date 2018/5/8
 * @desc TODO
 */
public class DownloadActivity extends XActivity<DownloadPresent> implements DownloadView {
    private static final String TAG = "DownloadActivity";
    @BindView(R.id.recycler_download)
    RecyclerView recyclerDownload;

    private DownloadListAdapter adapter;
    private List<DownloadTaskModel> downloadTaskModels;

    @Override
    public void initData(Bundle savedInstanceState) {
        FileDownloader.setup(this);
        downloadTaskModels = new ArrayList<>();
        adapter = new DownloadListAdapter();
        recyclerDownload.setLayoutManager(new LinearLayoutManager(context));
        recyclerDownload.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerDownload.setAdapter(adapter);

        TasksManager.getImpl().onCreate(new WeakReference<>(this));
    }

    public void postNotifyDataChanged() {
        if (adapter != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    public DownloadPresent newP() {
        return new DownloadPresent();
    }

    @Override
    protected void onDestroy() {
        TasksManager.getImpl().onDestroy();
        adapter = null;
        FileDownloader.getImpl().pauseAll();
        super.onDestroy();
    }
}
