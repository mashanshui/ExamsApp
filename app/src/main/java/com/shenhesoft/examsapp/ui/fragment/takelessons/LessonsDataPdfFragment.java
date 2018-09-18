package com.shenhesoft.examsapp.ui.fragment.takelessons;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.present.LessonsDataPdfPresent;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.mvp.XFragment;

/**
 * @author mashanshui
 * @date 2018/5/16
 * @desc 听课-》资料-》列表-》pdf展示
 */
public class LessonsDataPdfFragment extends XFragment<LessonsDataPdfPresent> implements OnPageChangeListener{
    private static final String TAG = "LessonsDataPdfFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.pdf_view)
    PDFView pdfView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    private TbsReaderView readerView;
    private QMUILoadingView loadingView;

    private String pdfUrl;
    private String mParam2;


    public LessonsDataPdfFragment() {
        // Required empty public constructor
    }


    public static LessonsDataPdfFragment newInstance(String pdfUrl) {
        LessonsDataPdfFragment fragment = new LessonsDataPdfFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, pdfUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pdfUrl = getArguments().getString(ARG_PARAM1);
        }
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        ivBack.bringToFront();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lessons_data_pdf;
    }

    @Override
    public LessonsDataPdfPresent newP() {
        return new LessonsDataPdfPresent(context);
    }

    @Override
    public void receiveStickyEvent(Event event) {
        if (event.getCode() == EventBusUtils.EventCode.LESSONS_PDF_URL) {
            String url = (String) event.getData();
//            String name = FileUtil.getFileName(url);
//            Router.newIntent(context).to(ShowFileActivity.class)
//                    .putString("filePath", path)
//                    .launch();
//            pdfView.fileFromLocalStorage(this, this, this, url, name);
            getP().loadPdfView(url);
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    public void showDialog() {
        loadingView = new QMUILoadingView(context);
        loadingView.setColor(Color.BLACK);
        loadingView.setSize(QMUIDisplayHelper.dp2px(context, 32));
        FrameLayout.LayoutParams loadingViewLP = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingViewLP.gravity = Gravity.CENTER;
        loadingView.setLayoutParams(loadingViewLP);
        layoutContent.addView(loadingView);
    }

    public void dismissDialog() {
        layoutContent.removeView(loadingView);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        LessonsDataFragment dataListFragment = (LessonsDataFragment) getParentFragment();
        dataListFragment.back();
    }

    public void showPdfView(String filePath) {
//        pdfView.fromFile(new File(filePath))
//                .defaultPage(1)
//                .showMinimap(true)
//                .enableSwipe(true)
//                .onPageChange(this)
//                .load();
        openFile(filePath);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    private void openFile(String filePath) {
        readerView = new TbsReaderView(context, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {
                Log.e(TAG, "onCallBackAction: "+integer );
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
        layoutContent.addView(readerView);
        ivBack.bringToFront();
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
    public void onDestroy() {
        if (readerView != null) {
            readerView.onStop();
        }
        super.onDestroy();
    }
}
