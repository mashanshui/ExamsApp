package com.shenhesoft.examsapp.ui.activity.user;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.shenhesoft.examsapp.R;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/6/5
 * @desc 展示文件（word、excel、pdf等)
 */
public class ShowFileActivity extends XTitleActivity {
    private TbsReaderView readerView;
    private FrameLayout layout;
    private String filePath;

    @Override
    protected void initTitle() {
        filePath = getIntent().getStringExtra("filePath");
        setBackAction();
        setTitle("查看文件");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        layout = findViewById(R.id.layout_content);
        if (!TextUtils.isEmpty(filePath)) {
            openFile(filePath);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_file;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void openFile(String filePath) {
        readerView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        });
        //通过bundle把文件传给x5,打开的事情交由x5处理
        Bundle bundle = new Bundle();
        //传递文件路径
        bundle.putString("filePath", filePath);
        //加载插件保存的路径
        bundle.putString("tempPath", Environment.getExternalStorageDirectory() + File.separator + "temp");
        //加载文件前的初始化工作,加载支持不同格式的插件
        boolean b = readerView.preOpen(getFileType(filePath), false);
        if (b) {
            readerView.openFile(bundle);
        }
        layout.addView(readerView);
    }

    /***
     * 获取文件类型
     *
     * @param path 文件路径
     * @return 文件的格式
     */
    private String getFileType(String path) {
        String str = "";

        if (TextUtils.isEmpty(path)) {
            return str;
        }
        int i = path.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = path.substring(i + 1);
        return str;
    }

    @Override
    protected void onDestroy() {
        if (readerView != null) {
            readerView.onStop();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
