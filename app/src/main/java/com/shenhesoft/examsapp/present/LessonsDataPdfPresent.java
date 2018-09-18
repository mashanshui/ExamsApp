package com.shenhesoft.examsapp.present;

import android.content.Context;
import android.text.TextUtils;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.shenhesoft.examsapp.ui.fragment.takelessons.LessonsDataPdfFragment;
import com.shenhesoft.examsapp.util.FileUtil;

import java.io.File;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * @author mashanshui
 * @date 2018/7/2
 * @desc TODO
 */
public class LessonsDataPdfPresent extends XPresent<LessonsDataPdfFragment> {

    private Context context;

    public LessonsDataPdfPresent(Context context) {
        this.context = context;
    }


    public void loadPdfView(String url) {
        String filePath = FileUtil.getDownloadAffixPath(context) + File.separator + FileUtil.getFileName(url);
        File file = new File(filePath);
        if (file.exists()) {
            getV().showPdfView(filePath);
        } else {
            createDownloadTask(url).start();
        }
    }

    private BaseDownloadTask createDownloadTask(String url) {
        if (TextUtils.isEmpty(url)) {
            IToast.showShort("视频地址不存在");
            return null;
        }
        return FileDownloader.getImpl().create(url)
                .setPath(FileUtil.getDownloadAffixPath(context) + File.separator + FileUtil.getFileName(url))
                .setCallbackProgressTimes(400)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        getV().showDialog();
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        getV().dismissDialog();
                        getV().showPdfView(task.getTargetFilePath());
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        IToast.showShort("加载失败");
                        getV().dismissDialog();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                });
    }

}
