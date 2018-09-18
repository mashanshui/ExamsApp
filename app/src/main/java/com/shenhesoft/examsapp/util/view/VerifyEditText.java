package com.shenhesoft.examsapp.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.shenhesoft.examsapp.R;

import cn.iwgang.countdownview.CountdownView;

/**
 * @author mashanshui
 * @date 2018/5/3
 * @desc TODO
 */
public class VerifyEditText extends android.support.v7.widget.AppCompatEditText {
    private static final String TAG = "VerifyEditText";
    private StateButton mButton;
    private int mButtonHeightPadding = 16;
    private int mButtonWeightPadding = 16;
    private CountdownView countdownView = null;

    public VerifyEditText(Context context) {
        super(context);
        init();
    }

    public VerifyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerifyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        mButton = (StateButton) LayoutInflater.from(context).inflate(R.layout.tag_verify_edittext, null);
        mButton = new StateButton(getContext());
        mButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mButton.setNormalBackgroundColor(Color.parseColor("#800A84C7"));
        mButton.setPadding(3, 3, 3, 3);
        mButton.setRound(true);
        mButton.setTextColor(getResources().getColor(R.color.login_btn_text_color));
        mButton.setText("获取验证码");
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyClickListener.onVerifyClick();
            }
        });
        countdownView = new CountdownView(getContext());
        countdownView.setOnCountdownIntervalListener(1000, new CountdownView.OnCountdownIntervalListener() {
            @Override
            public void onInterval(CountdownView cv, long remainTime) {
                int time = (int) (remainTime / 1000 + 0.5);
                mButton.setText(time + "秒后重新发送");
                requestLayout();
            }
        });
        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                showVerify();
            }
        });
    }

    public void showCountdown() {
        mButton.setTextColor(getResources().getColor(R.color.text_gray2));
        mButton.setEnabled(false);
        countdownView.start(60000);
    }

    public void showVerify() {
        stopCountdown();
        mButton.setTextColor(Color.WHITE);
        mButton.setEnabled(true);
        mButton.setText("获取验证码");
        requestLayout();
    }

    public void stopCountdown() {
        countdownView.stop();
    }

    public void restartCountdown() {
        countdownView.restart();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mButton.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() - mButtonHeightPadding * 2, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mButton.layout(0, 0, mButton.getMeasuredWidth(), mButton.getMeasuredHeight());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(getMeasuredWidth() - (mButton.getMeasuredWidth() + mButtonWeightPadding), mButtonHeightPadding);
        mButton.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getY() > mButtonHeightPadding && event.getY() < getHeight() - mButtonHeightPadding &&
                event.getX() > getWidth() - mButtonWeightPadding - mButton.getMeasuredWidth() &&
                event.getX() < getWidth() - mButtonWeightPadding) {
            if (mButton.isEnabled() && event.getAction() == MotionEvent.ACTION_UP) {
                return mButton.callOnClick();
            }
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    private VerifyClickListener verifyClickListener;

    public void setVerifyClickListener(VerifyClickListener verifyClickListener) {
        this.verifyClickListener = verifyClickListener;
    }

    public interface VerifyClickListener {
        void onVerifyClick();
    }
}
