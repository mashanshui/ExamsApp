package com.shenhesoft.examsapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.ViewTabPagerAdapter;
import com.shenhesoft.examsapp.ui.fragment.SelectOtherFileFragment;
import com.shenhesoft.examsapp.ui.fragment.SelectTencentFileFragment;
import com.shenhesoft.examsapp.util.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XTitleActivity;
import io.reactivex.functions.Consumer;

public class SelectFileActivity extends XTitleActivity {
    public static final int CHOOSE_REQUEST_CODE = 101;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    @BindView(R.id.btn_upload)
    QMUIRoundButton btnUpload;

    private List<Fragment> fragmentList;
    private SelectTencentFileFragment selectQQFileFragment;
    private SelectTencentFileFragment selectWeChatFileFragment;
    private SelectOtherFileFragment selectOtherFileFragment;

    @Override
    protected void initTitle() {
        setBackAction();
        setTitle("选择文件");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ArrayList<String> list = new ArrayList<>();
        list.add(".docx");
        list.add(".doc");
        selectQQFileFragment = SelectTencentFileFragment.newInstance(AppConstant.QQ);
        selectWeChatFileFragment = SelectTencentFileFragment.newInstance(AppConstant.WeChat);
        selectOtherFileFragment = SelectOtherFileFragment.newInstance(list);
        fragmentList = new ArrayList<>();
        fragmentList.add(selectQQFileFragment);
        fragmentList.add(selectWeChatFileFragment);
        fragmentList.add(selectOtherFileFragment);
        List<String> titleList = Arrays.asList("QQ", "微信", "其他");
        ViewTabPagerAdapter viewPageFragmentAdapter = new ViewTabPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPage.setOffscreenPageLimit(10);
        viewPage.setAdapter(viewPageFragmentAdapter);
        tabLayout.setupWithViewPager(viewPage);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_file;
    }

    @Override
    public Object newP() {
        return null;
    }

    public void filePickFinish(String path) {
        Intent intent = new Intent();
        intent.putExtra("path", path);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.btn_upload)
    public void onViewClicked() {
        getRxPermissions().request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setType("*/*");//无类型限制
                            String[] mimeTypes = {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            Intent chooser = Intent.createChooser(intent, "请选择要打开的文件");
                            startActivityForResult(chooser, CHOOSE_REQUEST_CODE);
                        } else {
                            showKonwnDialog();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_REQUEST_CODE && data != null) {
            Uri uri = data.getData();
//            String encodedPath = uri.getEncodedPath();
//            String filePath = Uri.decode(encodedPath);
            String path = FileUtil.getPath(context, uri);
            Intent intent = new Intent();
            intent.putExtra("path", path);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void showKonwnDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_single_confirm)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(com.othershe.nicedialog.ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                        viewHolder.setText(R.id.tv_title, "提示");
                        viewHolder.setText(R.id.tv_message, "没有权限无法查看");
                        viewHolder.setText(R.id.tv_confirm, "我知道了");
                        viewHolder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                baseNiceDialog.dismiss();
                            }
                        });
                    }
                })
                .setWidth(300)
                .setHeight(150)
                .show(getSupportFragmentManager());
    }
}
