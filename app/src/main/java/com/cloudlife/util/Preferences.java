package com.cloudlife.util;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author 基哥   2017-03-29
 *         使用SharedPreferences保存数据
 */
public class Preferences {
    private static Preferences preferences;
    private static SharedPreferences sharedPreferences;

    //单例模式
    public static Preferences getInstance(Context context) {
        if (preferences == null) {
            preferences = new Preferences();
            String name = context.getPackageName();
            sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        }
        return preferences;
    }

    //对象类型保存
    public void saveToPreferences(String key, Object object) {
        if (object == null) {
            sharedPreferences.edit().remove(key).apply();
        } else {
            sharedPreferences.edit().putString(key, JSON.toJSONString(object)).apply();
        }
    }

    //字符类型保存
    public void saveToPreferences(String key, String content) {
        if (content == null) {
            sharedPreferences.edit().remove(key).apply();
        } else {
            sharedPreferences.edit().putString(key, content).apply();
        }
    }

    //对象类型返回
    public <T> T getByPreferences(String key, Class<T> type) {
        String result = sharedPreferences.getString(key, null);
        if (result != null) {
            return JSON.parseObject(result, type);
        } else {
            return null;
        }
    }

    //数组对象类型返回
    public <T> List<T> getByPreferenceses(String key, Class<T> type) {
        String result = sharedPreferences.getString(key, null);
        if (result != null) {
            return JSON.parseArray(result, type);
        } else {
            return null;
        }

    }

    //字符类型返回
    public String getByPreferences(String key) {
        return sharedPreferences.getString(key, null);
    }

}
