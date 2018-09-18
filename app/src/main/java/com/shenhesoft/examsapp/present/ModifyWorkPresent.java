package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.adapter.bean.ModifyWorkBean;
import com.shenhesoft.examsapp.data.ModifyWorkDataSource;
import com.shenhesoft.examsapp.data.remote.ModifyWorkRemoteDataSource;
import com.shenhesoft.examsapp.network.ModifyRetrofit;
import com.shenhesoft.examsapp.network.ModifyService;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.SearchDateModel;
import com.shenhesoft.examsapp.network.model.SearchTypeModel;
import com.shenhesoft.examsapp.view.ModifyWorkView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResultsList;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/5/28
 * @desc TODO
 */
public class ModifyWorkPresent extends XPresent<ModifyWorkView> {

    private ModifyWorkDataSource modifyWorkDataSource;
    private ModifyService modifyService;

    public ModifyWorkPresent() {
        modifyWorkDataSource = new ModifyWorkRemoteDataSource();
        modifyService = HttpRequestUtil.getRetrofitClient(ModifyService.class.getName());
    }

    public void loadBuyData(int start, int length, final String head) {
        modifyWorkDataSource.loadBuyData(start, length, new ModifyWorkDataSource.LoadBuyDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<ProductModel> allResults) {
                List<ProductModel> productModelList = allResults.getRows();
                if (!TextUtils.isEmpty(head)) {
                    List<ModifyWorkBean> modifyWorkBeanList = packData(productModelList, head);
                    getV().updateAlreadyBuy(modifyWorkBeanList);
                }
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    public void loadBuyDataRefresh(int start, int length, BGARefreshLayout bgaRefreshLayout) {
        modifyWorkDataSource.loadBuyData(start, length, bgaRefreshLayout, new ModifyWorkDataSource.LoadBuyDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<ProductModel> allResults) {
                List<ProductModel> productModelList = allResults.getRows();
                List<ModifyWorkBean> modifyWorkBeanList = packData(productModelList, null);
                //下拉刷新
                if (!getV().getIsLoadingMore()) {
                    getV().updateAlreadyBuy(modifyWorkBeanList);
                }
                //上拉加载后有数据
                else if (getV().getIsLoadingMore() && !modifyWorkBeanList.isEmpty()) {
                    getV().updateAddAlreadyBuy(modifyWorkBeanList);
                }
                //上拉加载后没有数据
                else if (getV().getIsLoadingMore() && modifyWorkBeanList.isEmpty()) {
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

    public void loadRecommendData(int start, int length, final String head) {
        modifyWorkDataSource.loadRecommendData(start, length, new ModifyWorkDataSource.LoadBuyDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<ProductModel> allResults) {
                List<ProductModel> productModelList = allResults.getRows();
                if (!TextUtils.isEmpty(head)) {
                    List<ModifyWorkBean> modifyWorkBeanList = packData(productModelList, head);
                    getV().updateRecommend(modifyWorkBeanList);
                }
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    public void loadRecommendDataRefresh(int start, int length, BGARefreshLayout bgaRefreshLayout) {
        modifyWorkDataSource.loadRecommendData(start, length, bgaRefreshLayout, new ModifyWorkDataSource.LoadBuyDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<ProductModel> allResults) {
                List<ProductModel> productModelList = allResults.getRows();
                List<ModifyWorkBean> modifyWorkBeanList = packData(productModelList, null);
                //下拉刷新
                if (!getV().getIsLoadingMore()) {
                    getV().updateRecommend(modifyWorkBeanList);
                }
                //上拉加载后有数据
                else if (getV().getIsLoadingMore() && !modifyWorkBeanList.isEmpty()) {
                    getV().updateAddRecommend(modifyWorkBeanList);
                }
                //上拉加载后没有数据
                else if (getV().getIsLoadingMore() && modifyWorkBeanList.isEmpty()) {
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

    public void filterData(String searchType, String searchSource, String searchDate, BGARefreshLayout bgaRefreshLayout) {
        getV().setStart(0);
        Observable<RequestResults<ListALLResults<ProductModel>>> observable = modifyService.getRecommendProduct(ModifyRetrofit.getInstance()
                .getFilterRecommendProduct(getV().getStart(), getV().getLength(), searchType, searchSource, searchDate));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<ProductModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<ProductModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<ProductModel>> data) {
                if (data.getState() != 200) {
                    //加载失败将start重置到加载之前
                    if (getV().getIsLoadingMore()) {
                        getV().setStart(getV().getStart() - getV().getLength());
                    }
                    IToast.showShort(data.getMsg());
                    return;
                }
                List<ProductModel> productModelList = data.getObj().getRows();
                List<ModifyWorkBean> modifyWorkBeanList = packData(productModelList, null);
                //下拉刷新
                if (!getV().getIsLoadingMore()) {
                    getV().updateRecommend(modifyWorkBeanList);
                }
                //上拉加载后有数据
                else if (getV().getIsLoadingMore() && !modifyWorkBeanList.isEmpty()) {
                    getV().updateAddRecommend(modifyWorkBeanList);
                }
                //上拉加载后没有数据
                else if (getV().getIsLoadingMore() && modifyWorkBeanList.isEmpty()) {
                    getV().setStart(getV().getStart() - getV().getLength());
                    IToast.showShort("没有更多数据");
                }
            }
        });
        httpObserver.setRefreshLayout(bgaRefreshLayout);
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    private List<ModifyWorkBean> packData(List<ProductModel> productModelList, String head) {
        List<ModifyWorkBean> modifyWorkBeanList = new ArrayList<>();
        if (!TextUtils.isEmpty(head)) {
            ModifyWorkBean modifyWorkHead = new ModifyWorkBean(true, head);
            modifyWorkBeanList.add(modifyWorkHead);
        }
        for (int i = 0; i < productModelList.size(); i++) {
            ModifyWorkBean modifyWorkBean = new ModifyWorkBean(productModelList.get(i));
            modifyWorkBeanList.add(modifyWorkBean);
        }
        return modifyWorkBeanList;
    }

    public void getPopupType(final String pid) {
        Observable<RequestResultsList<SearchTypeModel>> observable = modifyService.getSearchType(pid);
        HttpObserver httpObserver = new HttpObserver<RequestResultsList<SearchTypeModel>>(new HttpObserver.OnNextListener<RequestResultsList<SearchTypeModel>>() {
            @Override
            public void onNext(RequestResultsList<SearchTypeModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                List<SearchTypeModel> searchTypeModels = data.getObj();
                if (TextUtils.equals("11", pid)) {
                    dealData(0, data.getObj());
                    getV().updatePopupData(searchTypeModels);
                } else {
                    dealData(1, data.getObj());
                    getV().updatePopupData(searchTypeModels);
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    public void getPopupDate() {
        Observable<RequestResults<SearchDateModel>> observable = modifyService.getSearchDate();
        HttpObserver httpObserver = new HttpObserver<RequestResults<SearchDateModel>>(new HttpObserver.OnNextListener<RequestResults<SearchDateModel>>() {
            @Override
            public void onNext(RequestResults<SearchDateModel> data) {
                if (data.getState() != 200) {
                    IToast.showShort(data.getMsg());
                    return;
                }
                if (data.getObj().getListYear().isEmpty()) {
                    IToast.showShort("未获取到数据");
                    return;
                }
                SearchDateModel.ListYearBean listYear = data.getObj().getListYear().get(0);
                List<SearchTypeModel> searchTypeModels = convertData(listYear);
                dealData(2, searchTypeModels);
                sortData(searchTypeModels);
                getV().updatePopupData(searchTypeModels);
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    private void sortData(List<SearchTypeModel> searchTypeModels) {
        Collections.sort(searchTypeModels, new Comparator<SearchTypeModel>() {
            @Override
            public int compare(SearchTypeModel o1, SearchTypeModel o2) {
                int num1 = Integer.valueOf(o1.getText());
                int num2 = Integer.valueOf(o2.getText());
                if (num2 - num1 == 0) {
                    return 0;
                } else if (num2 - num1 > 0) {
                    return 1;
                } else if (num2 - num1 < 0) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private List<SearchTypeModel> convertData(SearchDateModel.ListYearBean listYear) {
        List<SearchTypeModel> searchTypeModels = new ArrayList<>();
        for (Map.Entry<String, String> entry : listYear.getEconomyExamyear().entrySet()) {
            SearchTypeModel searchTypeModel = new SearchTypeModel();
            searchTypeModel.setId(entry.getValue());
            searchTypeModel.setText(entry.getValue());
            searchTypeModels.add(searchTypeModel);
        }
        for (Map.Entry<String, String> entry : listYear.getManagerExamyear().entrySet()) {
            SearchTypeModel searchTypeModel = new SearchTypeModel();
            searchTypeModel.setId(entry.getValue());
            searchTypeModel.setText(entry.getValue());
            searchTypeModels.add(searchTypeModel);
        }
        for (Map.Entry<String, String> entry : listYear.getMbaJanuaryYear().entrySet()) {
            SearchTypeModel searchTypeModel = new SearchTypeModel();
            searchTypeModel.setId(entry.getValue());
            searchTypeModel.setText(entry.getValue());
            searchTypeModels.add(searchTypeModel);
        }
        for (Map.Entry<String, String> entry : listYear.getMbaOctoberYear().entrySet()) {
            SearchTypeModel searchTypeModel = new SearchTypeModel();
            searchTypeModel.setId(entry.getValue());
            searchTypeModel.setText(entry.getValue());
            searchTypeModels.add(searchTypeModel);
        }
        return searchTypeModels;
    }

    private void dealData(int type, List<SearchTypeModel> obj) {
        for (int i = 0; i < obj.size(); i++) {
            SearchTypeModel model = obj.get(i);
            model.setType(type);
        }
    }


}
