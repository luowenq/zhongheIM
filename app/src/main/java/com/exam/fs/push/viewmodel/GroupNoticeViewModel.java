package com.exam.fs.push.viewmodel;

import androidx.databinding.ObservableField;

import com.exam.fs.push.callback.ViewModelLifecycle;
import com.exam.fs.push.viewmodel.base.BaseActivityViewModel;

public class GroupNoticeViewModel extends BaseActivityViewModel implements ViewModelLifecycle {
    public ObservableField<String> nickname = new ObservableField<>("昵称");
    public ObservableField<String> time = new ObservableField<>("2019.11.11  11：11");
    public ObservableField<String> content = new ObservableField<>("纵算水尽山穷，叶落成空，那老去的年华依旧可以风姿万种。纵算岁月朦胧，天涯西东，依然可以觅寻当年遗落的影踪。忽然间红尘梦醒，又是一场盛宴散去。将万千心事寄放天涯的年龄早已过去，那份年少时的冲动，也被岁月消磨得荡然无存。早已擦肩而过的何必追忆，反反复复，终究还是和昨天告了别。");

    @Override
    public void onDestroy() {

    }
}
