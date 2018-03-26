package com.cloudlife.support;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudlife.util.LogSwitch;
import com.cloudlife.util.Preferences;
import com.cloudlife.util.ViewReset;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 基哥   2017-12-21
 *         功能集成的Fragment
 */
public abstract class BaseFragment extends Fragment {
    private String TAG;

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    private Unbinder unbinder;
    private Preferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.TAG = getClass().getSimpleName();
        LogSwitch.v(TAG, "onCreateView");
        View vRoot = createView(inflater, container, savedInstanceState);
        new ViewReset().setViewsSize(vRoot);//缩放组件
        unbinder = ButterKnife.bind(this, vRoot);//绑定组件
        preferences = Preferences.getInstance(getActivity());
        initWidget(vRoot);//初始化组件
        initData();//初始化数据
        return vRoot;
    }

    @Override
    public void onDestroy() {
        LogSwitch.i(TAG, "onDestroy");
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //注册EventBus
    protected void setEventBus() {
        EventBus.getDefault().register(this);
    }

    //对象类型保存
    public void saveToPreferences(String key, Object object) {
        preferences.saveToPreferences(key, object);
    }

    //字符类型保存
    public void saveToPreferences(String key, String content) {
        preferences.saveToPreferences(key, content);
    }

    //对象类型返回
    public <T> T getByPreferences(String key, Class<T> type) {
        return preferences.getByPreferences(key, type);
    }

    //数组对象类型返回
    public <T> List<T> getByPreferenceses(String key, Class<T> type) {
        return preferences.getByPreferenceses(key, type);
    }

    //字符类型返回
    public String getByPreferences(String key) {
        return preferences.getByPreferences(key, null);
    }

    //初始化组件
    protected abstract void initWidget(View v);

    //初始化数据
    protected abstract void initData();
}