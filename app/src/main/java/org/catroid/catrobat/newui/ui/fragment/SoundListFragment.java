package org.catroid.catrobat.newui.ui.fragment;

import android.os.Bundle;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.io.FileInfo;
import org.catroid.catrobat.newui.io.StorageHandler;
import org.catroid.catrobat.newui.ui.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.SoundAdapter;
import org.catroid.catrobat.newui.utils.Utils;

import java.util.ArrayList;


public class SoundListFragment extends BaseRecyclerListFragment<SoundInfo> {

    private static final String ARG_SECTION_NUMBER = "section_number_sound_list";
    public static final String NAME = "Sounds";

    public static final String TAG = SoundListFragment.class.getSimpleName();

    @Override
    public String getTabName() {
        return NAME;
    }

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
        String name = Utils.getUniqueSoundName(item.getName(), mRecyclerViewAdapter.getItems());
        FileInfo fileInfo = null;
        if (item.getFileInfo() != null) {
            fileInfo = StorageHandler.copyFile(item.getFileInfo());
        }

        return new SoundInfo(name, fileInfo);
    }

    @Override
    protected void cleanupItem(SoundInfo item) throws Exception {
        // Currently nothing to do here!
    }

    @Override
    protected SoundInfo createNewItem(String itemName) {
        SoundInfo soundInfo = new SoundInfo(itemName, null);

        return soundInfo;
    }
}
