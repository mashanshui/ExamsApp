package com.shenhesoft.examsapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.ui.fragment.dohomework.AnswerFragment;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/4/18
 * @desc TODO
 */
public class AnswerPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "AnswerPagerAdapter";
    private List<Class> fragments;
    private int answerType;
    private String productId;
    private List<AnswerModel> answerModels;

    public AnswerPagerAdapter(FragmentManager fm, List<Class> fragments, int answerType, String productId, List<AnswerModel> answerModels) {
        super(fm);
        this.fragments = fragments;
        this.answerType = answerType;
        this.productId = productId;
        this.answerModels = answerModels;
    }

    @Override
    public Fragment getItem(int position) {
//        try {
////            return (Fragment) fragments.get(position).newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
        return AnswerFragment.newInstance(answerType,productId,answerModels.get(position));
    }

    public void setAnswerList(List<AnswerModel> answerList) {
        if (!answerModels.isEmpty()) {
            answerModels.clear();
        }
        answerModels.addAll(answerList);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
