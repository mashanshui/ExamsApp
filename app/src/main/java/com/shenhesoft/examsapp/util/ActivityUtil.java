package com.shenhesoft.examsapp.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.MyApplication;

import cn.droidlover.xdroidmvp.cache.SharedPref;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author mashanshui
 * @date 2018/4/12
 * @desc TODO
 */
public class ActivityUtil {
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commitAllowingStateLoss();
    }

    public static void addFragmentToActivityToBackStack(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static String getUserID() {
        return SharedPref.getInstance(MyApplication.context).getString(AppConstant.UserId, "");
    }
}
