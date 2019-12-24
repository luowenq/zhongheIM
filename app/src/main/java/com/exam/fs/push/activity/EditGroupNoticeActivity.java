package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityEditGroupNoticeBinding;
import com.exam.fs.push.router.RouterTables;

import cn.droidlover.xdroidbase.kit.ToastManager;

/**
 * 群公告编辑
 */
@Route(path = RouterTables.PAGE_ACTIVITY_EDIT_GROUP_NOTICE)
public class EditGroupNoticeActivity extends BaseActivity<ActivityEditGroupNoticeBinding> {
    private String content;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"编辑群公告");
        content = getIntent().getStringExtra("content");
        getBinding().etContent.setText(content);
        getBinding().titleView.getRightButton().setEnabled(TextUtils.isEmpty(content)?false:true);
        getBinding().etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                content = s.toString();
                if(content.length()>0){
                    getBinding().tvNum.setText(content.length()<=150?(content.length() + ""):"150");
                    if(content.length()>150){
                        getBinding().etContent.setText(content.substring(0,150));
                        getBinding().etContent.setSelection(content.length());
                        ToastManager.showShort(context,R.string.app_edit_group_notice_max_length_toast);
                    }
                    getBinding().titleView.getRightButton().setEnabled(true);
                }else {
                    getBinding().titleView.getRightButton().setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_group_notice;
    }
}
