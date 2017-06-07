package org.catroid.catrobat.newui.ui.fragment;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.db.brigde.SceneBridge;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.ui.activity.SceneActivity;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.SceneAdapter;

public class SceneListFragment extends BaseRecyclerListFragment<Scene> {
    @Override
    public RecyclerViewAdapter<Scene> createAdapter() {
        SceneActivity activity = (SceneActivity) getActivity();

        SceneBridge bridge = new SceneBridge(activity);
        SceneAdapter adapter = new SceneAdapter(activity);

        adapter.startLoading(bridge, activity.getProjectId());

        return adapter;
    }

    @Override
    protected void addToList(Scene item) {
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
        // derp
    }

    @Override
    protected void renameItem(Scene item, String itemName) {
        item.setName(itemName);
    }

    @Override
    protected Scene createNewItem(String itemName) {
        Scene scene = new Scene();
        scene.setName(itemName);

        return scene;
    }

    @Override
    protected Scene createNewItem(String itemName, PathInfoFile pathInfoFile) {
        Scene scene = new Scene();
        scene.setName(itemName);

        return scene;
    }

    @Override
    public int getTabNameResource() {
        return R.string.tab_name_scene;
    }
}
