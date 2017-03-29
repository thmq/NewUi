package org.catroid.catrobat.newui.recycleviewlist.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.ListItem;
import org.catroid.catrobat.newui.recycleviewlist.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.utils.Utils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Artur on 29.03.2017.
 */

public class SoundListFragment extends BaseRecyclerListFragment {

    private static final String ARG_SECTION_NUMBER = "section_number_sound_list";
    public static final String NAME = "Sounds";

    public static BaseRecyclerListFragment newInstance(int sectionNumber) {
        BaseRecyclerListFragment fragment = new SoundListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_view, container, false);

        List<ListItem> items = Utils.getItemList();
        Collections.reverse(items);
        mRecyclerViewAdapter = new RecyclerViewAdapter(items, R.layout.list_item);
        mRecyclerViewAdapter.addObserver(this);

        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return mRecyclerView;
    }
}
