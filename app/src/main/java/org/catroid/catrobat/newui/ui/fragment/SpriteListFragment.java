package org.catroid.catrobat.newui.ui.fragment;

import org.catroid.catrobat.newui.copypaste.Clipboard;
import org.catroid.catrobat.newui.data.Sprite;
import org.catroid.catrobat.newui.db.brigde.SpriteBridge;
import org.catroid.catrobat.newui.io.PathInfoFile;
import org.catroid.catrobat.newui.ui.activity.SpriteActivity;
import org.catroid.catrobat.newui.ui.activity.SpriteDetailActivity;
import org.catroid.catrobat.newui.ui.adapter.SpriteAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;

public class SpriteListFragment extends BaseRecyclerListFragment<Sprite>  {

    @Override
    public RecyclerViewAdapter<Sprite> createAdapter() {
        SpriteActivity activity = (SpriteActivity) getActivity();

        SpriteBridge bridge = new SpriteBridge(activity);
        SpriteAdapter adapter = new SpriteAdapter(activity);

        adapter.startLoading(bridge, activity.getSceneId());

        return adapter;
    }

    @Override
    protected void addToList(Sprite item) {

    }

    @Override
    protected String getItemName(Sprite sprite) {
        return sprite.getName();
    }

    @Override
    protected Clipboard.ItemType getItemType() {
        return Clipboard.ItemType.SPRITE;
    }

    @Override
    protected Sprite copyItem(Sprite item) throws Exception {
        return null;
    }

    @Override
    protected void cleanupItem(Sprite item) throws Exception {

    }

    @Override
    protected void renameItem(Sprite sprite, String name) {
        sprite.setName(name);
    }

    @Override
    protected Sprite createNewItem(String itemName, PathInfoFile pathInfoFile) {
        return null;
    }

    @Override
    protected Sprite createNewItem(String name) {
        Sprite sprite = new Sprite();

        sprite.setName(name);

        return sprite;
    }

    @Override
    public int getTabNameResource() {
        return 0;
    }
}
