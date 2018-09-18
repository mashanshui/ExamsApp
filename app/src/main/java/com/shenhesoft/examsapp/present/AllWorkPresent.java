package com.shenhesoft.examsapp.present;

import android.text.TextUtils;

import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.data.AllWorkDataSource;
import com.shenhesoft.examsapp.data.remote.AllWorkRemoteDataSource;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.ScienceDomainsModel;
import com.shenhesoft.examsapp.ui.fragment.dohomework.AllWorkFragment;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/5/22
 * @desc TODO
 */
public class AllWorkPresent extends XPresent<AllWorkFragment> {
    private static final String TAG = "AllWorkPresent";
    private AllWorkDataSource allWorkDataSource;

    public AllWorkPresent() {
        allWorkDataSource = new AllWorkRemoteDataSource();
    }

    public void loadData(int productType, final int isBuy, BGARefreshLayout bgaRefreshLayout) {
        if (productType == AppConstant.QuestionBank) {
            loadQuestionBankData(productType, isBuy, getV().getScienceDomainId(), bgaRefreshLayout);
        } else {
            loadCourseWareData(productType, isBuy, getV().getScienceDomainId(), bgaRefreshLayout);
        }
    }

    private void loadCourseWareData(int productType, final int isBuy, String scienceDomainId, BGARefreshLayout bgaRefreshLayout) {
        allWorkDataSource.loadCourseWareData(productType, isBuy, scienceDomainId, getV().start, getV().length, bgaRefreshLayout, new AllWorkDataSource.LoadDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<ProductModel> allResults) {
                List<ProductModel> subjectListBeans = allResults.getRows();
                //更新购买状态
//                updateBuyStatus(isBuy, subjectListBeans);
                //下拉刷新
                if (!getV().isLoadMore) {
                    getV().updateData(subjectListBeans);
                }
                //上拉加载后有数据
                else if (getV().isLoadMore && !subjectListBeans.isEmpty()) {
                    getV().updateAddData(subjectListBeans);
                }
                //上拉加载后没有数据
                else if (getV().isLoadMore && subjectListBeans.isEmpty()) {
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

    private void loadQuestionBankData(int productType, final int isBuy, String scienceDomainId, BGARefreshLayout bgaRefreshLayout) {
        allWorkDataSource.loadQuestionBankData(productType, isBuy, scienceDomainId, getV().start, getV().length, bgaRefreshLayout, new AllWorkDataSource.LoadDataCallBack() {
            @Override
            public void onSuccess(ListALLResults<ProductModel> allResults) {
                List<ProductModel> subjectListBeans = allResults.getRows();
                //更新购买状态
//                updateBuyStatus(isBuy, subjectListBeans);
                //下拉刷新
                if (!getV().isLoadMore) {
                    getV().updateData(subjectListBeans);
                }
                //上拉加载后有数据
                else if (getV().isLoadMore && !subjectListBeans.isEmpty()) {
                    getV().updateAddData(subjectListBeans);
                }
                //上拉加载后没有数据
                else if (getV().isLoadMore && subjectListBeans.isEmpty()) {
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


    public void getScienceDomainId(final String subjectType) {
        if (TextUtils.isEmpty(subjectType)) {
            return;
        }
        allWorkDataSource.getScienceDomainId(new AllWorkDataSource.ScienceDomainIdCallBack() {
            @Override
            public void onSuccess(ListALLResults<ScienceDomainsModel> allResults) {
                String scienceDomainId = getScienceDomainIdByType(subjectType, allResults.getRows());
                getV().setScienceDomainId(scienceDomainId);
                getV().getScienceDomainSuccess();
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    private String getScienceDomainIdByType(String subjectType, List<ScienceDomainsModel> rows) {
        for (int i = 0; i < rows.size(); i++) {
            ScienceDomainsModel scienceDomainsModel = rows.get(i);
            if (TextUtils.equals(scienceDomainsModel.getScienceDomainName(), subjectType)) {
                return scienceDomainsModel.getId();
            }
        }
        return null;
    }

    private void updateBuyStatus(int isBuy, List<ProductModel> subjectListBeans) {
        for (int i = 0; i < subjectListBeans.size(); i++) {
            if (isBuy == AppConstant.Buy) {
                subjectListBeans.get(i).setBuyStatus(AppConstant.Buy);
            } else {
                subjectListBeans.get(i).setBuyStatus(AppConstant.NotBuy);
            }
        }
    }

}
