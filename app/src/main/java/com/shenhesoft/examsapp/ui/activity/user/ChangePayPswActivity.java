package com.shenhesoft.examsapp.ui.activity.user;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.present.ChangePayPswPresent;
import com.shenhesoft.examsapp.util.keyboard.widget.VirtualKeyboardView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/5/10
 * @desc 设置支付密码
 */
public class ChangePayPswActivity extends XTitleActivity<ChangePayPswPresent> {

    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.et_pay_password)
    EditText etPayPassword;
    @BindView(R.id.et_reply_pay_password)
    EditText etReplyPayPassword;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton btnConfirm;
    @BindView(R.id.virtualKeyboardView)
    VirtualKeyboardView virtualKeyboardView;
    private GridView gridView;
    private Animation enterAnim;
    private Animation exitAnim;
    private ArrayList<Map<String, String>> valueList1;
    private ArrayList<Map<String, String>> valueList2;
    private InputMethodManager imm;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("支付密码设置");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnConfirm.setChangeAlphaWhenPress(true);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initAnim();
        initView();
        valueList1 = virtualKeyboardView.getValueList();
        valueList2 = virtualKeyboardView.getValueList();
    }

    private void initView() {
        disableShowInput();
        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
            }
        });

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);
        etPayPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 强制隐藏软键盘
                    imm.hideSoftInputFromWindow(etPayPassword.getWindowToken(), 0);
                    virtualKeyboardView.setFocusable(true);
                    virtualKeyboardView.setFocusableInTouchMode(true);

                    virtualKeyboardView.startAnimation(enterAnim);
                    virtualKeyboardView.setVisibility(View.VISIBLE);
                }
            }
        });
        etReplyPayPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 强制隐藏软键盘
                    imm.hideSoftInputFromWindow(etPayPassword.getWindowToken(), 0);
                    virtualKeyboardView.setFocusable(true);
                    virtualKeyboardView.setFocusableInTouchMode(true);

                    virtualKeyboardView.startAnimation(enterAnim);
                    virtualKeyboardView.setVisibility(View.VISIBLE);
                }
            }
        });
        etLoginPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    virtualKeyboardView.setFocusable(false);
                    virtualKeyboardView.setFocusableInTouchMode(false);

                    virtualKeyboardView.startAnimation(exitAnim);
                    virtualKeyboardView.setVisibility(View.GONE);
                }
            }
        });
    }

    public void disableShowInput() {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            etPayPassword.setInputType(InputType.TYPE_NULL);
            etReplyPayPassword.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(etPayPassword, false);
                method.invoke(etReplyPayPassword, false);
            } catch (Exception e) {//TODO: handle exception
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pay_psw;
    }

    @Override
    public ChangePayPswPresent newP() {
        return new ChangePayPswPresent();
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        getP().changePayPsw(etLoginPassword.getText().toString().trim(), etPayPassword.getText().toString().trim(), etReplyPayPassword.getText().toString().trim());
    }

    public void changeSuccess() {
        IToast.showShort("修改成功");
        finish();
    }

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮
                if (etPayPassword.isFocused()) {
                    String amount = etPayPassword.getText().toString().trim();
                    amount = amount + valueList1.get(position).get("name");

                    etPayPassword.setText(amount);

                    Editable ea = etPayPassword.getText();
                    etPayPassword.setSelection(ea.length());
                } else if (etReplyPayPassword.isFocused()) {
                    String amount = etReplyPayPassword.getText().toString().trim();
                    amount = amount + valueList2.get(position).get("name");

                    etReplyPayPassword.setText(amount);

                    Editable ea = etReplyPayPassword.getText();
                    etReplyPayPassword.setSelection(ea.length());
                }

            } else {
                if (etPayPassword.isFocused()) {
                    if (position == 9) {      //点击退格键
                        String amount = etPayPassword.getText().toString().trim();
                        if (!amount.contains(".")) {
                            amount = amount + valueList1.get(position).get("name");
                            etPayPassword.setText(amount);

                            Editable ea = etPayPassword.getText();
                            etPayPassword.setSelection(ea.length());
                        }
                    }

                    if (position == 11) {      //点击退格键
                        String amount = etPayPassword.getText().toString().trim();
                        if (amount.length() > 0) {
                            amount = amount.substring(0, amount.length() - 1);
                            etPayPassword.setText(amount);

                            Editable ea = etPayPassword.getText();
                            etPayPassword.setSelection(ea.length());
                        }
                    }
                } else if (etReplyPayPassword.isFocused()) {
                    if (position == 9) {      //点击退格键
                        String amount = etReplyPayPassword.getText().toString().trim();
                        if (!amount.contains(".")) {
                            amount = amount + valueList2.get(position).get("name");
                            etReplyPayPassword.setText(amount);

                            Editable ea = etReplyPayPassword.getText();
                            etReplyPayPassword.setSelection(ea.length());
                        }
                    }

                    if (position == 11) {      //点击退格键
                        String amount = etReplyPayPassword.getText().toString().trim();
                        if (amount.length() > 0) {
                            amount = amount.substring(0, amount.length() - 1);
                            etReplyPayPassword.setText(amount);

                            Editable ea = etReplyPayPassword.getText();
                            etReplyPayPassword.setSelection(ea.length());
                        }
                    }
                }

            }
        }
    };
}
