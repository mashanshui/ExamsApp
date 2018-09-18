package com.shenhesoft.examsapp.ui.fragment.takelessons;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.LessonsListAdapter;
import com.shenhesoft.examsapp.adapter.bean.LessonsListBean1;
import com.shenhesoft.examsapp.adapter.bean.LessonsListBean2;
import com.shenhesoft.examsapp.adapter.bean.LessonsListBean3;
import com.shenhesoft.examsapp.data.LocalVideoDataSource;
import com.shenhesoft.examsapp.data.db.LocalVideo;
import com.shenhesoft.examsapp.data.local.LocalVideoLocalDataSource;
import com.shenhesoft.examsapp.network.model.LessonsModel;
import com.shenhesoft.examsapp.network.model.LessonsSection;
import com.shenhesoft.examsapp.network.model.LessonsVideo;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.LessonsCatalogPresent;
import com.shenhesoft.examsapp.util.BackHandlerHelper;
import com.shenhesoft.examsapp.view.LessonsCatalogView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * @author mashanshui
 * @date 2018/5/16
 * @desc 听课-》目录
 */
public class LessonsCatalogFragment extends XLazyFragment<LessonsCatalogPresent> implements LessonsCatalogView, BGARefreshLayout.BGARefreshLayoutDelegate,BackHandlerHelper.FragmentBackHandler {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_lessons_list)
    RecyclerView rvLessonsList;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private String mParam1;
    private String mParam2;

    private LessonsListAdapter lessonsListAdapter;
    private List<MultiItemEntity> itemEntities;
    private ProductModel productModel;
    private LocalVideoDataSource localVideoDataSource;

    private boolean isLoadMore;
    private int start = 0;
    private int length = 20;

    public LessonsCatalogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LessonsCatalogFragment.
     */
    public static LessonsCatalogFragment newInstance(String param1, String param2) {
        LessonsCatalogFragment fragment = new LessonsCatalogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        localVideoDataSource = new LocalVideoLocalDataSource();
        itemEntities = new ArrayList<>();
        lessonsListAdapter = new LessonsListAdapter(itemEntities, getChildFragmentManager());
        rvLessonsList.setAdapter(lessonsListAdapter);
        rvLessonsList.setLayoutManager(new LinearLayoutManager(context));
        lessonsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (adapter.getItemViewType(position) == LessonsListAdapter.LESSONS_LIST_1) {
//                    if (((LessonsListBean1)itemEntities.get(position)).isExpanded()) {
//                        adapter.collapse(position);
//                    } else {
//                        adapter.expand(position);
//                    }
//                }
            }
        });
        lessonsListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        if (productModel == null) {
            bgaRefreshLayout.setDelegate(this);
            BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, true);
            bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
        }
        getP().loadData(bgaRefreshLayout);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lessons_catalog;
    }

    @Override
    public LessonsCatalogPresent newP() {
        return new LessonsCatalogPresent();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEvent(ProductModel listBean) {
        if (listBean == null) {
            return;
        }
        if (listBean.getProductType() == AppConstant.CourseWare) {
            productModel = listBean;
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public String getProductId() {
        return productModel == null ? "" : productModel.getId();
    }

    private void generateData() {
        int lv0Count = 3;
        int lv1Count = 3;
        int personCount = 5;

        for (int i = 0; i < lv0Count; i++) {
            LessonsListBean1 lv0 = new LessonsListBean1("1level" + i);
            for (int j = 0; j < lv1Count; j++) {
                LessonsListBean2 lv1 = new LessonsListBean2("2level" + j);
                for (int k = 0; k < personCount; k++) {
                    lv1.addSubItem(new LessonsListBean3("level" + k));
                }
                lv0.addSubItem(lv1);
            }
            itemEntities.add(lv0);
        }
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public boolean getIsLoadingMore() {
        return isLoadMore;
    }

    @Override
    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public void updateAddData(List<LessonsModel> lessonsModelList) {
        for (int i = 0; i < lessonsModelList.size(); i++) {
            LessonsModel lessonsModel = lessonsModelList.get(i);
            List<LessonsSection> lessonsSections = lessonsModel.getClassSection();
            for (int i1 = 0; i1 < lessonsSections.size(); i1++) {
                LessonsSection lessonsSection = lessonsSections.get(i1);
                List<LessonsVideo> lessonsVideos = lessonsSection.getClassVideo();
                for (int i2 = 0; i2 < lessonsVideos.size(); i2++) {
                    lessonsSection.addSubItem(lessonsVideos.get(i2));
                }
                lessonsModel.addSubItem(lessonsSection);
            }
            itemEntities.add(lessonsModel);
        }
        refreshData();
    }

    @Override
    public void updateData(List<LessonsModel> lessonsModelList) {
        if (!itemEntities.isEmpty()) {
            itemEntities.clear();
        }
        for (int i = 0; i < lessonsModelList.size(); i++) {
            LessonsModel lessonsModel = lessonsModelList.get(i);
            List<LessonsSection> lessonsSections = lessonsModel.getClassSection();
            for (int i1 = 0; i1 < lessonsSections.size(); i1++) {
                LessonsSection lessonsSection = lessonsSections.get(i1);
                List<LessonsVideo> lessonsVideos = lessonsSection.getClassVideo();
                for (int i2 = 0; i2 < lessonsVideos.size(); i2++) {
                    lessonsSection.addSubItem(lessonsVideos.get(i2));
                }
                lessonsModel.addSubItem(lessonsSection);
            }
            itemEntities.add(lessonsModel);
        }
        refreshData();
    }

    public void refreshData() {
        lessonsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        isLoadMore = false;
        start = 0;
        getP().loadData(bgaRefreshLayout);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        isLoadMore = true;
        start = start + length;
        getP().loadData(bgaRefreshLayout);
        return true;
    }

    @Override
    public boolean onBackPressed() {
        if (isHave()) {
            showConfirmDialog();
            return true;
        } else {
            return false;
        }
    }

    private boolean isHave() {
        List<LocalVideo> localVideoList = DataSupport.findAll(LocalVideo.class);
        for (int i = 0; i < localVideoList.size(); i++) {
            if (localVideoList.get(i).getIsComplete() == LocalVideo.InComplete) {
                LocalVideo localVideo = localVideoList.get(i);
                byte b = FileDownloader.getImpl().getStatus(localVideo.getTaskId(), localVideo.getFilepath());
                if (b == FileDownloadStatus.progress) {
                    return true;
                }
            }
        }
        return false;
    }

    private void showConfirmDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_confirm_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(com.othershe.nicedialog.ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "退出后将暂停下载");
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FileDownloader.getImpl().pauseAll();
                                getActivity().finish();
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
                .show(getChildFragmentManager());
    }
}
