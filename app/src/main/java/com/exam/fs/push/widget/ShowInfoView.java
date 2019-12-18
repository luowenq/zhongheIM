package com.exam.fs.push.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseConstraintLayoutView;
import com.exam.fs.push.databinding.ViewShowInfoBinding;

import cn.droidlover.xdroidbase.kit.Kits;

public class ShowInfoView extends BaseConstraintLayoutView<ViewShowInfoBinding> {
    /**
     * 左边文字
     */
    private String leftText;
    /**
     * 左边图标
     */
    private Drawable leftIcon;
    /**
     * 左边padding
     */
    private float leftPadding;
    /**
     * 左边文字和icon的margin
     */
    private float leftDrawMargin;
    /**
     * 左边文字大小
     */
    private float leftTextSize;
    /**
     * 左边文字颜色
     */
    private int leftTextColor;
    /**
     * 左边icon是否显示
     */
    private boolean leftIconIsShow = false;
    /**
     * 右边文字
     */
    private String rightText;
    /**
     * 右边文字大小
     */
    private float rightTextSize;
    /**
     * 右边文字颜色
     */
    private int rightTextColor;
    /**
     * 右边icon
     */
    private Drawable rightIcon;
    /**
     * 右边padding
     */
    private float rightPadding;
    /**
     * 右边icon和文字的距离
     */
    private float rightIconMargin;
    /**
     * 右边文字距离左边文字的距离
     */
    private float rightTextLeftMargin;
    /**
     * 右边icon是否显示
     */
    private boolean rightIconIsShow = false;
    /**
     * 右边文字是显示
     */
    private boolean rightTextIsShow = false;
    /**
     * 背景颜色
     */
    private Drawable itemBackground;
    /**
     * 设置右边图片
     */
    private Drawable rightImg;

    private Context context;

    public ShowInfoView(Context context) {
        super(context, null);
        this.context = context;
    }

