package com.shenhesoft.examsapp.ui.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.allen.library.SuperTextView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.present.AddAdvicePresent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/6/2
 * @desc 添加投诉建议
 */
public class AddAdviceActivity extends XTitleActivity<AddAdvicePresent> {
    @BindView(R.id.tv_type)
    SuperTextView tvType;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_order_code)
    EditText etOrderCode;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_contact_method)
    EditText etContactMethod;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton btnConfirm;
    @BindView(R.id.tv_object)
    SuperTextView tvObject;
    @BindView(R.id.tv_contact_type)
    SuperTextView tvContactType;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("添加投诉建议");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnConfirm.setChangeAlphaWhenPress(true);
        tvType.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                showTypeDialog();
            }
        });
        tvObject.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                showObjectDialog();
            }
        });
        tvContactType.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                showContactTypeDialog();
            }
        });
    }

    private void showObjectDialog() {
//        final String[] items = new String[]{"产品", "教师", "系统"};
        final String[] items = new String[]{"产品", "教师"};
        new QMUIDialog.MenuDialogBuilder(context)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvObject.setRightString(items[which]);
                        dialog.dismiss();
//                        if (items[which].equals("系统") || tvType.getRightString().equals("建议")) {
//                            etOrderCode.setVisibility(View.GONE);
//                        } else {
//                            etOrderCode.setVisibility(View.VISIBLE);
//                        }
                        etOrderCode.setVisibility(View.VISIBLE);
                    }
                })
                .show();
    }

    private void showTypeDialog() {
        final String[] items = new String[]{"投诉", "建议"};
        new QMUIDialog.MenuDialogBuilder(context)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvType.setRightString(items[which]);
                        dialog.dismiss();
//                        if (items[which].equals("建议")) {
//                            etOrderCode.setVisibility(View.GONE);
//                        } else {
//                            etOrderCode.setVisibility(View.VISIBLE);
//                        }
                        etOrderCode.setVisibility(View.VISIBLE);
                    }
                })
                .show();
    }

    private void showContactTypeDialog() {
        final String[] items = new String[]{"手机", "邮箱", "QQ"};
        new QMUIDialog.MenuDialogBuilder(context)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvContactType.setRightString(items[which]);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_advice;
    }

    @Override
    public AddAdvicePresent newP() {
        return new AddAdvicePresent();
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        getP().addAdvice(tvType.getRightString(), tvObject.getRightString(), etTitle.getText().toString(), etOrderCode.getText().toString()
                , etContent.getText().toString(), tvContactType.getRightString(), etContactMethod.getText().toString());
    }

    public void finishPage() {
        IToast.showShort("添加成功");
        setResult(RESULT_OK);
        finish();
    }

}
