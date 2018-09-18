package com.shenhesoft.examsapp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.shenhesoft.examsapp.PushConstant;
import com.shenhesoft.examsapp.R;
import com.shenhesoft.examsapp.adapter.MainViewPageFragmentAdapter;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.ui.activity.PushActivity;
import com.shenhesoft.examsapp.ui.activity.modifyhomework.InteractiveActivity;
import com.shenhesoft.examsapp.ui.fragment.DoHomeWorkFragment;
import com.shenhesoft.examsapp.ui.fragment.ModifyHomeworkFragment;
import com.shenhesoft.examsapp.ui.fragment.OnlineProductFragment;
import com.shenhesoft.examsapp.ui.fragment.PersonalCenterFragment;
import com.shenhesoft.examsapp.ui.fragment.TakeLessonsFragment;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * @author mashanshui
 * @date 2018/5/3
 * @desc 主界面
 */
public class MainActivity extends XActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.main_viewPage)
    QMUIViewPager viewPager;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    private DoHomeWorkFragment doHomeWorkFragment;
    private ModifyHomeworkFragment modifyHomeworkFragment;
    private OnlineProductFragment onlineProductFragment;
    private PersonalCenterFragment personalCenterFragment;
    private TakeLessonsFragment takeLessonsFragment;
    private MenuItem menuItem;
    private String pushStatus;
    private String pushData;

    //BottomNavigationView的监听事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_do_homework:
                    //点击菜单项时跳转ViewPage
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.menu_item_take_lessons:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.menu_item_personal_center:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.menu_item_modify_homework:
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.menu_item_online_product:
                    viewPager.setCurrentItem(4);
                    return true;
                default:
                    break;
            }
            return false;
        }

    };


    @Override
    public void initData(Bundle savedInstanceState) {
        pushStatus = SharedPref.getInstance(context).getString(PushConstant.PushStatus, "");
        pushData = SharedPref.getInstance(context).getString(PushConstant.PushData, "");
        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        MainViewPageFragmentAdapter adapter = new MainViewPageFragmentAdapter(getSupportFragmentManager());
        doHomeWorkFragment = new DoHomeWorkFragment();
        takeLessonsFragment = new TakeLessonsFragment();
        modifyHomeworkFragment = new ModifyHomeworkFragment();
        onlineProductFragment = new OnlineProductFragment();
        personalCenterFragment = new PersonalCenterFragment();
        adapter.addFragment(doHomeWorkFragment);
        adapter.addFragment(takeLessonsFragment);
        adapter.addFragment(personalCenterFragment);
        adapter.addFragment(modifyHomeworkFragment);
        adapter.addFragment(onlineProductFragment);
        //禁止滑动切换
//        viewPager.setSwipeable(false);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 将当前的页面对应的底部标签设为选中状态
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(2, false);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(pushData) && !TextUtils.isEmpty(pushStatus)) {
            SharedPref.getInstance(context).putNowString(PushConstant.PushStatus, "");
            SharedPref.getInstance(context).putNowString(PushConstant.PushData, "");
            goActivity();
            pushStatus = null;
            pushData = null;
        }
    }

    private void goActivity() {
        switch (pushStatus) {
            case "1":
                break;
            case "2":
                ProductModel productModel = new ProductModel();
                productModel.setOrderId(pushData);
                EventBus.getDefault().postSticky(productModel);
                Router.newIntent(context).to(InteractiveActivity.class).launch();
                break;
            case "3":
                ProductModel productModel2 = new ProductModel();
                productModel2.setOrderId(pushData);
                EventBus.getDefault().postSticky(productModel2);
                Router.newIntent(context).to(InteractiveActivity.class).launch();
                break;
            case "4":
                Router.newIntent(context).to(PushActivity.class)
                        .putString("data", pushData)
                        .launch();
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        QMUIStatusBarHelper.translucent(context);
        QMUIStatusBarHelper.setStatusBarLightMode(context);
        return R.layout.activity_main;
    }

    @Override
    public Object newP() {
        return null;
    }


    @SuppressLint("RestrictedApi")
    public void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private long mExitTime = 0;

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        startActivity(intent);
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.fixSoftInputLeaks(context);
    }

}
