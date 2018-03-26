package com.cloudlife.support;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.cloudlife.util.ActivityManager;
import com.cloudlife.util.LogSwitch;
import com.cloudlife.util.Preferences;
import com.cloudlife.util.ViewReset;
import com.cloudlife.utils.ViewReset;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 基哥   2017-12-21
 *         功能集成的Activity
 */
public abstract class BaseActivity extends FragmentActivity {
    private final String TAG;
    private Preferences preferences;

    protected BaseActivity() {
        this.TAG = getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogSwitch.v(TAG, "onStart");
        super.onCreate(savedInstanceState);
        this.preferences = Preferences.getInstance(this);
        ActivityManager.add(this);//添加此Activity
    }

    @Override
    protected void onDestroy() {
        LogSwitch.v(TAG, "onDestroy");
        super.onDestroy();
        ActivityManager.remove(this);//移除此Activity
        if (isManualBind && unbinder != null) {
            unbinder.unbind();
        }
        if (isRegistEventBus) {
            EventBus.getDefault().unregister(this);
        }
    }

    private boolean isRegistEventBus;//是否注册了EventBus

    //注册EventBus
    protected void setEventBus() {
        EventBus.getDefault().register(this);
        isRegistEventBus = true;
    }

    private boolean isManualBind;//是否手动解除组件绑定

    //设置为手动解除组件绑定
    public void setManualBind() {
        this.isManualBind = false;
    }

    //解除组件绑定
    public void unbind() {
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private Unbinder unbinder;//组件绑定

    @Override
    public void setContentView(int res) {
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        super.setContentView(res);
        ViewReset paramsKiller = new ViewReset();
        paramsKiller.setParams(this, ViewReset.SCALE_WIDTH);//设置参数
        paramsKiller.setViewsSize(getWindow().getDecorView().findViewById(android.R.id.content));//动态改变布局
        unbinder = ButterKnife.bind(this);//绑定组件
        initWidget();//初始化组件
        initData();//初始化数据
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    //获取根视图
    protected View getRootView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

    //获取根视图
    protected ViewGroup getRootViewGroup() {
        return (ViewGroup) getRootView();
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
        return preferences.getByPreferences(key);
    }

    //初始化组件
    protected abstract void initWidget();

    //初始化数据
    protected abstract void initData();
}
