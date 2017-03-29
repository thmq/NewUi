package org.catroid.catrobat.newui.recycleviewlist.fragment;

import android.os.Bundle;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.recycleviewlist.adapter.LookAdapter;
import org.catroid.catrobat.newui.recycleviewlist.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class LookListFragment extends BaseRecyclerListFragment<LookInfo> {

    private static final String ARG_SECTION_NUMBER = "section_number_look_list";
    public static final String NAME = "LOOKS";

    public static BaseRecyclerListFragment newInstance(int sectionNumber) {
        BaseRecyclerListFragment fragment = new LookListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public RecyclerViewAdapter<LookInfo> createAdapter() {
        return new LookAdapter(new ArrayList<LookInfo>(), R.layout.list_item);
    }
}
