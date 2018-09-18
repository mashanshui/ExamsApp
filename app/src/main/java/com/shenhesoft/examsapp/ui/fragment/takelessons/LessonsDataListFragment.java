package com.shenhesoft.examsapp.ui.fragment.takelessons;


import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.LessonsDataAdapter;
import com.shenhesoft.examsapp.network.model.LessonsModel;
import com.shenhesoft.examsapp.network.model.LessonsSection;
import com.shenhesoft.examsapp.network.model.LessonsVideo;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.LessonsDataPresent;
import com.shenhesoft.examsapp.view.LessonsDataView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import io.reactivex.functions.Consumer;

/**
 * @author mashanshui
 * @date 2018/5/16
 * @desc 听课-》资料-》列表
 */
public class LessonsDataListFragment extends XLazyFragment<LessonsDataPresent> implements LessonsDataView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;

    private String mParam1;
    private String mParam2;

    @BindView(R.id.rv_lessons_data)
    RecyclerView rvLessonsData;

    private LessonsDataAdapter dataAdapter;
    private List<MultiItemEntity> pdfList;
    private ProductModel productModel;

    private boolean isLoadMore;
    private int start = 0;
    private int length = 20;

    public LessonsDataListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LessonsDataListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LessonsDataListFragment newInstance(String param1, String param2) {
        LessonsDataListFragment fragment = new LessonsDataListFragment();
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
        pdfList = new ArrayList<>();
        dataAdapter = new LessonsDataAdapter(pdfList, getChildFragmentManager());
        rvLessonsData.setLayoutManager(new LinearLayoutManager(context));
        rvLessonsData.setAdapter(dataAdapter);
        dataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                RxPermissions rxPermissions = new RxPermissions(ActivityCollector.getTopActivity());
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {
                                    LessonsVideo lessonsVideo = (LessonsVideo) pdfList.get(position);
                                    getP().getPdfUrl(lessonsVideo.getVideoId());
                                } else {
                                    showKonwnDialog();
                                }
                            }
                        });
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
        return R.layout.fragment_lessons_data_list;
    }

    @Override
    public LessonsDataPresent newP() {
        return new LessonsDataPresent();
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

    @Override
    public void getUrlSuccess(String downloadUrl) {
        EventBusUtils.sendStickyEvent(new Event(EventBusUtils.EventCode.LESSONS_PDF_URL, downloadUrl));
        LessonsDataFragment dataListFragment = (LessonsDataFragment) getParentFragment();
        dataListFragment.addFragment();
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
            pdfList.add(lessonsModel);
        }
        refreshData();
    }

    @Override
    public void updateData(List<LessonsModel> lessonsModelList) {
        if (!pdfList.isEmpty()) {
            pdfList.clear();
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
            pdfList.add(lessonsModel);
        }
        refreshData();
    }

    public void refreshData() {
        dataAdapter.notifyDataSetChanged();
    }

    private void showKonwnDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_single_confirm)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "没有权限无法查看");
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
                .show(getChildFragmentManager());
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
}
