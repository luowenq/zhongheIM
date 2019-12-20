package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.CityAdapter;
import com.exam.fs.push.adapter.ProvinceAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityAddrSetBinding;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.model.bean.CityBean;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.ReadAssetsFileUtil;
import com.exam.fs.push.viewmodel.EditUserInfoViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.droidlover.xdroidbase.base.ItemCallback;

/**
 * 设置地区
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SET_ADDR)
public class AddrSetActivity extends BaseActivity<ActivityAddrSetBinding> {
    private static final String TAG = "AddrSetActivity";

    private EditUserInfoViewModel viewModel;
    private String province;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"设置地区");
        viewModel = new EditUserInfoViewModel();
        getBinding().setViewModel(viewModel);

        String json = ReadAssetsFileUtil.getJson(this,"city.json");
        List<CityBean> cityBeans = new Gson().fromJson(json, new TypeToken<List<CityBean>>(){}.getType());

        province = getIntent().getStringExtra("city");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        getBinding().rvCity.setLayoutManager(linearLayoutManager);

        CityAdapter adapter = new CityAdapter(this, new ItemCallback<CityBean.AreaBean>() {
            @Override
            public void onItemClick(int position, CityBean.AreaBean model) {
                super.onItemClick(position, model);
                viewModel.updateUserInfo("","","","",""
                        ,province,model.getName(),"",true);
            }
        });
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(this,cityBeans);

        if(!TextUtils.isEmpty(province)){
            for(int i = 0;i<cityBeans.size();i++) {
                if(cityBeans.get(i).getName().equals(province)) {
                    adapter.setData(cityBeans.get(i).getAreaDOList());
                }
            }
            getBinding().rvCity.setAdapter(adapter);
        }else {
            getBinding().rvCity.setAdapter(provinceAdapter);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusBean busBean) {
        if (busBean.getTag() == EventBusBean.TAG_FINISH_ADDR_SELECT_PAGE) {
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_addr_set;
    }
}
