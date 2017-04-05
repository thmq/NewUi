package org.catroid.catrobat.newui.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragment;
import org.catroid.catrobat.newui.ui.fragment.LookListFragment;
import org.catroid.catrobat.newui.ui.fragment.SoundListFragment;

import java.util.List;

public class SpriteViewPagerAdapter extends FragmentPagerAdapter {

    FragmentManager mFragmentManager;
    private int mNumOfTabs;

    public SpriteViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mNumOfTabs = 2;
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LookListFragment.newInstance(position);
            case 1:
                return SoundListFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return LookListFragment.NAME;
            case 1:
                return SoundListFragment.NAME;
            default:
                return "";
        }
    }

    public void clearSelectedItems() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            ((BaseRecyclerListFragment) fragment).clearSelection();
        }
    }
}
