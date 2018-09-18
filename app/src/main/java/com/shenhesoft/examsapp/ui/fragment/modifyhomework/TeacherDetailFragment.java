package com.shenhesoft.examsapp.ui.fragment.modifyhomework;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bumptech.glide.Glide;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.present.TeacherDetailPresent;
import com.shenhesoft.examsapp.ui.activity.PayActivity;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.InteractiveActivity;

import org.greenrobot.eventbus.EventBus;
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
 * @date 2018/5/17
 * @desc 老师详情
 */
public class TeacherDetailFragment extends XLazyFragment<TeacherDetailPresent> {
    public static final int RequestCode = 100;
    private static final String ARG_PARAM1 = "PRODUCT_ID";
    private static final String ARG_PARAM2 = "ISBuy";
    @BindView(R.id.btn_buy)
    QMUIRoundButton btnBuy;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tv_subject)
    TextView tvSubject;
    @BindView(R.id.tv_teacher)
    TextView tvTeacher;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_buy_num)
    TextView tvBuyNum;
    @BindView(R.id.rb_rate1)
    AppCompatRatingBar rbRate1;
    @BindView(R.id.ll_already_buy)
    LinearLayout llAlreadyBuy;
    @BindView(R.id.rb_rate2)
    AppCompatRatingBar rbRate2;
    @BindView(R.id.ll_not_buy)
    LinearLayout llNotBuy;
    @BindView(R.id.tv_evaluate_write)
    SuperTextView tvEvaluateWrite;
    @BindView(R.id.tv_type)
    SuperTextView tvType;
    @BindView(R.id.tv_source)
    SuperTextView tvSource;
    @BindView(R.id.tv_date)
    SuperTextView tvDate;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_orderCode)
    TextView tvOrderCode;
    @BindView(R.id.ll_orderCode)
    LinearLayout llOrderCode;

    private String misBuy;
    private ProductModel productModel;
    private ProductModel teacherDetail;
    /**
     * 购买成功后的orderid
     */
    private String orderId;

    /**
     * 是否是购买之后返回
     */
    private boolean isBuyBack = false;

    /**
     * 是否购买
     */
    private boolean isBuy = false;

    public TeacherDetailFragment() {
        // Required empty public constructor
    }

    public static TeacherDetailFragment newInstance(String isBuy) {
        TeacherDetailFragment fragment = new TeacherDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, isBuy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            misBuy = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        btnBuy.setChangeAlphaWhenPress(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_teacher_detail;
    }

    @Override
    public TeacherDetailPresent newP() {
        return new TeacherDetailPresent();
    }

    @OnClick({R.id.btn_buy, R.id.tv_evaluate_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                if ("已购买".equals(teacherDetail.getStatusOrderName()) || "免费".equals(teacherDetail.getProductFreeName()) || "buy".equals(misBuy)) {
                    uploadWriting();
                } else {
                    Intent intent = new Intent(context, PayActivity.class);
                    intent.putExtra("productDetail", productModel);
                    startActivityForResult(intent, RequestCode);
//                    Router.newIntent(context).to(PayActivity.class)
//                            .requestCode(RequestCode)
//                            .putSerializable("productDetail", teacherDetail)
//                            .launch();
                }
                break;
            case R.id.tv_evaluate_write:
                getP().isWriteEvaluate();
                break;
            default:
                break;
        }
    }


    public void uploadWriting() {
        EventBus.getDefault().postSticky(productModel);
        Router.newIntent(context).to(InteractiveActivity.class).launch();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEvent(ProductModel listBean) {
        if (listBean == null) {
            IToast.showShort("异常，请重新进入");
            getActivity().finish();
        }
        productModel = listBean;
        getP().loadProductData();
    }

    public void updateUI() {
        if ("已购买".equals(teacherDetail.getStatusOrderName()) || "免费".equals(teacherDetail.getProductFreeName()) || "buy".equals(misBuy)) {
            isBuy = true;
            tvEvaluateWrite.setVisibility(View.VISIBLE);
            btnBuy.setText("开始沟通");
            llAlreadyBuy.setVisibility(View.VISIBLE);
            llNotBuy.setVisibility(View.GONE);
            llOrderCode.setVisibility(View.VISIBLE);
            updatePublicUI();
        } else {
            isBuy = false;
            tvEvaluateWrite.setVisibility(View.GONE);
            btnBuy.setText("立即购买");
            llAlreadyBuy.setVisibility(View.GONE);
            llNotBuy.setVisibility(View.VISIBLE);
            llOrderCode.setVisibility(View.GONE);
            updatePublicUI();
        }
    }

    private void updatePublicUI() {
        tvName.setText(teacherDetail.getProductTitle());
        tvIntro.setText(teacherDetail.getProductIntroduction());
        tvTeacher.setText(teacherDetail.getAuthorUserName());
        tvSubject.setText(teacherDetail.getScienceDomainName());
        tvPrice.setText(teacherDetail.getProductPrice());
        tvBuyNum.setText(teacherDetail.getSalNumShow());
        rbRate1.setRating(teacherDetail.getScoreAvgShow());
        rbRate2.setRating(teacherDetail.getScoreAvgShow());
        tvType.setRightString(teacherDetail.getArticleTypeName());
        tvSource.setRightString(teacherDetail.getArticleSource());
        tvDate.setRightString(teacherDetail.getArticleYear());
        tvOrderCode.setText(teacherDetail.getOrderId());
        Glide.with(context).load(HttpConstant.BASE_IP + teacherDetail.getRemark())
                .placeholder(R.drawable.placeholder)
                .into(ivImage);
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

    @Override
    public boolean useEventBus() {
        return true;
    }

    public String getProductId() {
        return TextUtils.isEmpty(productModel.getProductId()) ? productModel.getId() : productModel.getProductId();
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
        if (requestCode == RequestCode && resultCode == RESULT_OK && data != null) {
            getP().loadProductData();
            orderId = data.getStringExtra("orderId");
            productModel.setOrderId(orderId);
            isBuyBack = true;
        }
    }

    public String getOrderId() {
        return productModel.getOrderId();
    }

    public boolean getIsBuyBack() {
        return isBuyBack;
    }

    public boolean getisBuy() {
        return isBuy;
    }


    public void updateData(ProductModel model) {
        teacherDetail = model;
        updateUI();
    }
}
