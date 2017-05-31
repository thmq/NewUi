package org.catroid.catrobat.newui.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragment;
import org.catroid.catrobat.newui.ui.fragment.LookListFragment;
import org.catroid.catrobat.newui.ui.fragment.SoundListFragment;
import org.catroid.catrobat.newui.ui.fragment.Tabable;
import org.catroid.catrobat.newui.ui.fragment.TabableFragment;

import java.util.ArrayList;
import java.util.List;

public class SpriteViewPagerAdapter extends FragmentPagerAdapter {
    private int mCurrentPosition;

    private List<TabableFragment> mFragments = new ArrayList<>();
    private AppCompatActivity mActivity;

    public SpriteViewPagerAdapter(AppCompatActivity activity) {
        super(activity.getSupportFragmentManager());
        mActivity = activity;

        setupFragments();
    }

    private void setupFragments() {
        mFragments.add(LookListFragment.newInstance(0));
        mFragments.add(SoundListFragment.newInstance(1));
    }

    @Override
    public TabableFragment getItem(int position) {
        return getTabableAtPosition(position);
    }

    private TabableFragment getTabableAtPosition(int position) {
        if (0 <= position && position < mFragments.size()) {
            return mFragments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        TabableFragment fragment = getTabableAtPosition(position);

        if (fragment != null) {
            int tabNameResource = fragment.getTabNameResource();

            return mActivity.getString(tabNameResource);
        } else {
            return null;
        }
    }

    public void onPageSelected(int position) {
        mCurrentPosition = position;

        clearSelectedItems();
    }

    public void onAddButtonClicked() {
        BaseRecyclerListFragment fragment = getCurrentFragment();

        fragment.onAddButtonClicked();
    }

    protected TabableFragment getCurrentFragment() {
        return (TabableFragment) getItem(mCurrentPosition);
    }

    protected void clearSelectedItems() {
        for (TabableFragment fragment : mFragments) {
            fragment.clearSelection();
        }
    }
}
