package org.catroid.catrobat.newui.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.dialog.NewItemDialog;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.io.StorageHandler;
import org.catroid.catrobat.newui.ui.AddItemActivity;
import org.catroid.catrobat.newui.ui.adapter.LookAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.featureDiscovery.SpriteViewFeatureDiscoveryManager;
import org.catroid.catrobat.newui.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LookListFragment extends TabableFragment<LookInfo>
        implements NewItemDialog.NewItemInterface {
    public static final String TAG = LookListFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number_look_list";

    public static LookListFragment newInstance(int sectionNumber) {
        LookListFragment fragment = new LookListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (shouldPresentFeatureDiscovery()) {
            SpriteViewFeatureDiscoveryManager.create(this).start();
        }
    }

    @Override
    public int getTabNameResource() {
        return R.string.tab_name_looks;
    }

    @Override
    public RecyclerViewAdapter<LookInfo> createAdapter() {
        //TODO change again
        List<LookInfo> lookInfoList = new ArrayList<LookInfo>();

        for (int i = 0; i < 5; i++) {
            lookInfoList.add(new LookInfo("Item " + i, createImage()));
        }

        return new LookAdapter(lookInfoList, R.layout.list_item);

    }

    @Override
    protected void addToList(LookInfo item) {
        mRecyclerViewAdapter.addItem(item);

    }

    @Override
    protected String getItemName(LookInfo item) {
        return item.getName();
    }

    @Override
    protected Clipboard.ItemType getItemType() {
        return Clipboard.ItemType.LOOK;
    }

    @Override
    protected LookInfo copyItem(LookInfo item) throws Exception {
        String name = Utils.getUniqueLookName(item.getName(), mRecyclerViewAdapter.getItems());
        PathInfoFile pathInfo = StorageHandler.copyFile(item.getPathInfo());

        return new LookInfo(name, pathInfo);
    }

    @Override
    protected void cleanupItem(LookInfo item) throws Exception {
        item.cleanup();
    }

    @Override
    protected void renameItem(LookInfo item, String itemName) {
        item.setName(itemName);
    }

    @Override
    protected LookInfo createNewItem(String itemName) {
        String uniqueLookName = Utils.getUniqueLookName(itemName, mRecyclerViewAdapter.getItems());

        PathInfoFile pathInfo = createImage();
        return new LookInfo(uniqueLookName, pathInfo);
    }

    private PathInfoFile createImage() {
        StorageHandler.setupDirectoryStructure();

        String dir = Utils.getImageDirectory().getAbsolutePath();
        File file;
        try {
            file = StorageHandler.getUniqueFile("bg_260.png", dir);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.d(TAG, "File not created: " + file.getAbsolutePath());
            e.printStackTrace();
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        InputStream is = getResources().openRawResource(R.raw.bg_260);
        Bitmap bmp = BitmapFactory.decodeStream(is);

        try {
            StorageHandler.exportBitmapToFile(bmp, file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new PathInfoFile(Utils.getImageDirectory(), file.getName());
    }

    @Override
    protected LookInfo createNewItem(String itemName, PathInfoFile pathInfoFile) {
        String uniqueLookName = Utils.getUniqueLookName(itemName, mRecyclerViewAdapter.getItems());
        return new LookInfo(uniqueLookName, pathInfoFile);
    }

    private boolean shouldPresentFeatureDiscovery() {
        return false;
    }
}
