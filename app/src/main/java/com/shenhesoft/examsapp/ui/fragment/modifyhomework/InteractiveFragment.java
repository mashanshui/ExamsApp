package com.shenhesoft.examsapp.ui.fragment.modifyhomework;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.FileDownloader;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.InteractiveListAdapter;
import com.shenhesoft.examsapp.network.model.InteractiveModel;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.InteractivePresent;
import com.shenhesoft.examsapp.ui.activity.SelectFileActivity;
import com.shenhesoft.examsapp.ui.activity.user.ShowFileActivity;
import com.shenhesoft.examsapp.util.FileUtil;
import com.shenhesoft.examsapp.util.GridItemDecoration;
import com.shenhesoft.examsapp.util.event.ChatEvent;
import com.shenhesoft.examsapp.view.InteractiveView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.net.HttpConstant;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;

/**
 * @author mashanshui
 * @date 2018/5/17
 * @desc 师生交互
 */
public class InteractiveFragment extends XLazyFragment<InteractivePresent> implements InteractiveView, BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final int RequestCode = 101;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_upload)
    QMUIRoundButton btnUpload;
    @BindView(R.id.bgaRefreshLayout)
    BGARefreshLayout bgaRefreshLayout;
    private QMUIRoundButton btnDownload;

    private String mParam1;
    private String mParam2;
    private int downloadId = 0;

    private InteractiveListAdapter interactiveListAdapter;
    private List<InteractiveModel> interactiveMessageBeans;
    private ProductModel productModel;

    public InteractiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InteractiveFragment.
     */
    public static InteractiveFragment newInstance(String param1, String param2) {
        InteractiveFragment fragment = new InteractiveFragment();
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
        btnUpload.setChangeAlphaWhenPress(true);
        interactiveMessageBeans = new ArrayList<>();
        interactiveListAdapter = new InteractiveListAdapter(interactiveMessageBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new GridItemDecoration(context, R.drawable.recycler_menu_divider));
        recyclerView.setAdapter(interactiveListAdapter);
        interactiveListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_download_affix) {
                    btnDownload = view.findViewById(R.id.btn_download_affix);
                    String url = interactiveMessageBeans.get(position).getArticleAttachReply();
                    if (TextUtils.isEmpty(url)) {
                        IToast.showShort("附件不存在");
                        return;
                    }
                    url = HttpConstant.BASE_URL + url;
                    String path = url.substring(url.lastIndexOf("/"));
                    path = FileUtil.getDownloadAffixPath(context) + path + "." + interactiveMessageBeans.get(position).getAttachPostfixReply();
                    if ("下载附件".equals(btnDownload.getText().toString())) {
                        final String finalUrl = url;
                        final String finalPath = path;
                        getRxPermissions().request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) {
                                        if (aBoolean) {
                                            getP().downloadWriting(finalUrl, finalPath);
                                        } else {
                                            showKonwnDialog();
                                        }
                                    }
                                });
                    } else if ("查看附件".equals(btnDownload.getText().toString())) {
                        Router.newIntent(context).to(ShowFileActivity.class)
                                .putString("filePath", path)
                                .launch();
                    }
                }
            }
        });
        bgaRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder bgaNormalRefreshViewHolder = new BGANormalRefreshViewHolder(context, false);
        bgaRefreshLayout.setRefreshViewHolder(bgaNormalRefreshViewHolder);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_interactive;
    }

    @Override
    public InteractivePresent newP() {
        return new InteractivePresent();
    }

    @OnClick(R.id.btn_upload)
    public void onViewClicked() {
        if ((interactiveMessageBeans.size() + 1) % 2 == 0) {
            IToast.showShort("等待老师回传后再上传");
            return;
        }
        Intent intent = new Intent(context, SelectFileActivity.class);
        startActivityForResult(intent, RequestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("path");
            getP().uploadWriting(path, productModel.getOrderId());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEvent(ProductModel listBean) {
        if (listBean == null) {
            IToast.showShort("异常，请重新进入");
            getActivity().finish();
        }
        productModel = listBean;
        getP().loadData(bgaRefreshLayout, listBean.getOrderId());
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void updateData(List<InteractiveModel> interactiveModels) {
        if (!interactiveMessageBeans.isEmpty()) {
            interactiveMessageBeans.clear();
        }
        interactiveMessageBeans.addAll(interactiveModels);
        if (!interactiveModels.isEmpty()) {
            String chatId = interactiveModels.get(0).getChatId();
            EventBus.getDefault().postSticky(new ChatEvent(chatId));
        }
        refreshData();
    }

    @Override
    public void setDownloadId(int downloadId) {
        this.downloadId = downloadId;
    }

    @Override
    public void setBtnDownloadText(String text) {
        if (btnDownload != null) {
            btnDownload.setText(text);
        }
    }

    @Override
    public void beginRefresh() {
        bgaRefreshLayout.beginRefreshing();
    }

    private void refreshData() {
        interactiveListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (downloadId != 0) {
            FileDownloader.getImpl().pause(downloadId);
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getP().loadData(bgaRefreshLayout, productModel.getOrderId());
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
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
                .show(getChildFragmentManager());
    }
}
