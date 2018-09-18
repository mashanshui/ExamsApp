package cn.droidlover.xdroidmvp.net;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.kit.IToast;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @author 马山水
 * @date 2018/3/22
 * @desc TODO
 */

public class HttpObserver<T> implements Observer<T>, LoadingDialogHandler.DialogCancleListener {
    private static final String TAG = "HttpObserver";
    //rxjava2新增接口，简单理解为观察者与观察对象的连接相关操作
    private Disposable disposable;

    private LoadingDialogHandler mProgressDialogHandler;
    private OnNextListener<T> onNextListener;
    private OnCompleteListener onCompleteListener;
    private OnErrorListener OnErrorListener;
    private BGARefreshLayout refreshLayout;
    private boolean isShowDialog;

    public HttpObserver(OnNextListener<T> onNextListener) {
        this(true, onNextListener);
    }

    public HttpObserver(OnNextListener<T> onNextListener, OnCompleteListener onCompleteListener) {
        this(true, onNextListener, onCompleteListener);
    }

    public HttpObserver(OnNextListener<T> onNextListener, OnErrorListener onErrorListener) {
        this(true, onNextListener, onErrorListener);
    }

    public HttpObserver(OnNextListener<T> onNextListener, OnCompleteListener onCompleteListener, OnErrorListener onErrorListener) {
        this(true, onNextListener, onCompleteListener, onErrorListener);
    }

    public HttpObserver(boolean isShowDialog, OnNextListener<T> onNextListener) {
        this.isShowDialog = isShowDialog;
        mProgressDialogHandler = new LoadingDialogHandler(ActivityCollector.getTopActivity(), this);
        this.onNextListener = onNextListener;
    }

    public HttpObserver(boolean isShowDialog, OnNextListener<T> onNextListener, OnErrorListener onErrorListener) {
        this.isShowDialog = isShowDialog;
        mProgressDialogHandler = new LoadingDialogHandler(ActivityCollector.getTopActivity(), this);
        this.onNextListener = onNextListener;
        this.OnErrorListener = onErrorListener;
    }

    public HttpObserver(boolean isShowDialog, OnNextListener<T> onNextListener, OnCompleteListener onCompleteListener) {
        this.isShowDialog = isShowDialog;
        mProgressDialogHandler = new LoadingDialogHandler(ActivityCollector.getTopActivity(), this);
        this.onNextListener = onNextListener;
        this.onCompleteListener = onCompleteListener;
    }

    public HttpObserver(boolean isShowDialog, OnNextListener<T> onNextListener, OnCompleteListener onCompleteListener, OnErrorListener onErrorListener) {
        this.isShowDialog = isShowDialog;
        mProgressDialogHandler = new LoadingDialogHandler(ActivityCollector.getTopActivity(), this);
        this.onNextListener = onNextListener;
        this.onCompleteListener = onCompleteListener;
        this.OnErrorListener = onErrorListener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        showProgressDialog();
    }

    public HttpRequestFunc getFunc() {
        return new HttpRequestFunc<T>();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        refreshOrLoadmoreFinsh();
        if (OnErrorListener != null) {
            OnErrorListener.onError(e);
            return;
        }
        if (NetworkUtil.getNetworkState(ActivityCollector.getTopActivity()) == NetworkUtil.INTERNET_NONE) {
            IToast.showShort("无网络连接");
            return;
        }
        //将错误统一处理
        if (e instanceof ConnectException
                || e instanceof SocketException
                || e instanceof UnknownHostException
                || e instanceof SocketTimeoutException) {
            IToast.showShort("未连接到服务器！");
            return;
        }
        if (e instanceof HttpException) {
            HttpException he = (HttpException) e;
            ResponseBody responseBody = he.response().errorBody();
            ErrorMessage message = null;
            try {
                Gson gson = new Gson();
                message = gson.fromJson(responseBody.string(), ErrorMessage.class);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (message == null) {
                return;
            }
            if (TextUtils.isEmpty(message.getMessage())) {
                IToast.showShort("服务器发生错误（HTTP_CODE:" + he.code() + ")");
            } else {
                IToast.showShort(message.getMessage());
            }
            return;
        }
        if (e instanceof JsonParseException ||
                e instanceof JSONException ||
                e instanceof ParseException) {
            Log.e(TAG, "onError: " + e.getMessage());
            IToast.showShort("解析数据发生错误");
        }
    }

    @Override
    public void onComplete() {
        refreshOrLoadmoreFinsh();
        if (onCompleteListener != null) {
            onCompleteListener.onComplete();
        }
    }

    @Override
    public void onNext(T values) {
        dismissProgressDialog();
        onNextListener.onNext(values);
    }

    @Override
    public void onCancle() {
        if (disposable != null) {
            //取消关联，即取消本次请求
            disposable.dispose();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null && isShowDialog) {
            mProgressDialogHandler.obtainMessage(LoadingDialogHandler.SHOW_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null && isShowDialog) {
            mProgressDialogHandler.obtainMessage(LoadingDialogHandler.DISMISS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    public interface OnNextListener<T> {
        void onNext(T data);
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    public interface OnErrorListener {
        void onError(Throwable throwable);
    }

    /**
     * 设置RefreshLayout,用来控制刷新完成，及加载完成
     *
     * @param refreshLayout 上拉刷新和加载更多
     * @return
     */
    public HttpObserver setRefreshLayout(BGARefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
        return this;
    }

    /**
     * 刷新或加载完成
     */
    private void refreshOrLoadmoreFinsh() {
        if (refreshLayout == null) {
            return;
        }
        if (refreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
            refreshLayout.endRefreshing();
        }
        if (refreshLayout.isLoadingMore()) {
            refreshLayout.endLoadingMore();
        }
    }

    public static class ErrorMessage {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
