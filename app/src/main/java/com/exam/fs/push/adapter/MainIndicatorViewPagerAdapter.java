package com.exam.fs.push.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.exam.fs.push.R;
import com.exam.fs.push.fragment.AddressFragment;
import com.exam.fs.push.fragment.FoundFragment;
import com.exam.fs.push.fragment.MessageFragment;
import com.exam.fs.push.fragment.MinFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainIndicatorViewPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private Context context;
    private List<String> titles = new ArrayList<>();
    private List<Integer> images = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    public MainIndicatorViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
        fragments.add(MessageFragment.newInstance());
        fragments.add(AddressFragment.newInstance());
        fragments.add(FoundFragment.newInstance());
        fragments.add(MinFragment.newInstance());
        titles.add("消息");
        titles.add("通讯录");
        titles.add("发现");
        titles.add("我的");
        images.add(R.drawable.sel_tab_message);
        images.add(R.drawable.sel_tab_address);
        images.add(R.drawable.set_tab_foun);
        images.add(R.drawable.sel_tab_min);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_item_main_tab, container, false);
            TextView tvTab = convertView.findViewById(R.id.tv_tab);
            tvTab.setText(titles.get(position));
            Drawable drawable = context.getResources().getDrawable(images.get(position));
            tvTab.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return fragments.get(position);
    }
}