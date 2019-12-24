package com.exam.fs.push.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.PayServiceAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityPayBinding;
import com.exam.fs.push.model.bean.PayServiceBean;
import com.exam.fs.push.router.RouterTables;

import java.util.ArrayList;
import java.util.List;

@Route(path = RouterTables.PAGE_ACTIVITY_PAY)
public class PayActivity extends BaseActivity<ActivityPayBinding> {
    private PayServiceAdapter adapterService;
    private PayServiceAdapter adapterOther;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"支付");
        getBinding().titleView.setRightBtnRes(R.drawable.icon_top_right_more,v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_PAY_MANAGER).navigation();
        });

        getBinding().rvService.setLayoutManager(new GridLayoutManager(context,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapterService = new PayServiceAdapter(context);
        getBinding().rvService.setAdapter(adapterService);

        getBinding().rvOtherService.setLayoutManager(new GridLayoutManager(context,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapterOther = new PayServiceAdapter(context);
        getBinding().rvOtherService.setAdapter(adapterOther);

        List<PayServiceBean> listService = new ArrayList<>();
        listService.add(new PayServiceBean("信用卡还贷",R.drawable.icon_xingyongka));
        listService.add(new PayServiceBean("手机充值",R.drawable.icon_phone));
        listService.add(new PayServiceBean("生活缴费",R.drawable.icon_jiaofei));
        listService.add(new PayServiceBean("城市服务",R.drawable.icon_city_service));
        listService.add(new PayServiceBean("保险服务",R.drawable.icon_baoxian));
        adapterService.setData(listService);

        List<PayServiceBean> listOther = new ArrayList<>();
        listOther.add(new PayServiceBean("火车票机票",R.drawable.icon_che_piao));
        listOther.add(new PayServiceBean("吃喝玩乐",R.drawable.icon_wanle));
        listOther.add(new PayServiceBean("滴滴出行",R.drawable.icon_didi));
        adapterOther.setData(listOther);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }
}
