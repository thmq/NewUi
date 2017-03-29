package org.catroid.catrobat.newui.recycleviewlist.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.catroid.catrobat.newui.recycleviewlist.fragment.SpriteActivityFragment;

import java.util.List;

/**
 * Created by Artur on 29.03.2017.
 */

public class SpriteViewPagerAdapter extends FragmentPagerAdapter {

    FragmentManager mFragmentManager;
    private int mNumOfTabs;

    public SpriteViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mNumOfTabs = 3;
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int i) {
        return SpriteActivityFragment.newInstance(i);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }

    public void clearSelectedItems() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            ((SpriteActivityFragment) fragment).clearSelection();
        }
    }
}
