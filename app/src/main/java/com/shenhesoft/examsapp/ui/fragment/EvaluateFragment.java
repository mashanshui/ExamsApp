package com.shenhesoft.examsapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.MainViewPageFragmentAdapter;
import com.shenhesoft.examsapp.network.model.EvaluateModel;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.EvaluatePresent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * @author mashanshui
 * @date 2018/5/14
 * @desc 课件详情—>评价
 */
public class EvaluateFragment extends XLazyFragment<EvaluatePresent> {
    private static final String TAG = "EvaluateFragment";
    private static final String ARG_PARAM1 = "PRODUCT_ID";
    @BindView(R.id.all_evaluate)
    QMUIRoundButton allEvaluate;
    @BindView(R.id.good_evaluate)
    QMUIRoundButton goodEvaluate;
    @BindView(R.id.middle_evaluate)
    QMUIRoundButton middleEvaluate;
    @BindView(R.id.bad_evaluate)
    QMUIRoundButton badEvaluate;
    @BindView(R.id.viewPage_evaluate)
    QMUIViewPager viewPageEvaluate;

    private String productId;

    private EvaluateContentFragment allEvaluateFragment;
    private EvaluateContentFragment goodEvaluateFragment;
    private EvaluateContentFragment middleEvaluateFragment;
    private EvaluateContentFragment badEvaluateFragment;

    public EvaluateFragment() {
        // Required empty public constructor
    }

    public static EvaluateFragment newInstance(String productId) {
        EvaluateFragment fragment = new EvaluateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        allEvaluate.setChangeAlphaWhenPress(true);
        goodEvaluate.setChangeAlphaWhenPress(true);
        middleEvaluate.setChangeAlphaWhenPress(true);
        badEvaluate.setChangeAlphaWhenPress(true);
//
//        allEvaluateFragment = EvaluateContentFragment.newInstance(productId, "");
//        goodEvaluateFragment = EvaluateContentFragment.newInstance(productId, "0");
//        middleEvaluateFragment = EvaluateContentFragment.newInstance(productId, "1");
//        badEvaluateFragment = EvaluateContentFragment.newInstance(productId, "2");
//
//        MainViewPageFragmentAdapter viewPageFragmentAdapter = new MainViewPageFragmentAdapter(getChildFragmentManager());
//        viewPageFragmentAdapter.addFragment(allEvaluateFragment);
//        viewPageFragmentAdapter.addFragment(goodEvaluateFragment);
//        viewPageFragmentAdapter.addFragment(middleEvaluateFragment);
//        viewPageFragmentAdapter.addFragment(badEvaluateFragment);
//        //禁止滑动切换
//        viewPageEvaluate.setSwipeable(false);
//        viewPageEvaluate.setAdapter(viewPageFragmentAdapter);
    }

    private void updateButtonStatus(int position) {
        switch (position) {
            case 0:
                QMUIRoundButtonDrawable buttonDrawable1 = (QMUIRoundButtonDrawable) allEvaluate.getBackground();
                buttonDrawable1.setBgData(getResources().getColorStateList(R.color.colorPrimaryLight));
                allEvaluate.setTextColor(getResources().getColor(R.color.white));
                restartBadEvaluate();
                restartGoodEvaluate();
                restartMiddleEvaluate();
                break;
            case 1:
                QMUIRoundButtonDrawable buttonDrawable2 = (QMUIRoundButtonDrawable) goodEvaluate.getBackground();
                buttonDrawable2.setBgData(getResources().getColorStateList(R.color.colorPrimaryLight));
                goodEvaluate.setTextColor(getResources().getColor(R.color.white));
                restartBadEvaluate();
                restartAllEvaluate();
                restartMiddleEvaluate();
                break;
            case 2:
                QMUIRoundButtonDrawable buttonDrawable3 = (QMUIRoundButtonDrawable) middleEvaluate.getBackground();
                buttonDrawable3.setBgData(getResources().getColorStateList(R.color.colorPrimaryLight));
                middleEvaluate.setTextColor(getResources().getColor(R.color.white));
                restartBadEvaluate();
                restartAllEvaluate();
                restartGoodEvaluate();
                break;
            case 3:
                QMUIRoundButtonDrawable buttonDrawable4 = (QMUIRoundButtonDrawable) badEvaluate.getBackground();
                buttonDrawable4.setBgData(getResources().getColorStateList(R.color.colorPrimaryLight));
                badEvaluate.setTextColor(getResources().getColor(R.color.white));
                restartGoodEvaluate();
                restartAllEvaluate();
                restartMiddleEvaluate();
                break;
            default:
                break;
        }
    }

