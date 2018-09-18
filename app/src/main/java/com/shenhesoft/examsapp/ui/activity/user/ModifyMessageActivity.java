package com.shenhesoft.examsapp.ui.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.allen.library.SuperTextView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.present.ModifyMessagePresent;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;

/**
 * @author mashanshui
 * @date 2018/5/19
 * @desc 用户修改个人信息
 */
public class ModifyMessageActivity extends XTitleActivity<ModifyMessagePresent> {

    public static final int ResultCode = 101;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btn_confirm)
    QMUIRoundButton btnConfirm;
    @BindView(R.id.tv_sex)
    SuperTextView tvSex;
    private int type;

    @Override
    protected void initTitle() {
        type = getIntent().getIntExtra("modifyItem", 0);
        setBackAction();
        switch (type) {
            case 1:
                setTitle("修改用户名");
                etMessage.setHint("请输入用户名");
                tvSex.setVisibility(View.GONE);
                etMessage.setVisibility(View.VISIBLE);
                break;
            case 2:
                setTitle("修改性别");
                etMessage.setHint("请输入性别");
                tvSex.setVisibility(View.VISIBLE);
                etMessage.setVisibility(View.GONE);
                break;
            case 3:
                setTitle("修改年龄");
                etMessage.setHint("请输入年龄");
                tvSex.setVisibility(View.GONE);
                etMessage.setVisibility(View.VISIBLE);
                break;
            case 4:
                setTitle("修改邮箱");
                etMessage.setHint("请输入邮箱");
                tvSex.setVisibility(View.GONE);
                etMessage.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        btnConfirm.setChangeAlphaWhenPress(true);
        tvSex.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                showTypeDialog();
            }
        });
    }

    private void showTypeDialog() {
        final String[] items = new String[]{"男", "女"};
        new QMUIDialog.MenuDialogBuilder(context)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvSex.setRightString(items[which]);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_message;
    }

    @Override
    public ModifyMessagePresent newP() {
        return new ModifyMessagePresent();
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        if (type == 2 && TextUtils.equals("男", tvSex.getRightString())) {
            getP().modifyMessage("1");
        } else if (type == 2 && TextUtils.equals("女", tvSex.getRightString())) {
            getP().modifyMessage("0");
        } else {
            getP().modifyMessage(etMessage.getText().toString().trim());
        }
    }

    public void modifySuccess() {
        IToast.showShort("修改成功");
        setResult(ResultCode);
        finish();
    }

    public int getType() {
        return type;
    }

}
