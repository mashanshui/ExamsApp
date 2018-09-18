package com.shenhesoft.examsapp.ui.activity.dohomework;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.AnswerPagerAdapter;
import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.network.model.FinishAnswerModel;
import com.shenhesoft.examsapp.present.AnswerPresent;
import com.shenhesoft.examsapp.ui.fragment.dohomework.AnswerFragment;
import com.shenhesoft.examsapp.util.UltimateBar;
import com.shenhesoft.examsapp.util.view.SelectQuestionDialog;
import com.shenhesoft.examsapp.view.AnswerView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import cn.droidlover.xdroidmvp.net.HttpConstant;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/11
 * @desc 做题目
 */
public class AnswerActivity extends XTitleActivity<AnswerPresent> implements AnswerView {
    @BindView(R.id.cb_collect)
    CheckBox cbCollect;
    @BindView(R.id.btn_operate)
    QMUIRoundButton btnOperate;
    @BindView(R.id.tv_done_subject)
    TextView tvDoneSubject;
    @BindView(R.id.tv_total_subject)
    TextView tvTotalSubject;
    public int start = 0;
    public int length = 20;
    //题目是否作对
    public static final int Right = 0;
    public static final int Error = 1;

    //题目答案
    public static final int RightOption = 1;
    public static final int ErrorOption = 2;

    //题目是否做了
    public static final int Done = 0;
    public static final int NotDone = 1;
    @BindView(R.id.btn_back_subject)
    SuperButton btnBackSubject;
    @BindView(R.id.btn_next_subject)
    SuperButton btnNextSubject;
    @BindView(R.id.cl_back_next_layout)
    ConstraintLayout clBackNextLayout;
    @BindView(R.id.iv_total_question)
    ImageView ivTotalQuestion;
    /**
     * 做题类型
     */
    private int answerType;
    /**
     * 产品id,用于获取信息
     */
    private String productId;
    /**
     * 题目分析是所有题目分析还是错题分析
     */
    private Integer WrongOrAll;

    /**
     * 答题策略
     */
    private String answerPolicyName;

    private long startTime;

    /**
     * 开始做题后返回的做题id，用于下一题接口和提交数据时使用
     */
    private String answerId;
    /**
     * 题库的题目总数量
     */
    private int totalAnswerNum;

    /**
     * 开始做题时的位置（以前做过的数量）
     */
    private int startAnswerNum;

    /**
     * 做题的最后位置
     */
    private int doneSize;

    @BindView(R.id.viewPager)
    QMUIViewPager viewPager;
    @BindView(R.id.cl_main_layout)
    ConstraintLayout clMainLayout;
    private Switch switchMode;
    private List<Class> fragments;
    private int themeMode;
    public static final int DayMode = 0;
    public static final int NightMode = 1;
    private AnswerPagerAdapter pagerAdapter;
    private List<AnswerModel> answerModels;

    //友盟分享
    private UMShareListener mShareListener;
    private ShareAction mShareAction;
    private ShareBoardConfig config;
    //用户选择的分享类型
    private SHARE_MEDIA shareMedia;

