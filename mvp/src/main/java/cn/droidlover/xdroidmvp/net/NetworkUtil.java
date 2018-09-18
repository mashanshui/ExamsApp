package cn.droidlover.xdroidmvp.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author mashanshui
 * @date 2018/6/22
 * @desc 获取网络状态
 */
public class NetworkUtil {
    public static final int INTERNET_WIFI = 0;
    public static final int INTERNET_MOBILE = 1;
    public static final int INTERNET_OTHERS = 2;
    public static final int INTERNET_NONE = 3;

    public static int getNetworkState(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager
                .getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
            //获取网络类型
            int netWorkType = mNetworkInfo.getType();
            if (netWorkType == ConnectivityManager.TYPE_WIFI) {
                return INTERNET_WIFI;
            } else if (netWorkType == ConnectivityManager.TYPE_MOBILE) {
                return INTERNET_MOBILE;
            } else {
                return INTERNET_OTHERS;
            }
        } else {
            return INTERNET_NONE;
        }
    }
}
