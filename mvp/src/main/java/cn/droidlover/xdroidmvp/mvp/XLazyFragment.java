package cn.droidlover.xdroidmvp.mvp;

import android.os.Bundle;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.event.Event;
import cn.droidlover.xdroidmvp.event.EventBusUtils;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by wanglei on 2017/1/26.
 */

public abstract class XLazyFragment<P extends IPresent>
        extends LazyFragment implements IView<P> {
    public String TAG="";
    private VDelegate vDelegate;
    private P p;

    private RxPermissions rxPermissions;
    private Unbinder unbinder;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        TAG = getClass().getName();
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(getRealRootView());
        }
        if (useEventBus()) {
            EventBusUtils.register(this);
        }
        bindEvent();
        initData(savedInstanceState);
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = KnifeKit.bind(this, rootView);
    }

    @Override
    public void bindEvent() {

    }


    public VDelegate getvDelegate() {
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
    protected void onDestoryLazy() {
        super.onDestoryLazy();
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


    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(getActivity());
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }


    @Override
    public int getOptionsMenuId() {
        return 0;
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



}
