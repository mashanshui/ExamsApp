package cn.droidlover.xdroidmvp.kit;

import android.widget.Toast;

import cn.droidlover.xdroidmvp.base.ActivityCollector;


/**
 * 作者：Tornado
 * 创作日期：2017/8/9.
 * 描述：
 */

public class IToast {

    private static Toast toast;

    public static void showShort(String msg) {
        if (toast == null) {
            toast = Toast.makeText(ActivityCollector.getTopActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showLong(String msg) {
        if (toast == null) {
            toast = Toast.makeText(ActivityCollector.getTopActivity().getApplicationContext(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
