package cn.droidlover.xdroidmvp.net;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import cn.droidlover.xdroidmvp.R;
import cn.droidlover.xdroidmvp.base.ActivityCollector;


/**
 * 作者：Tornado
 * 创作日期：2017/8/9.
 * 描述：封装的一个LoadingDialog，使用Handler控制显示与关闭的
 */

public class LoadingDialogHandler extends Handler {

    public final static int SHOW_DIALOG = 0;
    public final static int DISMISS_DIALOG = 1;

    private LoadingDialog loadingDialog;
    private Dialog dialog;
    private Context context;
    private DialogCancleListener dialogCancleListener;

    public LoadingDialogHandler(Context context, DialogCancleListener listener) {
        super();
        this.context = context;
        dialogCancleListener = listener;
    }

    private void showDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog();
        }
        loadingDialog.setOutCancel(true);
//        FragmentTransaction ft = ((AppCompatActivity) (ActivityCollector.getTopActivity())).getSupportFragmentManager().beginTransaction();
//        ft.add(loadingDialog, this.getClass().getSimpleName());
//        ft.commitAllowingStateLoss();//注意这里使用commitAllowingStateLoss()
        loadingDialog.show(((AppCompatActivity) (ActivityCollector.getTopActivity())).getSupportFragmentManager());

        loadingDialog.setOnCancelListener(new LoadingDialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialogCancleListener.onCancle();
            }
        });
//        dialog = new QMUITipDialog.Builder(context)
//                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
//                .setTipWord("正在加载")
//                .create();
//        //当用户点击返回键 关闭dialog时，即认为用户想取消本次请求
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                dialogCancleListener.onCancle();
//            }
//        });
//        if (!dialog.isShowing()) {
//            dialog.show();
//        }
    }

    private void dismissDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismissAllowingStateLoss();
        }
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_DIALOG:
                showDialog();
                break;
            case DISMISS_DIALOG:
                dismissDialog();
                break;
            default:
                break;
        }
    }


    /**
     * Dialog取消时回调接口
     */
    public interface DialogCancleListener {
        void onCancle();
    }
}
