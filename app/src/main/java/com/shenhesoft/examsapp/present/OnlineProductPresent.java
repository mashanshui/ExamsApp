package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.data.OnlineProductDataSource;
import com.shenhesoft.examsapp.data.remote.OnlineProductRemoteDataSource;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.view.OnlineProductView;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/5/29
 * @desc TODO
 */
public class OnlineProductPresent extends XPresent<OnlineProductView> {

    private OnlineProductDataSource onlineProductDataSource;

    public OnlineProductPresent() {
        onlineProductDataSource = new OnlineProductRemoteDataSource();
    }

    public void loadData(int productType, String sort, BGARefreshLayout bgaRefreshLayout) {
        onlineProductDataSource.loadData(productType, sort, getV().getStart(), getV().getLength(), bgaRefreshLayout, new OnlineProductDataSource.LoadDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<ProductModel> allResults) {
                List<ProductModel> productModels = allResults.getRows();
                //下拉刷新
                if (!getV().getIsLoadingMore()) {
                    getV().updateData(productModels);
                }
                //上拉加载后有数据
                else if (getV().getIsLoadingMore() && !productModels.isEmpty()) {
                    getV().updateAddData(productModels);
                }
                //上拉加载后没有数据
                else if (getV().getIsLoadingMore() && productModels.isEmpty()) {
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

    public void searchData(BGARefreshLayout bgaRefreshLayout) {
        if (TextUtils.isEmpty(getV().getSearchKey())) {
            IToast.showShort("请输入关键字");
            return;
        }
        onlineProductDataSource.searchData(getV().getProductType(), getV().getStart(), getV().getLength(), getV().getSearchKey()
                , getV().getSearchType(), getV().getSearchSource(), getV().getSearchDate(), bgaRefreshLayout, new OnlineProductDataSource.SearchDataCallBack() {
                    @Override
                    public void onSuccess(ListALLResults<ProductModel> allResults) {
                        List<ProductModel> productModels = allResults.getRows();
                        //下拉刷新
                        if (!getV().getIsLoadingMore()) {
                            getV().updateData(productModels);
                        }
                        //上拉加载后有数据
                        else if (getV().getIsLoadingMore() && !productModels.isEmpty()) {
                            getV().updateAddData(productModels);
                        }
                        //上拉加载后没有数据
                        else if (getV().getIsLoadingMore() && productModels.isEmpty()) {
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