    @Override
    protected void initTitle() {
        //答题类型，做作业还是分析
        answerType = getIntent().getIntExtra(AppConstant.AnswerType, AppConstant.DoHomeWork);
        //广泛的id，不同的页面代表的意思不同
        productId = getIntent().getStringExtra("productId");
        //做题完成后选择查看题目分析，全部题目或者错题，HomeWorkStatisticsActivity才有,其他页面跳转时为空
        WrongOrAll = getIntent().getIntExtra("WrongOrAll", 0);
        //答题策略，StartDoSubjectActivity跳转时才有，其他页面跳转时为空
        answerPolicyName = getIntent().getStringExtra("answerPolicyName");
        //开始做题的位置，TestPagerActivity传过来的，按试卷做题的时候才会有
        startAnswerNum = getIntent().getIntExtra("startAnswerNum", 0);
        setBackAction();
        QMUIAlphaImageButton imageButton1 = setRightImageButton(R.drawable.share);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerModels.isEmpty()) {
                    IToast.showShort("没有题目");
                    return;
                }
                //开启分享面板
                mShareAction.open(config);
            }
        });
        if (TextUtils.equals(answerPolicyName, AppConstant.AnswerPolicy4)) {
            QMUIAlphaImageButton imageButton2 = setRightImageButton2(R.drawable.delete);
            imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answerModels.isEmpty()) {
                        IToast.showShort("没有题目");
                        return;
                    }
                    getP().removeQuestion(answerModels.get(viewPager.getCurrentItem()).getId());
                }
            });
        }

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.switch_button, null);
        switchMode = view.findViewById(R.id.sc_eye_mode);
        if (themeMode == DayMode) {
            switchMode.setChecked(false);
        } else {
            switchMode.setChecked(true);
        }
        switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showAnimation();
                if (isChecked) {
                    SharedPref.getInstance(context).putInt(AppConstant.ThemeMode, NightMode);
                    setTheme(R.style.NightTheme);
                } else {
                    SharedPref.getInstance(context).putInt(AppConstant.ThemeMode, DayMode);
                    setTheme(R.style.AppTheme);
                }
                EventBusUtils.sendEvent(new Event(EventBusUtils.EventCode.THEME_MODE, themeMode));
                refreshUI();
            }
        });
        setRightView(view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setResult(RESULT_OK);
        //分享面板样式配置类初始化，在刷新界面之前
        config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_NONE);
        refreshUI();
        //友盟分享
        mShareListener = new MyShareListener();
        mShareAction = new ShareAction(context)
                .withText("hello")
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(shareBoardlistener);

        fragments = new ArrayList<>();
        answerModels = new ArrayList<>();
        startTime = System.currentTimeMillis();
        pagerAdapter = new AnswerPagerAdapter(getSupportFragmentManager(), fragments, answerType, productId, answerModels);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setSwipeable(false);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnOperate.setChangeAlphaWhenPress(true);
        if (answerType == AppConstant.DoHomeWork) {
            btnOperate.setVisibility(View.VISIBLE);
            clBackNextLayout.setVisibility(View.GONE);
        } else {
            btnOperate.setVisibility(View.GONE);
            clBackNextLayout.setVisibility(View.VISIBLE);
        }
        cbCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCollect.isChecked()) {
                    //收藏题目
                    getP().collectAnswer(answerModels.get(viewPager.getCurrentItem()).getId());
                } else {
                    //取消收藏
                    getP().cancelCollect(answerModels.get(viewPager.getCurrentItem()).getId());
                }
            }
        });
        cbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        getP().loadData(true, start, length, productId, answerType, WrongOrAll, answerPolicyName);
    }

    @Override
    public int getLayoutId() {
        themeMode = SharedPref.getInstance(context).getInt(AppConstant.ThemeMode, DayMode);
        if (themeMode == DayMode) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.NightTheme);
        }
        return R.layout.activity_do_subject;
    }

    @Override
    public AnswerPresent newP() {
        return new AnswerPresent();
    }

    @OnClick({R.id.btn_operate, R.id.btn_back_subject, R.id.btn_next_subject, R.id.iv_total_question})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_subject:
                backAnswer();
                break;
            case R.id.btn_next_subject:
                //下一题
                nextAnswer();
                break;
            case R.id.btn_operate:
                if (answerType == AppConstant.DoHomeWork && !answerModels.isEmpty()) {
                    //统计成绩
                    showConfirmDialog(false);
                }
                break;
            case R.id.iv_total_question:
                if (answerType == AppConstant.DoHomeWork) {
                    showTotalQuestionDialog();
                }
                break;
            default:
                break;
        }
    }

    private void showTotalQuestionDialog() {
        SelectQuestionDialog.newInstance(doneSize + 1)
                .setOnItemClickListener(new SelectQuestionDialog.OnItemClickListener() {
                    @Override
                    public void onItemClick(int num) {
                        viewPager.setCurrentItem(num-1);
                        updateSubjectNum(viewPager.getCurrentItem());
                        clearStatusData(viewPager.getCurrentItem());
                    }
                })
                .setShowBottom(true)
                .setHeight(300)
                .show(getSupportFragmentManager());
    }

    /**
     * 返回时必须先统计成绩
     */
    @Override
    public void onBackPressed() {
        if (answerType == AppConstant.DoHomeWork && !answerModels.isEmpty()) {
            showConfirmDialog(false);
            return;
        }
        super.onBackPressed();
    }

    private void showConfirmDialog(final boolean isLastQuestion) {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_confirm_layout)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "统计成绩");
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getP().nextSubject(true, answerId, answerModels.get(viewPager.getCurrentItem()).getId(), isRight(), isFinish());
                                baseNiceDialog.dismiss();
                            }
                        });
                        viewHolder.setOnClickListener(R.id.tv_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(300)
                .setHeight(150)
                .show(getSupportFragmentManager());
    }

    @Override
    public void updateData(List<AnswerModel> allResults) {
        if (!answerModels.isEmpty()) {
            answerModels.clear();
        }
        answerModels.addAll(allResults);
        for (int i = 0; i < allResults.size(); i++) {
            fragments.add(AnswerFragment.class);
        }
        updateSubjectNum(0);
        clearStatusData(viewPager.getCurrentItem());
        start = start + length;
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateAddData(List<AnswerModel> allResults) {
        answerModels.addAll(allResults);
        for (int i = 0; i < allResults.size(); i++) {
            fragments.add(AnswerFragment.class);
        }
        start = start + length;
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void nextAnswer() {
        int currentPage = viewPager.getCurrentItem() + startAnswerNum;
        if (currentPage + 1 == totalAnswerNum) {
            IToast.showShort("已经是最后一题了");
            if (answerType == AppConstant.DoHomeWork && !answerModels.isEmpty()) {
                showConfirmDialog(true);
            }
            return;
        }
        if (viewPager.getCurrentItem() + 1 == fragments.size()) {
            getP().loadData(false, start, length, productId, answerType, WrongOrAll, answerPolicyName);
            return;
        }
        if (answerType == AppConstant.DoHomeWork) {
            getP().nextSubject(false, answerId, answerModels.get(viewPager.getCurrentItem()).getId(), isRight(), isFinish());
        }
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        if (doneSize < viewPager.getCurrentItem()) {
            doneSize = viewPager.getCurrentItem();
        }
        updateSubjectNum(viewPager.getCurrentItem());
        clearStatusData(viewPager.getCurrentItem());
    }


    private void backAnswer() {
        int currentPage = viewPager.getCurrentItem();
        if (currentPage == 0) {
            IToast.showShort("已经是第一题了");
            return;
        }
        viewPager.setCurrentItem(currentPage - 1);
        updateSubjectNum(viewPager.getCurrentItem());
        clearStatusData(viewPager.getCurrentItem());
    }

    private void clearStatusData(int currentPage) {
        if (currentPage == fragments.size()) {
            return;
        }
        Integer collectStatus = answerModels.get(currentPage).getCollectStatus();
        if (collectStatus == null || collectStatus == 0) {
            cbCollect.setChecked(false);
        } else {
            cbCollect.setChecked(true);
        }
    }

    @Override
    public void nextSuccess() {
        getP().finishAnswer(startTime);
    }

    @Override
    public String getAnswerPolicyName() {
        return answerPolicyName;
    }

    @Override
    public int getDoneSize() {
        return doneSize;
    }

    @Override
    public int getCurrentPage() {
        return viewPager.getCurrentItem();
    }

    @Override
    public void updateSubjectNum(int currentPage) {
        currentPage++;
        tvDoneSubject.setText(String.valueOf(startAnswerNum + currentPage));
        tvTotalSubject.setText(String.valueOf(totalAnswerNum));
    }

    @Override
    public void setAnswerId(String id) {
        answerId = id;
    }

    private int isRight() {
        List<AnswerModel.OptionsBean> optionsBeanList = answerModels.get(viewPager.getCurrentItem()).getOptions();
        for (int i = 0; i < optionsBeanList.size(); i++) {
            AnswerModel.OptionsBean optionsBean = optionsBeanList.get(i);
            //如果没有选择的选项是对的,则说明答题错误
            if (optionsBean.getOptionSelect() == AppConstant.NotSelect && optionsBean.getOptionAnswered() == RightOption) {
                return Error;
            }
            //如果选择的选项是错的,则说明答题错误
            if (optionsBean.getOptionSelect() == AppConstant.Select && optionsBean.getOptionAnswered() == ErrorOption) {
                return Error;
            }
        }
        return Right;
    }

    private int isFinish() {
//        List<AnswerModel.OptionsBean> optionsBeanList = answerModels.get(viewPager.getCurrentItem()).getOptions();
//        for (int i = 0; i < optionsBeanList.size(); i++) {
//            AnswerModel.OptionsBean optionsBean = optionsBeanList.get(i);
//            if (optionsBean.getOptionSelect() == AppConstant.Select) {
//                return Done;
//            }
//        }
        //修改为无论是否做，都是已做
        return Done;
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public String getAnswerId() {
        return answerId == null ? "" : answerId;
    }

    @Override
    public void setTotalAnswerNum(int totalAnswerNum) {
        this.totalAnswerNum = totalAnswerNum;
    }

    @Override
    public int getTotalAnswerNum() {
        return totalAnswerNum;
    }

    @Override
    public int getStartAnswerNum() {
        return startAnswerNum;
    }

    @Override
    public void toStatisticsActivity(FinishAnswerModel model) {
        Router.newIntent(context).to(HomeWorkStatisticsActivity.class)
                .putSerializable("data", model)
                .launch();
        finish();
    }

    @Override
    public void setCollectCheck(boolean isCheck) {
        cbCollect.setChecked(isCheck);
    }

    @Override
    public void receiveEvent(Event event) {
        if (event.getCode() == EventBusUtils.EventCode.NEXT_ANSWER) {
            nextAnswer();
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 分享面板的监听
     */
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            shareMedia = share_media;
            String url = HttpConstant.BASE_URL + "api/shareQuestion?id=" + answerModels.get(viewPager.getCurrentItem()).getId();
            UMWeb web = new UMWeb(url);
            web.setTitle("分享题目");
            web.setDescription("点击查看题目详情");
            web.setThumb(new UMImage(context, R.mipmap.logo));
            new ShareAction(context).withMedia(web)
                    .setPlatform(shareMedia)
                    .setCallback(mShareListener)
                    .share();
        }
    };


    /**
     * 分享结果的监听
     */
    public class MyShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            IToast.showShort("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            IToast.showShort("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            IToast.showShort("分享取消");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }

    private void refreshUI() {
        //背景色
        TypedValue background = new TypedValue();
        //字体颜色
        TypedValue textColor = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.clockBackground, background, true);
        theme.resolveAttribute(R.attr.clockTextColor, textColor, true);
        clMainLayout.setBackgroundResource(background.resourceId);
        setTitleBackgroundColor(getResources().getColor(background.resourceId));
        refreshStatusBar();
        switchMode.setBackgroundColor(getResources().getColor(background.resourceId));
        //设置分享面板的颜色
        config.setShareboardBackgroundColor(getResources().getColor(background.resourceId));
        config.setCancelButtonBackground(getResources().getColor(background.resourceId));
    }

    /**
     * 刷新 StatusBar
     */
    private void refreshStatusBar() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.clockBackground, typedValue, true);
        UltimateBar ultimateBar = new UltimateBar(context);
        ultimateBar.setColorBar(getResources().getColor(typedValue.resourceId));
    }

    /**
     * 展示一个切换动画
     */
    private void showAnimation() {
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(500);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}

