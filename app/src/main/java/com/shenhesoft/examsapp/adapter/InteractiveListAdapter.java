package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.InteractiveModel;
import com.shenhesoft.examsapp.util.FileUtil;

import java.io.File;
import java.util.List;

import cn.droidlover.xdroidmvp.base.ActivityCollector;

/**
 * @author mashanshui
 * @date 2018/5/17
 * @desc TODO
 */
public class InteractiveListAdapter extends BaseQuickAdapter<InteractiveModel, BaseViewHolder> {

    public static final int STUDENT = 0;
    public static final int TEACHER = 1;

    public InteractiveListAdapter(@Nullable List<InteractiveModel> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<InteractiveModel>() {
            @Override
            protected int getItemType(InteractiveModel interactiveModel) {
                return interactiveModel.getInteractiveType();
            }
        });
        getMultiTypeDelegate()
                .registerItemType(STUDENT, R.layout.recycler_list_interactive_student)
                .registerItemType(TEACHER, R.layout.recycler_list_interactive_teacher);
    }

    @Override
    protected void convert(BaseViewHolder helper, InteractiveModel item) {
        switch (helper.getItemViewType()) {
            case STUDENT:
                if (!TextUtils.isEmpty(item.getRefuseReson())) {
                    helper.setVisible(R.id.tv_refuse_reason, true);
                    helper.setText(R.id.tv_refuse_reason, "教师已拒单，拒单理由：" + item.getRefuseReson());
                }
                helper.setText(R.id.tv_student_name, item.getAuthorUserName())
                        .setText(R.id.tv_student_date, item.getWriteTime())
                        .setText(R.id.tv_student_affix, item.getArticleTitle())
                        .setText(R.id.tv_student_title, item.getArticleTitle());
                break;
            case TEACHER:
                String url = item.getArticleAttachReply();
                String path = "";
                if (!TextUtils.isEmpty(url)) {
                    path = url.substring(url.lastIndexOf("/"));
                    path = FileUtil.getDownloadAffixPath(ActivityCollector.getTopActivity()) + path + "." + item.getAttachPostfixReply();
                }
                File file = new File(path);
                helper.setText(R.id.tv_teacher_name, item.getScoreUserName())
                        .setText(R.id.tv_teacher_date, item.getRemarkTime())
                        .setText(R.id.tv_teacher_affix, item.getAttachOriginTitle())
                        .setText(R.id.tv_teacher_title, item.getAttachOriginTitle())
                        .setText(R.id.tv_teacher_remark, item.getArticleRemark())
                        .setText(R.id.tv_teacher_mark, "【打分：" + item.getArticleScore() + "】")
                        .setText(R.id.btn_download_affix, file.exists() ? "查看附件" : "下载附件")
                        .addOnClickListener(R.id.btn_download_affix);
                break;
            default:
                break;
        }
    }
}
