package org.catroid.catrobat.newui.db.contentprovider;

import android.net.Uri;
import android.util.Log;

import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.db.util.DataContract.SceneEntry;

public class SceneContentDescription extends ContentDescription {
    @Override
    protected String getPath() {
        return DataContract.PATH_SCENE;
    }

    @Override
    protected String getTableName() {
        return SceneEntry.TABLE_NAME;
    }

    @Override
    protected Uri getItemsUri() {
        return SceneEntry.SCENE_URI;
    }

    @Override
    protected String getIdColumn() {
        return SceneEntry._ID;
    }
}
