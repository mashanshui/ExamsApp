package com.shenhesoft.examsapp.ui.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.necistudio.libarary.item.Document;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.FilePickerAdapter;
import com.shenhesoft.examsapp.ui.activity.SelectFileActivity;
import com.shenhesoft.examsapp.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * @author mashanshui
 * @date 2018-07-01
 * @desc 选择文件——》QQ、微信
 */
public class SelectTencentFileFragment extends XLazyFragment {
    private static final String ARG_PARAM1 = "type";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int type;

    private FilePickerAdapter adapter;
    private List<Document> scannerList;

    public SelectTencentFileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type
     * @return A new instance of fragment SelectTencentFileFragment.
     */
    public static SelectTencentFileFragment newInstance(int type) {
        SelectTencentFileFragment fragment = new SelectTencentFileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_PARAM1);
        }
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        scannerList = new ArrayList<>();
        adapter = new FilePickerAdapter(scannerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        View notDataView = getLayoutInflater().inflate(R.layout.recycler_file_empty_layout, (ViewGroup) recyclerView.getParent(), false);
        adapter.setEmptyView(notDataView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((SelectFileActivity) (getActivity())).filePickFinish(scannerList.get(position).getPath());
            }
        });
        String scanPath;
        if (type == AppConstant.QQ) {
            scanPath = FileUtil.getExternalStoragePath() + "/Tencent/QQfile_recv";
        } else {
            scanPath = FileUtil.getExternalStoragePath() + "/Tencent/MicroMsg/Download";
        }
        File file = new File(scanPath);
        if (file.exists()) {
            new FileAsyncTask().execute(scanPath);
        } else {
            IToast.showShort("找不到文件");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_tencent_file;
    }

    @Override
    public Object newP() {
        return null;
    }

    class FileAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            for (int i = 0; i < params.length; i++) {
                folderScan(params[i]);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            sortData(scannerList);
            for (int i = 0; i < scannerList.size(); i++) {
            }
            refreshData();
        }
    }

    private void sortData(List<Document> files) {
        Collections.sort(files, new Comparator<Document>() {
            @Override
            public int compare(Document o1, Document o2) {
                if (o2.getLastModifyTime() - o1.getLastModifyTime() == 0) {
                    return 0;
                } else if (o2.getLastModifyTime() - o1.getLastModifyTime() > 0) {
                    return 1;
                } else if (o2.getLastModifyTime() - o1.getLastModifyTime() < 0) {
                    return -1;
                }
                return 0;
            }
        });
    }

    public void refreshData() {
        adapter.notifyDataSetChanged();
    }

    /**
     * @param path
     */
    public void folderScan(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] array = file.listFiles();
            for (int i = 0; i < array.length; i++) {
                File f = array[i];
                if (f.isFile()) {//FILE TYPE
                    String name = f.getName();
                    String fileType = FileUtil.getFileType(name);
                    if (TextUtils.equals("doc", fileType) || TextUtils.equals("docx", fileType)) {
                        Document document = new Document();
                        document.setPath(f.getAbsolutePath());
                        scannerList.add(document);
                    }
                } else {//FOLDER TYPE
                    folderScan(f.getAbsolutePath());
                }
            }
        }
    }

}
