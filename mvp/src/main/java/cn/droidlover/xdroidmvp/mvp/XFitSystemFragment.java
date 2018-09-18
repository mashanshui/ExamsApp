package cn.droidlover.xdroidmvp.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author mashanshui
 * @date 2018/5/16
 * @desc TODO
 */
public abstract class XFitSystemFragment extends Fragment {

    protected abstract View onCreateView();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = onCreateView();
        if (translucentFull()) {
            rootView.setFitsSystemWindows(false);
        } else {
            rootView.setFitsSystemWindows(true);
        }
        return rootView;
    }

    /**
     * 沉浸式处理，返回 false，则状态栏下为内容区域，返回 true, 则状态栏下为 padding 区域
     */
    protected boolean translucentFull() {
            return false;
    }
}
