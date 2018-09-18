package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.network.ModifyService;
import com.shenhesoft.examsapp.network.model.SearchDateModel;
import com.shenhesoft.examsapp.network.model.SearchTypeModel;
import com.shenhesoft.examsapp.ui.activity.onlineproduct.SearchResultActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResultsList;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/6/25
 * @desc TODO
 */
public class OnlineSearchPresent extends XPresent<SearchResultActivity> {
    private ModifyService modifyService;

    public OnlineSearchPresent() {
        modifyService = HttpRequestUtil.getRetrofitClient(ModifyService.class.getName());
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
