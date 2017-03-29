package org.catroid.catrobat.newui.recycleviewlist.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.ListItem;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.recycleviewlist.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.recycleviewlist.adapter.SoundsAdapter;
import org.catroid.catrobat.newui.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SoundListFragment extends BaseRecyclerListFragment<SoundInfo> {

    private static final String ARG_SECTION_NUMBER = "section_number_sound_list";
    public static final String NAME = "Sounds";

    public static BaseRecyclerListFragment newInstance(int sectionNumber) {
        BaseRecyclerListFragment fragment = new SoundListFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public RecyclerViewAdapter createAdapter() {
        return new SoundsAdapter(new ArrayList<SoundInfo>(), R.layout.list_item);
    }

}
