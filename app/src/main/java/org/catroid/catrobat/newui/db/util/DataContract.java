package org.catroid.catrobat.newui.db.util;

import android.net.Uri;
import android.provider.BaseColumns;

import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.data.Scene;

public class DataContract {
    public static final String CONTENT_AUTHORITY = "org.catroid.catrobat.newui";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_PROJECT = "projects";
    public static final String PATH_SCENE = "scenes";
    public static final String PATH_SPRITE = "sprite";
    public static final String PATH_LOOK = "looks";
    public static final String PATH_SOUND = "sound";

    public static class ProjectEntry implements BaseColumns {
        public static final Uri PROJECT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROJECT).build();

        public static final String TABLE_NAME = "projects";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_INFO_TEXT = "info_text";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_FAVORITE = "favorite";

        public static Uri getProjectUri(Project project) {
            return PROJECT_URI.buildUpon().appendPath(String.valueOf(project.getId())).build();
        }

        public static Uri getProjectUri(long id) {
            return PROJECT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String[] getFullProjection() {
            return new String[]{
                    _ID,
                    COLUMN_NAME,
                    COLUMN_INFO_TEXT,
                    COLUMN_DESCRIPTION,
                    COLUMN_FAVORITE
            };
        }
    }

    public static class SceneEntry implements BaseColumns {
        public static final Uri SCENE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCENE).build();

        public static final String TABLE_NAME = "scenes";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PROJECT_ID = "project_id";

        public static Uri getSceneUri(Scene project) {
            return getSceneUri(project.getId());
        }

        public static Uri getSceneUri(long id) {
            return SCENE_URI.buildUpon().appendPath(String.valueOf(id)).build();
        }

        public static String[] getFullProjection() {
            return new String[]{
                    _ID,
                    COLUMN_NAME,
                    COLUMN_PROJECT_ID
            };
        }
    }

}
