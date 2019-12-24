package com.exam.fs.push.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MonitorScrollView extends ScrollView {

    private OnScrollListener listener;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public MonitorScrollView(Context context) {
        super(context);
    }

    public MonitorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonitorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置接口
    public interface OnScrollListener{
        void onScroll(int scrollY,int oldScrollY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null){
            listener.onScroll(t,oldt);
        }
    }
}