    private void restartAllEvaluate() {
        QMUIRoundButtonDrawable buttonDrawable = (QMUIRoundButtonDrawable) allEvaluate.getBackground();
        buttonDrawable.setBgData(getResources().getColorStateList(R.color.stroke_recycler_divider_color));
        allEvaluate.setTextColor(getResources().getColor(R.color.text_gray3));
    }

    private void restartGoodEvaluate() {
        QMUIRoundButtonDrawable buttonDrawable = (QMUIRoundButtonDrawable) goodEvaluate.getBackground();
        buttonDrawable.setBgData(getResources().getColorStateList(R.color.stroke_recycler_divider_color));
        goodEvaluate.setTextColor(getResources().getColor(R.color.text_gray3));
    }

    private void restartMiddleEvaluate() {
        QMUIRoundButtonDrawable buttonDrawable = (QMUIRoundButtonDrawable) middleEvaluate.getBackground();
        buttonDrawable.setBgData(getResources().getColorStateList(R.color.stroke_recycler_divider_color));
        middleEvaluate.setTextColor(getResources().getColor(R.color.text_gray3));
    }

    private void restartBadEvaluate() {
        QMUIRoundButtonDrawable buttonDrawable = (QMUIRoundButtonDrawable) badEvaluate.getBackground();
        buttonDrawable.setBgData(getResources().getColorStateList(R.color.stroke_recycler_divider_color));
        badEvaluate.setTextColor(getResources().getColor(R.color.text_gray3));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEvent(ProductModel listBean) {
        if (listBean == null) {
            IToast.showShort("异常，请重新进入");
            getActivity().finish();
        }
        getP().loadData(TextUtils.isEmpty(listBean.getProductId()) ? listBean.getId() : listBean.getProductId());
        allEvaluateFragment = EvaluateContentFragment.newInstance(TextUtils.isEmpty(listBean.getProductId()) ? listBean.getId() : listBean.getProductId(), "");
        goodEvaluateFragment = EvaluateContentFragment.newInstance(TextUtils.isEmpty(listBean.getProductId()) ? listBean.getId() : listBean.getProductId(), "0");
        middleEvaluateFragment = EvaluateContentFragment.newInstance(TextUtils.isEmpty(listBean.getProductId()) ? listBean.getId() : listBean.getProductId(), "1");
        badEvaluateFragment = EvaluateContentFragment.newInstance(TextUtils.isEmpty(listBean.getProductId()) ? listBean.getId() : listBean.getProductId(), "2");

        MainViewPageFragmentAdapter viewPageFragmentAdapter = new MainViewPageFragmentAdapter(getChildFragmentManager());
        viewPageFragmentAdapter.addFragment(allEvaluateFragment);
        viewPageFragmentAdapter.addFragment(goodEvaluateFragment);
        viewPageFragmentAdapter.addFragment(middleEvaluateFragment);
        viewPageFragmentAdapter.addFragment(badEvaluateFragment);
        viewPageEvaluate.setOffscreenPageLimit(5);
        viewPageEvaluate.setAdapter(viewPageFragmentAdapter);
        viewPageEvaluate.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateButtonStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_evaluate;
    }

    @Override
    public EvaluatePresent newP() {
        return new EvaluatePresent();
    }


    @OnClick({R.id.all_evaluate, R.id.good_evaluate, R.id.middle_evaluate, R.id.bad_evaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_evaluate:
                viewPageEvaluate.setCurrentItem(0, true);
                break;
            case R.id.good_evaluate:
                viewPageEvaluate.setCurrentItem(1, true);
                break;
            case R.id.middle_evaluate:
                viewPageEvaluate.setCurrentItem(2, true);
                break;
            case R.id.bad_evaluate:
                viewPageEvaluate.setCurrentItem(3, true);
                break;
            default:
                break;
        }
    }

    public void updateNum(EvaluateModel evaluateModel) {
        EvaluateModel.CountBean countBean = evaluateModel.getCount();
        allEvaluate.setText("全部评价" + countBean.getScoreTotal());
        goodEvaluate.setText("好评" + countBean.getScoreTotalGood());
        middleEvaluate.setText("中评" + countBean.getScoreTotalMid());
        badEvaluate.setText("差评" + countBean.getScoreTotalLower());
    }
}
