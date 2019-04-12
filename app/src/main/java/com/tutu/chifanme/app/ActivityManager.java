package com.tutu.chifanme.app;

import android.app.Activity;
import android.content.Context;
import android.os.Process;

import java.util.Stack;

/**
 * Activity栈式管理类
 *
 * 作者:李俊贤  on 2016年5月17日.
 * 邮箱:21194250@qq.com
 * 说明:
 */
public class ActivityManager {

    private static ActivityManager instance;

    /**
     * Activity栈
     */
    private static Stack<Activity> activityStack;

    private ActivityManager() {

    }

    /**
     * 单例
     */
    public static ActivityManager getAppManager() {
        if (null == instance) instance = new ActivityManager();
        return instance;
    }

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        if (null == activityStack) activityStack = new Stack<Activity>();
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（Activity栈中最后一个压栈的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 获取指定的Activity
     */
    public Activity getActivity(Class<?> cls) {
        if (null != activityStack)
            for (Activity activity : activityStack)
                if (cls.equals(activity.getClass())) return activity;
        return null;
    }

    /**
     * 结束当前Activity（Activity栈中最后一个压栈的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (null != activity && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类型的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (cls.equals(activity.getClass())) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    public void finishAllActivity() {
        while (activityStack.size() > 0) {
            Activity activity = activityStack.get(activityStack.size() - 1);
            activityStack.remove(activityStack.size() - 1);
            activity.finish();
            activity = null;
        }
    }

    /**
     * APP
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            // Kill App
            Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (Exception e) {

        }
    }

}
