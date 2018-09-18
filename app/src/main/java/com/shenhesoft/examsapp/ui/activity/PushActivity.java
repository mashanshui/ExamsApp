package com.shenhesoft.examsapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.util.browser.X5WebView;
import com.tencent.smtt.sdk.WebSettings;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.net.HttpConstant;

/**
 * @author mashanshui
 * @date 2018/5/26
 * @desc 收到全局推送后跳转到这个界面加载网页（全局推送只有加载网页）
 */
public class PushActivity extends XActivity {
    private static final String TAG = "PushActivity";
    @BindView(R.id.fab_back)
    FloatingActionButton fabBack;
    private X5WebView webView;
    private String url;

    @Override
    public void initData(Bundle savedInstanceState) {
        webView = findViewById(R.id.webView);
        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        String murl = getIntent().getStringExtra("data");
        murl = HttpConstant.BASE_URL + murl;
        if (!TextUtils.isEmpty(murl)) {
            url = murl;
        }
        webView.loadUrl(url);

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public int getLayoutId() {
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        QMUIStatusBarHelper.translucent(context);
        return R.layout.activity_push;
    }

    @Override
    public Object newP() {
        return null;
    }

    /**
     * 返回键监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        //释放资源
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
