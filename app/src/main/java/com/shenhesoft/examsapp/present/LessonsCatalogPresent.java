package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.data.LessonsCatalogDataSource;
import com.shenhesoft.examsapp.data.remote.LessonsCatalogRemoteDataSource;
import com.shenhesoft.examsapp.network.model.LessonsModel;
import com.shenhesoft.examsapp.view.LessonsCatalogView;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/6/5
 * @desc TODO
 */
public class LessonsCatalogPresent extends XPresent<LessonsCatalogView> {

    private LessonsCatalogDataSource lessonsCatalogDataSource;

    public LessonsCatalogPresent() {
        lessonsCatalogDataSource = new LessonsCatalogRemoteDataSource();
    }

    public void loadData(BGARefreshLayout bgaRefreshLayout) {
        if (TextUtils.isEmpty(getV().getProductId())) {
            loadAllData(bgaRefreshLayout);
        } else {
            loadSingleData(bgaRefreshLayout);
        }
    }

    private void loadSingleData(BGARefreshLayout bgaRefreshLayout) {
        lessonsCatalogDataSource.loadSingleData(bgaRefreshLayout, getV().getStart(), getV().getLength(), getV().getProductId(), new LessonsCatalogDataSource.LoadSingleDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<LessonsModel> allResults) {
                List<LessonsModel> lessonsModelList = allResults.getRows();
                getV().updateData(lessonsModelList);
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    private void loadAllData(BGARefreshLayout bgaRefreshLayout) {
        lessonsCatalogDataSource.loadData(bgaRefreshLayout, getV().getStart(), getV().getLength(), new LessonsCatalogDataSource.LoadDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<LessonsModel> allResults) {
                List<LessonsModel> lessonsModelList = allResults.getRows();
                //下拉刷新
                if (!getV().getIsLoadingMore()) {
                    getV().updateData(lessonsModelList);
                }
                //上拉加载后有数据
                else if (getV().getIsLoadingMore() && !lessonsModelList.isEmpty()) {
                    getV().updateAddData(lessonsModelList);
                }
                //上拉加载后没有数据
                else if (getV().getIsLoadingMore() && lessonsModelList.isEmpty()) {
                    getV().setStart(getV().getStart() - getV().getLength());
                    IToast.showShort("没有更多数据");
                }
            }

            @Override
            public void onFail(String msg) {
                //加载失败将start重置到加载之前
                if (getV().getIsLoadingMore()) {
                    getV().setStart(getV().getStart() - getV().getLength());
                }
                IToast.showShort(msg);
            }
        });
    }
}