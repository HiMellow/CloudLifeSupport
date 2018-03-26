package com.cloudlife.util;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author 基哥   2017-05-31 10:49
 *         动态改变布局,自适应需要手动设置按宽、高、等比例
 */
public class ViewReset {
    private final String TAG;
    private static float scale;                 //缩放比
    private static float density;               //屏幕密度

    //缩放依据:宽、高、等比
    public static final int SCALE_WIDTH = 0X00;
    public static final int SCALE_HEIGHT = 0X01;
    public static final int SCALE_BOTH = 0X02;

    public ViewReset() {
        this.TAG = getClass().getSimpleName();
    }

    //计算出density
    public void setParams(Context context, int type) {
        if (scale == 0) {
            //布局标准,单位DP
            final int standardWidth = 960;
            final int standardHeight = 540;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) (context)).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            density = context.getResources().getDisplayMetrics().density;
            final float scaleWidth = (float) displayMetrics.widthPixels / standardWidth / density;
            final float scaleHeight = (float) displayMetrics.heightPixels / standardHeight / density;
            if (type == SCALE_WIDTH) {
                scale = scaleWidth;//按宽度缩放
            } else if (type == SCALE_HEIGHT) {
                scale = scaleHeight;//按高度缩放
            } else if (type == SCALE_BOTH) {
                scale = scaleWidth > scaleHeight ? scaleHeight : scaleWidth;//按最小比例缩放
            }
        }
    }

    //dp转化为px
    public int dp2px(float dp) {
        return (int) (dp * density * scale);
    }

    //px转化为dp
    public int px2dp(int px) {
        return (int) (px / density / scale);
    }

    //px转化为dp
    public float px2dp(float px) {
        return px / density / scale;
    }

    //需要调整的视图
    public void setViewsSize(View view) {
        setParams(view);
        if (view instanceof ViewGroup) {
            ViewGroup item = (ViewGroup) view;
            int amount = item.getChildCount();
            if (amount > 0) {
                for (int i = 0; i < amount; i++) {
                    setViewsSize(item.getChildAt(i));
                }
            }
        }
    }

    //设置View Params大小
    private void setParams(View view) {
        if (view.getTag() != null && view.getTag().equals("NO")) {
            return;
        }
        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (relativeParams.width > 0) {
                relativeParams.width = (int) (relativeParams.width * scale);
            }
            if (relativeParams.height > 0) {
                relativeParams.height = (int) (relativeParams.height * scale);
            }
            relativeParams.leftMargin = (int) (relativeParams.leftMargin * scale);
            relativeParams.rightMargin = (int) (relativeParams.rightMargin * scale);
            relativeParams.topMargin = (int) (relativeParams.topMargin * scale);
            relativeParams.bottomMargin = (int) (relativeParams.bottomMargin * scale);
            //安全距离动态适配
            if (view.getTag() != null && view.getTag().equals("margin")) {
                relativeParams.leftMargin = 0;
                relativeParams.rightMargin = 0;
                relativeParams.topMargin = 0;
                relativeParams.bottomMargin = 0;
            }
            view.setLayoutParams(relativeParams);
        } else if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            if (linearParams.width > 0) {
                linearParams.width = (int) (linearParams.width * scale);
            }
            if (linearParams.height > 0) {
                linearParams.height = (int) (linearParams.height * scale);
            }
            linearParams.leftMargin = (int) (linearParams.leftMargin * scale);
            linearParams.rightMargin = (int) (linearParams.rightMargin * scale);
            linearParams.topMargin = (int) (linearParams.topMargin * scale);
            linearParams.bottomMargin = (int) (linearParams.bottomMargin * scale);
            //安全距离动态适配
            if (view.getTag() != null && view.getTag().equals("margin")) {
                linearParams.leftMargin = 0;
                linearParams.rightMargin = 0;
                linearParams.topMargin = 0;
                linearParams.bottomMargin = 0;
            }
            view.setLayoutParams(linearParams);
        } else if (view.getLayoutParams() instanceof ScrollView.LayoutParams) {
            ScrollView.LayoutParams scrollParams = (ScrollView.LayoutParams) view.getLayoutParams();
            if (scrollParams.width > 0) {
                scrollParams.width = (int) (scrollParams.width * scale);
            }
            if (scrollParams.height > 0) {
                scrollParams.height = (int) (scrollParams.height * scale);
            }
            scrollParams.leftMargin = (int) (scrollParams.leftMargin * scale);
            scrollParams.rightMargin = (int) (scrollParams.rightMargin * scale);
            scrollParams.topMargin = (int) (scrollParams.topMargin * scale);
            scrollParams.bottomMargin = (int) (scrollParams.bottomMargin * scale);
            //安全距离动态适配
            if (view.getTag() != null && view.getTag().equals("margin")) {
                scrollParams.leftMargin = 0;
                scrollParams.rightMargin = 0;
                scrollParams.topMargin = 0;
                scrollParams.bottomMargin = 0;
            }
            view.setLayoutParams(scrollParams);
            if (view instanceof TextView) {
                TextView tv = (TextView) (view);
                tv.setMaxHeight((int) (tv.getHeight() * scale));
            }
        } else if (view.getLayoutParams() instanceof AbsListView.LayoutParams) {
            AbsListView.LayoutParams absparams = (AbsListView.LayoutParams) view.getLayoutParams();
            if (absparams.width > 0) {
                absparams.width = (int) (absparams.width * scale);
            }
            if (absparams.height > 0) {
                absparams.height = (int) (absparams.height * scale);
            }
            view.setLayoutParams(absparams);
        }
        //动态设置Padding
        int top = (int) (view.getPaddingTop() * scale);
        int bottom = (int) (view.getPaddingBottom() * scale);
        int left = (int) (view.getPaddingLeft() * scale);
        int right = (int) (view.getPaddingRight() * scale);
        //安全距离动态适配
        if (view.getTag() == null || !view.getTag().equals("padding")) {
            view.setPadding(left, top, right, bottom);
        } else {
            view.setPadding(0, 0, 0, 0);
        }
        //动态改变文字对象大小
        if (view instanceof TextView) {
            TextView tvTemp = (TextView) view;
            final int unit = TypedValue.COMPLEX_UNIT_PX;
            tvTemp.setTextSize(unit, tvTemp.getTextSize() * scale);
        }
        if (view instanceof ListView) {
            int dividerHeight = (int) (((ListView) view).getDividerHeight() * scale);
            ((ListView) view).setDividerHeight(dividerHeight);
        }
    }
}
