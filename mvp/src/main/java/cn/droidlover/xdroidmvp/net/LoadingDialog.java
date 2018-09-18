package cn.droidlover.xdroidmvp.net;

import android.content.DialogInterface;
import android.view.KeyEvent;

import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.ViewHolder;

import cn.droidlover.xdroidmvp.R;

/**
 * @author mashanshui
 * @date 2018/6/22
 * @desc TODO
 */
public class LoadingDialog extends BaseNiceDialog implements DialogInterface.OnKeyListener{

    private OnCancelListener onCancelListener;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        onCancelListener.onCancel(dialog);
    }

    @Override
    public void convertView(ViewHolder viewHolder, BaseNiceDialog baseNiceDialog) {
        getDialog().setCanceledOnTouchOutside(false);
        baseNiceDialog.setWidth(100)
                .setHeight(100)
                .setDimAmount(0);
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return false;
    }

    public interface OnCancelListener {
        void onCancel(DialogInterface dialog);
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        Window window = getDialog().getWindow();
//        if (window != null) {
//            WindowManager.LayoutParams wmLp = window.getAttributes();
//            wmLp.dimAmount = 0.0f;
////            wmLp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//            window.setAttributes(wmLp);
//        }
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.dialog_loading, container, false);
//        ViewGroup contentWrap = (ViewGroup) view.findViewById(R.id.contentWrap);
//        QMUILoadingView loadingView = new QMUILoadingView(getContext());
//        loadingView.setColor(Color.WHITE);
//        loadingView.setSize(QMUIDisplayHelper.dp2px(getContext(), 32));
//        LinearLayout.LayoutParams loadingViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        loadingView.setLayoutParams(loadingViewLP);
//        contentWrap.addView(loadingView);
//
//        TextView tipView = new TextView(getContext());
//        LinearLayout.LayoutParams tipViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        tipViewLP.topMargin = QMUIDisplayHelper.dp2px(getContext(), 12);
//        tipView.setLayoutParams(tipViewLP);
//
//        tipView.setEllipsize(TextUtils.TruncateAt.END);
//        tipView.setGravity(Gravity.CENTER);
//        tipView.setMaxLines(2);
//        tipView.setTextColor(ContextCompat.getColor(getContext(), com.qmuiteam.qmui.R.color.qmui_config_color_white));
//        tipView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
//        tipView.setText("正在加载");
//
//        contentWrap.addView(tipView);
//        return view;
//    }
}
