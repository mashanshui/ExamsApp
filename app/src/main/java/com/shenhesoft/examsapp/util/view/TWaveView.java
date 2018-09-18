package com.shenhesoft.examsapp.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：
 * 创作日期：2017/12/29.
 * 描述：一个贝塞尔曲线波动的效果View，无交互。
 * <p>
 * 源：https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247485954&idx=1&sn=7183334de913cb70e15
 * b40fa276af03b&chksm=96cdab4fa1ba2259d8f835861d2b55c669fbbb4ffda81f4fa1db48b630b67f0653baade80f1f
 * &mpshare=1&scene=23&srcid=0726CGlhy57ZpSwWC2yMn0U2#rd
 */

public class TWaveView extends View {

    private Path mAbovePath, mBelowWavePath;
    private Paint mAboveWavePaint, mBelowWavePaint;
    private DrawFilter mDrawFilter;
    private float q;
    private OnWaveAnimationListener mWaveAnimationListener;

    public TWaveView(Context context) {
        super(context);
    }

    public TWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化路径
        mAbovePath = new Path();
        mBelowWavePath = new Path();
        //初始化画笔
        mAboveWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAboveWavePaint.setAntiAlias(true);
        mAboveWavePaint.setStyle(Paint.Style.FILL);
        mAboveWavePaint.setColor(Color.WHITE);

        mBelowWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBelowWavePaint.setAntiAlias(true);
        mBelowWavePaint.setStyle(Paint.Style.FILL);
        mBelowWavePaint.setColor(Color.WHITE);
        mBelowWavePaint.setAlpha(80);

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        mAbovePath.reset();
        mBelowWavePath.reset();
        q -= 0.1f;
        float y, y2;
        double w = 2 * Math.PI / getWidth();
        mAbovePath.moveTo(getLeft(), getBottom());
        mBelowWavePath.moveTo(getLeft(), getBottom());
        for (float x = 0; x <= getWidth(); x += 20) {
            /**
             y =Asin(wx+q)+k
             A-振幅越大，波形在y轴上最大与最小值的差值越大
             w-角速度，控制正弦周期(单位角度内震动的次数)
             q-初相，反映在坐标系上则为图像的左右移动。这里通过不断改变q,达到波浪移动效果
             k 偏距，反映在坐标系上则为图像的上移或下移。
             */

            y = (float) (12 * Math.cos(w * x + q) + 12);
            y2 = (float) (12 * Math.sin(w * x + q));
            mAbovePath.lineTo(x, y);
            mBelowWavePath.lineTo(x, y2);

            //回调把y坐标的值传出去(在activity有控件需要一起摆动时使用)
            // mWaveAnimationListener.onWaveAnimation(y);
        }
        mAbovePath.lineTo(getRight(), getBottom());
        mBelowWavePath.lineTo(getRight(), getBottom());
        canvas.drawPath(mAbovePath, mAboveWavePaint);
        canvas.drawPath(mBelowWavePath, mBelowWavePaint);

        //等待20ms后 再次Draw()
        postInvalidateDelayed(20);
    }

    public void setOnWaveAnimationListener(OnWaveAnimationListener listener) {
        this.mWaveAnimationListener = listener;
    }

    public interface OnWaveAnimationListener {
        void onWaveAnimation(float y);
    }
}
