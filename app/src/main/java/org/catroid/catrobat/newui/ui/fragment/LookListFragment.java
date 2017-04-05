package org.catroid.catrobat.newui.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.dialog.NewItemDialog;
import org.catroid.catrobat.newui.io.FileInfo;
import org.catroid.catrobat.newui.io.StorageHandler;
import org.catroid.catrobat.newui.ui.adapter.LookAdapter;
import org.catroid.catrobat.newui.ui.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LookListFragment extends BaseRecyclerListFragment<LookInfo> implements NewItemDialog.NewItemInterface {
    private static final String ARG_SECTION_NUMBER = "section_number_look_list";

    public static final String TAG = LookListFragment.class.getSimpleName();

    @Override
    public int getTabNameResource() {
        return R.string.tab_name_looks;
    }

    public static BaseRecyclerListFragment newInstance(int sectionNumber) {
        BaseRecyclerListFragment fragment = new LookListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        Log.d(TAG, "New Instance created (" + System.identityHashCode(fragment) + ")");

        return fragment;
    }

    @Override
    public RecyclerViewAdapter<LookInfo> createAdapter() {
        Log.d(TAG, "Adapter created! (" + System.identityHashCode(this) + ")");

        return new LookAdapter(new ArrayList<LookInfo>(), R.layout.list_item);
    }

    @Override
    protected LookInfo copyItem(LookInfo item) throws Exception {
        String name = Utils.getUniqueLookName(item.getName(), mRecyclerViewAdapter.getItems());
        FileInfo fileInfo = StorageHandler.copyFile(item.getFileInfo());

        return new LookInfo(name, fileInfo);
    }

    @Override
    protected void cleanupItem(LookInfo item) throws Exception {
        item.cleanup();
    }

    @Override
    protected LookInfo createNewItem(String itemName) {
        Log.d(TAG, "Creating new item! (" + System.identityHashCode(this) + ")");
        String uniqueLookName = Utils.getUniqueLookName(itemName, mRecyclerViewAdapter.getItems());

        FileInfo fileInfo = createImage();
        return new LookInfo(uniqueLookName, fileInfo);
    }


    private FileInfo createImage() {
        StorageHandler.setupDirectoryStructure();

        String dir = Utils.getImageDirectory().getAbsolutePath();
        File file;
        try {
            file = StorageHandler.getUniqueFile("look.png", dir);
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

        return new FileInfo(Utils.getImageDirectory(), file.getName());
    }
}
