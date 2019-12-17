package com.exam.fs.push.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseConstraintLayoutView;
import com.exam.fs.push.databinding.ViewTitleBinding;

import cn.droidlover.xdroidbase.kit.AppUtils;

public class TitleView extends BaseConstraintLayoutView<ViewTitleBinding> {

    private String rightText, rightBtnText;
    private int rightImageRes;
    private int colorStyle = 0;
    private boolean isleftImageShow, isrightResIconisShow, righttextisShow, isrightBtnShow;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
        setColorStyle(colorStyle);
        initView();
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.TitleView, defStyleAttr, 0);
        try {
            colorStyle = array.getInt(R.styleable.TitleView_color_style, 0);
            isleftImageShow = array.getBoolean(R.styleable.TitleView_left_ico_back, true);
            rightText = array.getString(R.styleable.TitleView_right_text);
            rightBtnText = array.getString(R.styleable.TitleView_right_btn_text);
            rightImageRes = array.getResourceId(R.styleable.TitleView_right_icon_res, 0);
            righttextisShow = array.getBoolean(R.styleable.TitleView_right_text_ishow, true);
            isrightResIconisShow = array.getBoolean(R.styleable.TitleView_right_icon_res_ishow, true);
            isrightBtnShow = array.getBoolean(R.styleable.TitleView_right_btn_isShow, true);
        } finally {
            array.recycle();
        }
    }

    private void setColorStyle(int style) {
        if (style == 0) {
            setLeftImage(R.drawable.icon_img_back);
            setLeftTextColor(getResources().getColor(R.color.white));
            setRightTextColor(getResources().getColor(R.color.white));
        } else if (style == 1) {
            setLeftImage(R.drawable.icon_img_back);
            setLeftTextColor(getResources().getColor(R.color.white));
            setRightTextColor(getResources().getColor(R.color.white));
            setTitleBackgroundColor(AppUtils.getResource().getColor(R.color.colorTransparent));
        }
    }

    private void initView() {
        getBinding().ivBack.setVisibility(isleftImageShow ? VISIBLE : GONE);
        getBinding().ivRight.setVisibility(isrightResIconisShow ? VISIBLE : GONE);
        getBinding().tvRigth.setVisibility(righttextisShow ? VISIBLE : GONE);
        getBinding().btnButton.setVisibility(isrightBtnShow ? VISIBLE : GONE);
        if (rightImageRes > 0) {
            getBinding().ivRight.setImageResource(rightImageRes);
        }
        getBinding().tvRigth.setText(rightText);
        getBinding().btnButton.setText(rightBtnText);
    }

    public void setLeftImage(@DrawableRes int resId) {
        getBinding().ivBack.setImageResource(resId);
    }

    public void setTitleBackgroundColor(@ColorInt int color) {
        getBinding().root.setBackgroundColor(color);
    }

    public void setLeftTextColor(@ColorInt int color) {
        getBinding().tvCenterTitle.setTextColor(color);
    }

    public void setRightTextColor(@ColorInt int color) {
        getBinding().tvRigth.setTextColor(color);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_title;
    }

    public TextView getTitleTextView() {
        return getBinding().tvCenterTitle;
    }

    public ImageView getRightImageView() {
        return getBinding().ivRight;
    }

    public Button getRightButton(){
        return getBinding().btnButton;
    }

    public void setTitle(String title) {
        getBinding().tvCenterTitle.setText(title);
    }

    public void setTitle(@StringRes int resTitle) {
        getBinding().tvCenterTitle.setText(resTitle);
    }

    public void setBackClickListener(OnClickListener listener) {
        if (listener != null)
            getBinding().ivBack.setOnClickListener(listener);
    }

    public void setRightText(String text, OnClickListener listener) {
        getBinding().tvRigth.setText(text);
        getBinding().tvRigth.setOnClickListener(listener);
    }

    public void setRightText(@StringRes int text, OnClickListener listener) {
        getBinding().tvRigth.setText(text);
        getBinding().tvRigth.setOnClickListener(listener);
    }

    public void setRightBtnRes(@DrawableRes int rightBtnRes) {
        getBinding().ivRight.setImageResource(rightBtnRes);
    }

    public void setRightBtnRes(@DrawableRes int rightBtnRes, OnClickListener listener) {
        getBinding().ivRight.setImageResource(rightBtnRes);
        getBinding().ivRight.setOnClickListener(listener);
    }

    public void setRightBtnRes(OnClickListener listener) {
        getBinding().btnButton.setOnClickListener(listener);
    }

    public View getRootLayout() {
        return getBinding().root;
    }

    @Override
    protected void initData() {

    }
}