package com.exam.fs.push.viewmodel.base;

import androidx.databinding.ObservableBoolean;

public class BaseActivityViewModel {
    public ObservableBoolean isNetError = new ObservableBoolean(false);
    public ObservableBoolean isLoading = new ObservableBoolean(true);
    public ObservableBoolean isShowContent = new ObservableBoolean(false);
}