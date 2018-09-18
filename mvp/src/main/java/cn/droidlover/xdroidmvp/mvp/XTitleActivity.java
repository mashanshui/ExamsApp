package cn.droidlover.xdroidmvp.mvp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.R;
import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author mashanshui
 * @date 2018/4/10
 * @desc TODO
 */
public abstract class XTitleActivity<P extends IPresent> extends RxAppCompatActivity implements IView<P> {
    public String TAG = "";
    private VDelegate vDelegate;
    private P p;
    protected Activity context;

    private RxPermissions rxPermissions;

    private Unbinder unbinder;

    private QMUITopBar mTopBar;

    private FrameLayout mContent;//主布局


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.addActivity(this);
        context = this;
        TAG = context.getLocalClassName();
        if (getLayoutId() > 0) {
            QMUIStatusBarHelper.translucent(this);
            QMUIStatusBarHelper.setStatusBarLightMode(context);
            setContentView(R.layout.base_title_activity);
            //将activity的layout放入content中
            View view = getLayoutInflater().inflate(getLayoutId(), null);
            mTopBar = findViewById(R.id.title_bar);
            mTopBar.setBackgroundDividerEnabled(true);
            mContent = findViewById(R.id.flt_context);
            mContent.addView(view);
            bindUI(null);
            bindEvent();
        }
        initTitle();
        initData(savedInstanceState);
    }


    protected abstract void initTitle();

    public void setTitle(String title) {
        mTopBar.setTitle(title);
    }

    public void setTitleBackgroundColor(int color) {
        mTopBar.setBackgroundColor(color);
    }

    public void setTitleBackgroundDrawable(int drawableId) {
        mTopBar.setBackground(ContextCompat.getDrawable(context, drawableId));
    }

    public void setBackAction() {
        QMUIAlphaImageButton imageButton = mTopBar.addLeftImageButton(R.drawable.topbar_left_back, R.id.topbar_left_change_button);
        imageButton.setChangeAlphaWhenPress(true);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setRightView(View view) {
        //指定view的宽为130dp，防止view覆盖其他view
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mTopBar.addRightView(view, R.id.topbar_right_switch_button, layoutParams);
    }

    public int dp2px(float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public QMUIAlphaImageButton setRightImageButton(int resourcesId) {
        QMUIAlphaImageButton imageButton = mTopBar.addRightImageButton(resourcesId, R.id.topbar_right_change_button);
        imageButton.setChangeAlphaWhenPress(true);
        return imageButton;
    }

    public QMUIAlphaImageButton setRightImageButton2(int resourcesId) {
        QMUIAlphaImageButton imageButton = mTopBar.addRightImageButton(resourcesId, R.id.topbar_right_change_button2);
        imageButton.setChangeAlphaWhenPress(true);
        return imageButton;
    }

    public Button setRightText(String buttonText) {
        return mTopBar.addRightTextButton(buttonText, R.id.topbar_right_change_text);
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = KnifeKit.bind(this);
    }

    protected VDelegate getvDelegate() {
        if (vDelegate == null) {
            vDelegate = VDelegateBase.create(context);
        }
        return vDelegate;
    }

    protected P getP() {
        if (p == null) {
            p = newP();
            if (p != null) {
                p.attachV(this);
            }
        }
        return p;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus()) {
            EventBusUtils.register(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getvDelegate().resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        getvDelegate().pause();
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    public void receiveEvent(Event event) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    public void receiveStickyEvent(Event event) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        if (useEventBus()) {
            EventBusUtils.unregister(this);
        }
        if (getP() != null) {
            getP().detachV();
        }
        getvDelegate().destory();
        p = null;
        vDelegate = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getOptionsMenuId() > 0) {
            getMenuInflater().inflate(getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public void bindEvent() {

    }
}

