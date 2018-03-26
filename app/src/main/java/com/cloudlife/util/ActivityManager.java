package com.cloudlife.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 基哥   2017-12-22
 *         Activity管理类
 */
public class ActivityManager {
    private static List<Activity> listActivity;

    //添加Activity
    public static void add(Activity activity) {
        if (listActivity == null) {
            listActivity = new ArrayList<>();
        }
        listActivity.add(activity);
    }

    //添加Activity,去掉重复
    public static void addNotSame(Activity activity) {
        if (listActivity == null) {
            listActivity = new ArrayList<>();
        }
        if (listActivity.contains(activity)) {
            listActivity.remove(activity);
        }
        listActivity.add(activity);
    }


    //移除Activity
    public static void remove(Activity activity) {
        if (listActivity == null || listActivity.isEmpty()) {
            return;
        }
        if (listActivity.contains(activity)) {
            listActivity.remove(activity);
        }
    }

    //退出应用
    public static void exitApplication() {
        if (listActivity == null) {
            return;
        }
        for (Activity activity : listActivity) {
            activity.finish();
        }
        handler.sendEmptyMessageDelayed(0, 500);
    }

    //不完全退出,保留最后一个
    public static void exitWithoutLastActivity() {
        if (listActivity == null || listActivity.size() <= 1) {
            return;
        }
        listActivity.remove(listActivity.size() - 1);
        for (Activity activity : listActivity) {
            activity.finish();
        }
    }

    //获取最后一个Activity
    public static Activity getLastActivity() {
        if (listActivity.size() > 0) {
            return listActivity.get(listActivity.size() - 1);
        }
        return null;
    }

    @SuppressLint("HandlerLeak")
    private static android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.exit(0);
        }
    };
}
