package org.catroid.catrobat.newui.db.brigde;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.data.Sprite;
import org.catroid.catrobat.newui.db.fetchrequest.ChildCollectionFetchRequest;
import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.db.util.DataContract.SceneEntry;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.ListViewHolder;

import java.util.List;

public class SceneBridge extends DatabaseBridge<Scene> {
    public SceneBridge(Context context) {
        super(context);
    }

    @Override
    protected ContentValues serializeForDatabase(Scene scene) {
        ContentValues values = new ContentValues();

        values.put(SceneEntry.COLUMN_NAME, scene.getName());
        values.put(SceneEntry.COLUMN_PROJECT_ID, scene.getProjectId());

        return values;
    }

    @Override
    protected Scene unserializeFromDatabaseCursor(Cursor cursor) {
        Scene scene = new Scene();

        scene.setId(cursor.getInt(cursor.getColumnIndex(SceneEntry._ID)));
        scene.setName(cursor.getString(cursor.getColumnIndex(SceneEntry.COLUMN_NAME)));
        scene.setProjectId(cursor.getLong(cursor.getColumnIndex(SceneEntry.COLUMN_PROJECT_ID)));

        return scene;
    }

    @Override
    protected String[] getProjection() {
        return SceneEntry.getFullProjection();
    }

    @Override
    protected Uri getCollectionUri() {
        return SceneEntry.SCENE_URI;
    }

    @Override
    protected Uri getItemUri(long id) {
        return SceneEntry.getSceneUri(id);
    }

    @Override
    protected void beforeDestroy(Scene scene) {
        destroySprites(scene);
    }


    @Override
    protected void afterDestroy(Scene item) {

    }

    private void destroySprites(Scene scene) {
        SpriteBridge spriteBridge = new SpriteBridge(getContext());

        List<Sprite> sprites = spriteBridge.findAll(new ChildCollectionFetchRequest(DataContract.SpriteEntry.COLUMN_SCENE_ID, scene.getId()));

        for (Sprite sprite : sprites) {
            spriteBridge.delete(sprite);
        }
    }
}
