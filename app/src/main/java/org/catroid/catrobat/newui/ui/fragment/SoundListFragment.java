package org.catroid.catrobat.newui.ui.fragment;

import android.os.Bundle;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;
import org.catroid.catrobat.newui.ui.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.SoundAdapter;
import org.catroid.catrobat.newui.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class SoundListFragment extends BaseRecyclerListFragment<SoundInfo> {

    public static final String TAG = SoundListFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number_sound_list";

    public static BaseRecyclerListFragment newInstance(int sectionNumber) {
        BaseRecyclerListFragment fragment = new SoundListFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getTabNameResource() {
        return R.string.tab_name_sounds;
    }

    @Override
    public RecyclerViewAdapter createAdapter() {
        return new SoundAdapter(new ArrayList<SoundInfo>(), R.layout.list_item);
    }

    @Override
    protected SoundInfo copyItem(SoundInfo item) throws Exception {
        String name = Utils.getUniqueSoundName(item.getName(), mRecyclerViewAdapter.getItems());
        PathInfoFile pathInfo = null;
        if (item.getPathInfo() != null) {
            pathInfo = StorageHandler.copyFile(item.getPathInfo());
        }

        return new SoundInfo(name, pathInfo);
    }

    @Override
    protected void cleanupItem(SoundInfo item) throws Exception {
        // Currently nothing to do here!
    }

    @Override
    public boolean isNameValid(String itemName) {
        return (Utils.isItemNameUnique(itemName, mRecyclerViewAdapter.getItems()) && itemName != null);
    }

    @Override
    public void renameItem(String itemName) {
        List<SoundInfo> item = mRecyclerViewAdapter.getSelectedItems();
        item.get(0).setName(itemName);
        mRecyclerViewAdapter.clearSelection();
    }

    @Override
    protected SoundInfo createNewItem(String itemName) {
        String uniqueSoundName = Utils.getUniqueSoundName(itemName,
                mRecyclerViewAdapter.getItems());

        SoundInfo soundInfo = new SoundInfo(uniqueSoundName, null);

        return soundInfo;
    }
}