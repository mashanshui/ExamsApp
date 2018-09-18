package com.shenhesoft.examsapp.ui.activity.user;

import android.os.Bundle;

import com.shenhesoft.examsapp.R;

import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc 关于
 */
public class AboutActivity extends XTitleActivity {


    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("关于");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        SpannableStringBuilder span = new SpannableStringBuilder("缩进"+"暂无");
//        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
//                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        tvMessage.setText(span);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public Object newP() {
        return null;
    }
}
