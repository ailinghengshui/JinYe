package com.hzjytech.operation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.hzjytech.operation.module.data.DataFragment;
import com.hzjytech.operation.module.frags.main.HomeFragment;
import com.hzjytech.operation.module.frags.main.MachineFragment;
import com.hzjytech.operation.module.frags.main.MeFragment;
import com.hzjytech.operation.module.frags.main.TaskFragment;

/**
 * Created by hehongcan on 2017/4/7.
 */
public class MainPageAdapter extends FragmentPagerAdapter {
    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new TaskFragment();
            case 2:
                return new DataFragment();
            case 3:
                return new MachineFragment();
            case 4:
                return new MeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
