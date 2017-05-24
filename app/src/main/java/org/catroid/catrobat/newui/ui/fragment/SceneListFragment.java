package org.catroid.catrobat.newui.ui.fragment;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.db.brigde.SceneBridge;
import org.catroid.catrobat.newui.ui.activity.SceneActivity;
import org.catroid.catrobat.newui.ui.adapter.recyclerview.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.recyclerview.SceneAdapter;

public class SceneListFragment extends BaseRecyclerListFragment<Scene> {

    @Override
    public int getTabNameResource() {
        return 0;
    }

    @Override
    public RecyclerViewAdapter<Scene> createAdapter() {
        SceneActivity activity = (SceneActivity) getActivity();

        SceneBridge bridge = new SceneBridge(activity);
        SceneAdapter adapter = new SceneAdapter(activity);

        adapter.startLoading(bridge, activity.getProjectId());

        return adapter;
    }

    @Override
    protected String getItemName(Scene item) {
        return null;
    }

    @Override
    protected Clipboard.ItemType getItemType() {
        return null;
    }

    @Override
    protected Scene copyItem(Scene item) throws Exception {
        return null;
    }

    @Override
    protected void cleanupItem(Scene item) throws Exception {

    }

    @Override
    protected void renameItem(Scene item, String itemName) {

    }

    @Override
    protected Scene createNewItem(String itemName) {
        return null;
    }
}
