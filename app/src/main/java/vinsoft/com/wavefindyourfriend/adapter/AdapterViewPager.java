package vinsoft.com.wavefindyourfriend.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by DONG on 04-Apr-17.
 */

public class AdapterViewPager extends FragmentStatePagerAdapter {
    List<Fragment> fragments;
    private final String[] TITLES = {"Chat","Contact" };
    public AdapterViewPager(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
