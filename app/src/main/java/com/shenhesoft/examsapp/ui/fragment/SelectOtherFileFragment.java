package com.shenhesoft.examsapp.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.necistudio.libarary.cursors.loadercallback.FileResultCallback;
import com.necistudio.libarary.item.Document;
import com.necistudio.libarary.utils.MediaStoreHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.FilePickerAdapter;
import com.shenhesoft.examsapp.ui.activity.SelectFileActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * @author mashanshui
 * @date 2018-07-01
 * @desc 文件选择——》其他文件
 */
public class SelectOtherFileFragment extends XLazyFragment {
    private static final String ARG_PARAM1 = "TypeList";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private FilePickerAdapter adapter;

    private List<String> typeList;
    private List<Document> documentList;
    private QMUITipDialog tipDialog;

    public SelectOtherFileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list
     * @return A new instance of fragment SelectOtherFileFragment.
     */
    public static SelectOtherFileFragment newInstance(ArrayList<String> list) {
        SelectOtherFileFragment fragment = new SelectOtherFileFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeList = getArguments().getStringArrayList(ARG_PARAM1);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        documentList = new ArrayList<>();
        adapter = new FilePickerAdapter(documentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        View notDataView = getLayoutInflater().inflate(R.layout.recycler_file_empty_layout, (ViewGroup) recyclerView.getParent(), false);
        adapter.setEmptyView(notDataView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((SelectFileActivity) (getActivity())).filePickFinish(documentList.get(position).getPath());
            }
        });
        getDocument();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_other_file;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void getDocument() {
        tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        tipDialog.show();
        MediaStoreHelper.getDocs(getActivity(), typeList, new FileResultCallback<Document>() {
            @Override
            public void onResultCallback(List<Document> files) {
                tipDialog.dismiss();
                sortData(files);
                refreshData(files);
            }
        });
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

    public void refreshData(List<Document> files) {
        if (!documentList.isEmpty()) {
            documentList.clear();
        }
        documentList.addAll(files);
        adapter.notifyDataSetChanged();
    }
}
