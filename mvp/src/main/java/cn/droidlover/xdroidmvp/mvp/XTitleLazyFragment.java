package cn.droidlover.xdroidmvp.mvp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.R;
import cn.droidlover.xdroidmvp.XDroidConf;
import cn.droidlover.xdroidmvp.event.BusProvider;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * @author mashanshui
 * @date 2018/5/9
 * @desc TODO
 */
public abstract class XTitleLazyFragment<P extends IPresent>
        extends LazyFragment implements IView<P>  {
    public String TAG="";
    private VDelegate vDelegate;
    private P p;

    private RxPermissions rxPermissions;
    private Unbinder unbinder;
    private QMUITopBar mTopBar;

    private FrameLayout mContent;//主布局

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        TAG = getClass().getName();
        if (getLayoutId() > 0) {
            setContentView(R.layout.base_title_fragment);
            QMUIStatusBarHelper.translucent(getActivity());
            //将activity的layout放入content中
            View view = getLayoutInflater().inflate(getLayoutId(), null);
            mTopBar = context.findViewById(R.id.title_bar);
            mTopBar.setBackgroundColor(Color.parseColor("#9FD661"));
            mContent = context.findViewById(R.id.flt_context);
            mContent.addView(view);
            bindUI(getRealRootView());
        }
        if (useEventBus()) {
            BusProvider.getBus().register(this);
        }
        bindEvent();
        initData(savedInstanceState);
        initTitle();
    }

    protected abstract void initTitle();
    public void setTitle(String title) {
        mTopBar.setTitle(title);
    }

    public void setTitleBackgroundColor(String colorRGB) {
        mTopBar.setBackgroundColor(Color.parseColor(colorRGB));
    }

    public void setBackAction() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setRightImageButton(int resourcesId) {
        mTopBar.addRightImageButton(resourcesId,R.id.topbar_right_change_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setRightText(int resourcesId) {
        mTopBar.addRightTextButton(resourcesId, R.id.topbar_right_change_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
            BusProvider.getBus().unregister(this);
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

}
