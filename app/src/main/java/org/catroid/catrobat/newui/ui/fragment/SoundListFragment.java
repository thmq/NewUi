package org.catroid.catrobat.newui.ui.fragment;

import android.os.Bundle;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.ui.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.SoundAdapter;

import java.util.ArrayList;


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
        return new SoundAdapter(new ArrayList<SoundInfo>(), R.layout.list_item);
    }

    @Override
    protected SoundInfo copyItem(SoundInfo item) throws Exception {
        return null;
    }

    @Override
    protected void cleanupItem(SoundInfo item) throws Exception {

    }

    @Override
    protected SoundInfo createNewItem(String itemName) {
        return null;
    }

}
