package com.shenhesoft.examsapp.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.necistudio.libarary.item.Document;
import com.shenhesoft.examsapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author mashanshui
 * @date 2018-07-01
 * @desc TODO
 */
public class FilePickerAdapter extends BaseQuickAdapter<Document, BaseViewHolder> {

    public FilePickerAdapter(@Nullable List<Document> data) {
        super(R.layout.recycler_list_select_file, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Document item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(item.getLastModifyTime());
        String modifyDate = sdf.format(cal.getTime());
        helper.setText(R.id.tv_file_name, item.getTitle())
                .setText(R.id.tv_file_date, modifyDate)
                .setImageResource(R.id.iv_file_icon, item.getTypeDrawable());
    }
}
