package org.catroid.catrobat.newui.db.contentprovider;

import android.net.Uri;
import android.util.Log;

import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.db.util.DataContract.ProjectEntry;

public class ProjectContentDescription extends ContentDescription {
    @Override
    protected String getPath() {
        return DataContract.PATH_PROJECT;
    }

    @Override
    protected String getTableName() {
        return ProjectEntry.TABLE_NAME;
    }

    @Override
    protected Uri getItemsUri() {
        return ProjectEntry.PROJECT_URI;
    }

    @Override
    protected String getIdColumn() {
        return ProjectEntry._ID;
    }
}
