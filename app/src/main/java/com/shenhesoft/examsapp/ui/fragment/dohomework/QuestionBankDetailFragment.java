package com.shenhesoft.examsapp.ui.fragment.dohomework;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.QuestionBankDetailPresent;
import com.shenhesoft.examsapp.ui.activity.PayActivity;
import com.shenhesoft.examsapp.ui.activity.dohomework.TestPaperActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.net.HttpConstant;
import cn.droidlover.xdroidmvp.router.Router;

import static android.app.Activity.RESULT_OK;

/**
 * @author mashanshui
 * @date 2018/5/15
 * @desc 题库详情
 */
public class QuestionBankDetailFragment extends XLazyFragment<QuestionBankDetailPresent> {
    /**
     * 题目分析
     */
    public static final int LookQuestion = 0;
    /**
     * 做题
     */
    public static final int WritingQuestion = 1;
    public static final int RequestCode = 100;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.btn_buy)
    QMUIRoundButton btnBuy;
//    @BindView(R.id.tv_done_subject)
//    TextView tvDoneSubject;
//    @BindView(R.id.tv_total_subject)
//    TextView tvTotalSubject;
    @BindView(R.id.btn_look_total_subject)
    SuperButton btnLookTotalSubject;
    @BindView(R.id.btn_start_subject)
    SuperButton btnStartSubject;
    @BindView(R.id.courseware_image)
    ImageView coursewareImage;
    @BindView(R.id.tv_courseware_name)
    TextView tvCoursewareName;
    @BindView(R.id.tv_courseware_intro)
    TextView tvCoursewareIntro;
    @BindView(R.id.tv_courseware_subject)
    TextView tvCoursewareSubject;
    @BindView(R.id.tv_courseware_teacher)
    TextView tvCoursewareTeacher;
    @BindView(R.id.tv_courseware_price)
    TextView tvCoursewarePrice;
    @BindView(R.id.tv_courseware_buy_num)
    TextView tvCoursewareBuyNum;
    @BindView(R.id.rb_courseware_rate1)
    AppCompatRatingBar rbCoursewareRate1;
    @BindView(R.id.ll_already_buy)
    LinearLayout llAlreadyBuy;
    @BindView(R.id.rb_courseware_rate2)
    AppCompatRatingBar rbCoursewareRate2;
    @BindView(R.id.ll_not_buy)
    LinearLayout llNotBuy;
    @BindView(R.id.tv_courseware_evaluate_write)
    SuperTextView tvCoursewareEvaluateWrite;
    @BindView(R.id.layout_include_bottom)
    ConstraintLayout clLayoutIncludeBottom;
    @BindView(R.id.tv_orderCode)
    TextView tvOrderCode;
    @BindView(R.id.ll_orderCode)
    LinearLayout llOrderCode;

    private ProductModel productModel;

    private String mParam1;
    private String mParam2;


    public QuestionBankDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuestionBankDetailFragment.
     */
    public static QuestionBankDetailFragment newInstance(String param1, String param2) {
        QuestionBankDetailFragment fragment = new QuestionBankDetailFragment();
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
        btnBuy.setChangeAlphaWhenPress(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_question_bank_detail;
    }

    @Override
    public QuestionBankDetailPresent newP() {
        return new QuestionBankDetailPresent();
    }


    @OnClick({R.id.btn_buy, R.id.btn_look_total_subject, R.id.btn_start_subject, R.id.tv_courseware_evaluate_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra("productDetail", productModel);
                startActivityForResult(intent, RequestCode);
//                Router.newIntent(context).to(PayActivity.class)
//                        .requestCode(RequestCode)
//                        .putSerializable("productDetail", productModel)
//                        .launch();
                break;
            case R.id.btn_look_total_subject:
                Router.newIntent(context).to(TestPaperActivity.class)
                        .putInt("testPagerType", LookQuestion)
                        .launch();
                break;
            case R.id.btn_start_subject:
                Router.newIntent(context).to(TestPaperActivity.class)
                        .putInt("testPagerType", WritingQuestion)
                        .launch();
                break;
            case R.id.tv_courseware_evaluate_write:
                getP().isWriteEvaluate();
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEvent(ProductModel listBean) {
        if (listBean == null) {
            IToast.showShort("异常，请重新进入");
            getActivity().finish();
        }
        productModel = listBean;
        getP().loadData(productModel.getId());
    }

    private void updateUI() {
        if ("已购买".equals(productModel.getStatusOrderName()) || "免费".equals(productModel.getProductFreeName())) {
            tvCoursewareEvaluateWrite.setVisibility(View.VISIBLE);
            clLayoutIncludeBottom.setVisibility(View.VISIBLE);
            btnBuy.setVisibility(View.GONE);
            llAlreadyBuy.setVisibility(View.VISIBLE);
            llNotBuy.setVisibility(View.GONE);
            llOrderCode.setVisibility(View.VISIBLE);
            updatePublicUI();
        } else {
            tvCoursewareEvaluateWrite.setVisibility(View.GONE);
            clLayoutIncludeBottom.setVisibility(View.GONE);
            btnBuy.setVisibility(View.VISIBLE);
            llAlreadyBuy.setVisibility(View.GONE);
            llNotBuy.setVisibility(View.VISIBLE);
            llOrderCode.setVisibility(View.GONE);
            updatePublicUI();
        }
    }

    private void updatePublicUI() {
        tvCoursewareName.setText(productModel.getProductTitle());
        tvCoursewareIntro.setText(productModel.getProductIntroduction());
        tvCoursewareTeacher.setText(productModel.getAuthorUserName());
        tvCoursewareSubject.setText(productModel.getScienceDomainName());
        tvCoursewarePrice.setText(productModel.getProductPrice());
        tvCoursewareBuyNum.setText(productModel.getSalNumShow());
        rbCoursewareRate2.setRating(productModel.getScoreAvgShow());
        rbCoursewareRate1.setRating(productModel.getScoreAvgShow());
        tvOrderCode.setText(productModel.getOrderId());
        Glide.with(context).load(HttpConstant.BASE_IP + productModel.getRemark())
                .placeholder(R.drawable.placeholder)
                .into(coursewareImage);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    private BaseNiceDialog niceDialog;


    public void startEvaluate() {
        initDialog();
    }

    private void initDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_product_evaluate)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(final ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        final RatingBar ratingBar = viewHolder.getView(R.id.ratingBar);
                        final EditText editText = viewHolder.getView(R.id.et_evaluate_content);
                        viewHolder.setOnClickListener(R.id.btn_submit, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                niceDialog = baseNiceDialog;
                                getP().writeEvaluate(editText.getText().toString().trim(), (int) ratingBar.getRating());
                            }
                        });
                        viewHolder.setOnClickListener(R.id.iv_back, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                            }
                        });
                    }
                })
                .setShowBottom(true)
                .setAnimStyle(R.style.EnterExitAnimation)
                .show(getChildFragmentManager());
    }

    public String getProductId() {
        return productModel.getId();
    }

    public void dismissDialog() {
        if (niceDialog != null) {
            niceDialog.dismiss();
            niceDialog = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode && resultCode == RESULT_OK) {
            getP().loadData(productModel.getId());
        }
    }

    public void updateData(ProductModel model) {
        productModel = model;
        updateUI();
    }

}