    public ShowInfoView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        init(attrs, 0);
    }

    public ShowInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ShowInfoView, defStyleAttr, 0);
        try {
            /** 左边文字*/
            leftText = array.getString(R.styleable.ShowInfoView_left_text);
            /** 左边图标*/
            leftIcon = array.getDrawable(R.styleable.ShowInfoView_left_icon);
            /** 左边padding*/
            leftPadding = array.getDimension(R.styleable.ShowInfoView_left_padding, 16);
            /** 左边文字和icon的margin*/
            leftDrawMargin = array.getDimension(R.styleable.ShowInfoView_left_draw_margin, 10);
            /** 左边文字大小*/
            leftTextSize = array.getDimension(R.styleable.ShowInfoView_left_text_size, 0);
            /** 左边文字颜色*/
            leftTextColor = array.getColor(R.styleable.ShowInfoView_left_text_color, getResources().getColor(R.color.color_666666));
            /** 左边icon是否显示*/
            leftIconIsShow = array.getBoolean(R.styleable.ShowInfoView_left_icon_is_show, false);
            /** 右边文字*/
            rightText = array.getString(R.styleable.ShowInfoView_right_txt);
            /** 右边文字大小*/
            rightTextSize = array.getDimension(R.styleable.ShowInfoView_right_text_size, 0);
            /** 右边文字颜色*/
            rightTextColor = array.getColor(R.styleable.ShowInfoView_right_text_color, getResources().getColor(R.color.color_999999));
            /** 右边icon*/
            rightIcon = array.getDrawable(R.styleable.ShowInfoView_right_icon);
            /** 右边padding*/
            rightPadding = array.getDimension(R.styleable.ShowInfoView_right_padding, 16);
            /** 右边icon和文字的距离*/
            rightIconMargin = array.getDimension(R.styleable.ShowInfoView_right_icon_margin, 10);
            /** 右边文字距离左边文字的距离*/
            rightTextLeftMargin = array.getDimension(R.styleable.ShowInfoView_right_text_left_margin, 120);
            /** 右边icon是否显示*/
            rightIconIsShow = array.getBoolean(R.styleable.ShowInfoView_right_icon_is_show, false);
            /** 右边文字是显示*/
            rightTextIsShow = array.getBoolean(R.styleable.ShowInfoView_right_text_is_show, false);
            /** 背景颜色*/
            itemBackground = array.getDrawable(R.styleable.ShowInfoView_item_background);
            /** 设置右边图片*/
            rightImg = array.getDrawable(R.styleable.ShowInfoView_right_img);
        } finally {
            array.recycle();
        }

        if (leftIconIsShow) {
            getBinding().imgLeft.setVisibility(VISIBLE);
        }
        if (rightIconIsShow) {
            getBinding().imgRight.setVisibility(VISIBLE);
        }

        setLeftIconIsShow(leftIconIsShow);
        setLeftText(leftText);
        setLeftTextColor(leftTextColor);
        setLeftTextSize(leftTextSize);
        setLeftIconMargin(leftDrawMargin);
        setLeftIcon(leftIcon);

        setRightIconIsShow(rightIconIsShow);
        setRightTextIsShow(rightTextIsShow);
        setRightTextColor(rightTextColor);
        setRightTextSize(rightTextSize);
        setRightIconMargin(rightIconMargin);
        setRightTextLeftMargin(rightTextLeftMargin);
        setRightIcon(rightIcon);
        setRightText(rightText);

        setBackgroundView(itemBackground);
        setPaddingView(leftPadding, rightPadding);
        setRightImg(rightImg);
    }

    public void setLeftIcon(Drawable icon) {
        if (icon == null) {
            return;
        }
        getBinding().imgLeft.setVisibility(VISIBLE);
        getBinding().imgLeft.setImageDrawable(icon);
    }

    public void setLeftIconIsShow(boolean isShow) {
        getBinding().imgLeft.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void setLeftText(String text) {
        if (!TextUtils.isEmpty(text)) {
            getBinding().tvLeft.setText(text);
        }
    }

    public void setLeftTextColor(int color) {
        getBinding().tvLeft.setTextColor(color);
    }

    public void setLeftTextSize(float size) {
        if (size > 0) {
            getBinding().tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public void setLeftIconMargin(float margin) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getBinding().imgLeft.getLayoutParams();
        layoutParams.setMargins(0, 0, Kits.Dimens.dpToPxInt(context, margin), 0);
        getBinding().imgLeft.setLayoutParams(layoutParams);
    }

    public void setPaddingView(float paddingLeft, float paddingRight) {
        getBinding().llBg.setPadding(Kits.Dimens.dpToPxInt(context, paddingLeft), 0, Kits.Dimens.dpToPxInt(context, paddingRight), 0);
    }

    public void setRightIcon(Drawable icon) {
        if (icon == null) {
            return;
        }
        getBinding().imgRight.setVisibility(VISIBLE);
        getBinding().imgRight.setImageDrawable(icon);
    }

    public void setRightIconIsShow(boolean isShow) {
        getBinding().imgRight.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void setRightText(String text) {
        if (!TextUtils.isEmpty(text)) {
            getBinding().tvRight.setVisibility(VISIBLE);
            getBinding().tvRight.setText(text);
        }
    }

    public void setRightTextIsShow(boolean rightTextIsShow) {
        getBinding().tvRight.setVisibility(rightTextIsShow ? VISIBLE : GONE);
    }

    public void setRightTextColor(int rightTextColor) {
        getBinding().tvRight.setTextColor(rightTextColor);
    }

    public void setRightTextSize(float rightTextSize) {
        if (rightTextSize > 0) {
            getBinding().tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        }
    }

    public void setRightIconMargin(float rightIconMargin) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getBinding().imgRight.getLayoutParams();
        layoutParams.setMargins(Kits.Dimens.dpToPxInt(context, rightIconMargin), 0, 0, 0);
        getBinding().imgRight.setLayoutParams(layoutParams);
    }

    public void setRightTextLeftMargin(float rightTextLeftMargin) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getBinding().tvRight.getLayoutParams();
        layoutParams.setMargins(Kits.Dimens.dpToPxInt(context, rightTextLeftMargin), 0, 0, 0);
        getBinding().tvRight.setLayoutParams(layoutParams);
    }

    public void setBackgroundView(Drawable bg) {
        if (bg != null) {
            getBinding().llBg.setBackground(bg);
        }
    }

    public ImageView getRightImageView(){
        return getBinding().imgRight;
    }

    public void setRightImg(Drawable rightImg){
        if(rightImg != null) {
            getBinding().rightImg.setVisibility(VISIBLE);
            getBinding().rightImg.setImageDrawable(rightImg);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_show_info;
    }

    @Override
    protected void initData() {

    }
}
