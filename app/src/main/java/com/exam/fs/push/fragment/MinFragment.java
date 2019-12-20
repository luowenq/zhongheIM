package com.exam.fs.push.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentMinBinding;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.net.Api;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.utils.LoadImage;
import com.exam.fs.push.utils.LoginOut;
import com.exam.fs.push.viewmodel.MineViewModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.droidlover.xdroidbase.kit.ToastManager;
import me.shihao.library.XStatusBarHelper;

/**
 * 我的
 * A simple {@link Fragment} subclass.
 */
public class MinFragment extends BaseFragment<FragmentMinBinding> {
    private static final String TAG = "MinFragment";
    private MineViewModel viewModel;

    public MinFragment() {
    }

    public static MinFragment newInstance() {
        MinFragment mf = new MinFragment();
        Bundle bundle = new Bundle();
        mf.setArguments(bundle);
        return mf;
    }

    @Override
    public void initData(Bundle bundle) {
        XStatusBarHelper.forceFitsSystemWindows(context);
        XStatusBarHelper.immersiveStatusBar(context);
        XStatusBarHelper.setHeightAndPadding(context, getBinding().clBg);
        viewModel = new MineViewModel();
        getBinding().setViewModel(viewModel);
        UserModel userModel = Config.getUsers();
        if(userModel != null) {
            viewModel.nickName.set(userModel.nickname);
            viewModel.username.set(userModel.username);
            LoadImage.loadHeadImage(getBinding().ivPhoto, Api.imageUrl+userModel.headIcon);
        }

        getBinding().btnSetting.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_SETTING).navigation();
        });
        getBinding().btnPay.setOnClickListener(v -> {
//            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_PAY).navigation();
            ToastManager.showShort(context,R.string.app_no_function);
        });
        getBinding().btnCollection.setOnClickListener(v -> {
            ToastManager.showShort(context,R.string.app_no_function);
        });
        getBinding().btnPics.setOnClickListener(v -> {
            ToastManager.showShort(context,R.string.app_no_function);
        });
        getBinding().btnBiaoqing.setOnClickListener(v -> {
            ToastManager.showShort(context,R.string.app_no_function);
        });
        getBinding().btnLoginOut.setOnClickListener(v -> {
            LoginOut.loginOut();
            context.finish();
        });
        getBinding().btnChangeAccount.setOnClickListener(v -> {

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_min;
    }

    @Override
    protected void stop() {
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusBean busBean) {
        if (busBean.getTag() == EventBusBean.TAG_REFRESH_USERINFO) {
            UserModel userModel = Config.getUsers();
            if(userModel != null) {
                viewModel.nickName.set(userModel.nickname);
                LoadImage.loadHeadImage(getBinding().ivPhoto, Api.imageUrl + userModel.headIcon);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}