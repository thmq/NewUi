package org.catroid.catrobat.newui.db.contentprovider;

import android.net.Uri;

import org.catroid.catrobat.newui.db.util.DataContract;

public class SpriteContentDescription extends ContentDescription {
    @Override
    String getPath() {
        return DataContract.PATH_SPRITE;
    }

    @Override
    String getTableName() {
        return DataContract.SpriteEntry.TABLE_NAME;
    }

    @Override
    Uri getItemsUri() {
        return DataContract.SpriteEntry.SPRITE_URI;
    }

    @Override
    String getIdColumn() {
        return DataContract.SpriteEntry._ID;
    }
}
