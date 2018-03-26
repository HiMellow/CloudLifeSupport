package com.cloudlife.util;

import android.util.Log;

/**
 * @author 基哥   2017-12-21
 *         日志打印管理
 */
public class LogSwitch {
    public static int log = 6;
    private static final int v = 1;
    private static final int d = 2;
    private static final int i = 3;
    private static final int w = 4;
    private static final int e = 5;
    private static final String author = "Speaker-->";
    private static final String sign = "()\n-->>";

    //V级日志
    public static void v(String className, String describe) {
        if (log > v) {
            Log.v(className, author + describe);
        }
    }

    //V级日志
    public static void v(String className, String methodName, String describe) {
        if (log > v) {
            Log.v(className, author + methodName + sign + describe);
        }
    }

    //D级日志
    public static void d(String className, String describe) {
        if (log > d) {
            Log.d(className, author + describe);
        }
    }

    //D级日志
    public static void d(String className, String methodName, String describe) {
        if (log > d) {
            Log.d(className, author + methodName + sign + describe);
        }
    }

    //I级日志
    public static void i(String className, String describe) {
        if (log > i) {
            Log.i(className, author + describe);
        }
    }

    //I级日志
    public static void i(String className, String methodName, String describe) {
        if (log > i) {
            Log.i(className, author + methodName + sign + describe);
        }
    }

    //W级日志
    public static void w(String className, String describe) {
        if (log > w) {
            Log.w(className, author + describe);
        }
    }

    //W级日志
    public static void w(String className, String methodName, String describe) {
        if (log > w) {
            Log.w(className, author + methodName + sign + describe);
        }
    }

    //E级日志
    public static void e(String className, String describe) {
        if (log > e) {
            Log.e(className, author + describe);
        }
    }

    //E级日志,带异常
    public static void e(String className, Throwable err) {
        if (log > e) {
            Log.e(className, author + sign, err);
        }
    }

    //E级日志
    public static void e(String className, String methodName, String describe) {
        if (log > e) {
            Log.e(className, author + methodName + sign + describe);
        }
    }

    //E级日志,带异常
    public static void e(String className, String methodName, Throwable err) {
        if (log > e) {
            Log.e(className, author + methodName + sign + err);
        }
    }

    //E级日志,带异常
    public static void e(String className, String methodName, String describe, Throwable err) {
        if (log > e) {
            Log.e(className, author + methodName + sign + describe + "↓");
            Log.e(methodName + "():", "", err);
        }
    }
}
