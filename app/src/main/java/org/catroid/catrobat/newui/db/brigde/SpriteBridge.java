package org.catroid.catrobat.newui.db.brigde;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.catroid.catrobat.newui.data.Sprite;
import org.catroid.catrobat.newui.db.util.DataContract.SpriteEntry;

public class SpriteBridge extends DatabaseBridge<Sprite> {
    public SpriteBridge(Context context) {
        super(context);
    }

    @Override
    protected ContentValues serializeForDatabase(Sprite sprite) {
        ContentValues values = new ContentValues();

        values.put(SpriteEntry.COLUMN_NAME, sprite.getName());
        values.put(SpriteEntry.COLUMN_SCENE_ID, sprite.getSceneId());

        return values;
    }

    @Override
    protected Sprite unserializeFromDatabaseCursor(Cursor cursor) {
        Sprite sprite = new Sprite();

        sprite.setId(cursor.getInt(cursor.getColumnIndex(SpriteEntry._ID)));
        sprite.setName(cursor.getString(cursor.getColumnIndex(SpriteEntry.COLUMN_NAME)));
        sprite.setSceneId(cursor.getLong(cursor.getColumnIndex(SpriteEntry.COLUMN_SCENE_ID)));

        return sprite;
    }

    @Override
    protected String[] getProjection() {
        return SpriteEntry.getFullProjection();
    }

    @Override
    protected Uri getCollectionUri() {
        return SpriteEntry.SPRITE_URI;
    }

    @Override
    protected Uri getItemUri(long id) {
        return SpriteEntry.getSpriteUri(id);
    }

    @Override
    protected void beforeDestroy(Sprite item) {

    }

    @Override
    protected void afterDestroy(Sprite item) {

    }
}
