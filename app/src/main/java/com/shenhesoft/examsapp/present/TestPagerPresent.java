package com.shenhesoft.examsapp.present;

import com.shenhesoft.examsapp.data.TestPagerDataSource;
import com.shenhesoft.examsapp.data.remote.TestPagerRemoteDataSource;
import com.shenhesoft.examsapp.network.model.TestPagerListModel;
import com.shenhesoft.examsapp.ui.activity.dohomework.TestPaperActivity;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/5/23
 * @desc TODO
 */
public class TestPagerPresent extends XPresent<TestPaperActivity> {
    private TestPagerDataSource testPagerDataSource;

    public TestPagerPresent() {
        this.testPagerDataSource = new TestPagerRemoteDataSource();
    }

    public void loadData(String productId, int status, BGARefreshLayout bgaRefreshLayout) {
        testPagerDataSource.loadData(productId, status, getV().start, getV().length, bgaRefreshLayout, new TestPagerDataSource.LoadDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<TestPagerListModel> testPagerListModel) {
                List<TestPagerListModel> testPagerListModels = testPagerListModel.getRows();
                //下拉刷新
                if (!getV().isLoadMore) {
                    getV().updateData(testPagerListModels);
                }
                //上拉加载后有数据
                else if (getV().isLoadMore && !testPagerListModels.isEmpty()) {
                    getV().updateAddData(testPagerListModels);
                }
                //上拉加载后没有数据
                else if (getV().isLoadMore && testPagerListModels.isEmpty()) {
                    getV().start = getV().start - getV().length;
                    IToast.showShort("没有更多数据");
                }
            }

            @Override
            public void onFail(String msg) {
                //加载失败将start重置到加载之前
                if (getV().isLoadMore) {
                    getV().start = getV().start - getV().length;
                }
                IToast.showShort(msg);
            }
        });
    }
}
