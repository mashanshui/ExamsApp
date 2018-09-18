package cn.droidlover.xdroidmvp.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.message.UmengNotifyClickActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author mashanshui
 * @date 2018/5/4
 * @desc 友盟推送点击后启动页面的BaseActivity
 */
public abstract class XUmengActivity<P extends IPresent> extends UmengNotifyClickActivity implements IView<P> {

    private VDelegate vDelegate;
    private P p;
    protected Activity context;

    private RxPermissions rxPermissions;

    private Unbinder unbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        ActivityCollector.addActivity(this);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(null);
        }
        initData(savedInstanceState);
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
