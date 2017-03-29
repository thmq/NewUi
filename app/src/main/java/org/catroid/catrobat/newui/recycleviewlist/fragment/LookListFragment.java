package org.catroid.catrobat.newui.recycleviewlist.fragment;

import android.os.Bundle;

import org.catroid.catrobat.newui.R;

/**
 * Created by Artur on 29.03.2017.
 */

public class LookListFragment extends BaseRecyclerListFragment {


    private static final String ARG_SECTION_NUMBER = "section_number_look_list";
    public static final String NAME = "LOOKS";

    public static BaseRecyclerListFragment newInstance(int sectionNumber) {
        BaseRecyclerListFragment fragment = new LookListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
}
