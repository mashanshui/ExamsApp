package com.shenhesoft.examsapp.adapter;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.network.model.LessonsModel;
import com.shenhesoft.examsapp.network.model.LessonsSection;
import com.shenhesoft.examsapp.network.model.LessonsVideo;

import java.util.List;


/**
 * @author mashanshui
 * @date 2018/5/16
 * @desc TODO
 */
public class LessonsDataAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int LESSONS_LIST_1 = 0;
    public static final int LESSONS_LIST_2 = 1;
    public static final int LESSONS_LIST_3 = 2;
    private FragmentManager fragmentManager;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public LessonsDataAdapter(List<MultiItemEntity> data, FragmentManager fragmentManager) {
        super(data);
        this.fragmentManager = fragmentManager;
        addItemType(LESSONS_LIST_1, R.layout.recycler_list_lessons_item1);
        addItemType(LESSONS_LIST_2, R.layout.recycler_list_lessons_item2);
        addItemType(LESSONS_LIST_3, R.layout.recycler_list_lessons_item4);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case LESSONS_LIST_1:
                final LessonsModel listBean1 = (LessonsModel) item;
                helper.setText(R.id.tv_message, listBean1.getProductTitle())
                        .setImageResource(R.id.iv_indicate_image, listBean1.isExpanded() ? R.drawable.more_unfold_dark : R.drawable.more_dark);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (listBean1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case LESSONS_LIST_2:
                final LessonsSection listBean2 = (LessonsSection) item;
                helper.setText(R.id.tv_message, listBean2.getCourseName())
                        .setImageResource(R.id.iv_indicate_image, listBean2.isExpanded() ? R.drawable.more_unfold_light : R.drawable.more_light);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (listBean2.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case LESSONS_LIST_3:
                final LessonsVideo listBean3 = (LessonsVideo) item;
                helper.setText(R.id.tv_pdfMessage, listBean3.getVideoName());
                break;
            default:
                break;
        }
    }

}
