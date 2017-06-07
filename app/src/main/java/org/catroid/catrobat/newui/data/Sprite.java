package org.catroid.catrobat.newui.data;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.db.brigde.PersistableRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sprite implements Serializable, CopyPasteable, PersistableRecord {

    public static final String TAG = Sprite.class.getSimpleName();
    private static final long serialVersionUID = 1L;
    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute

    private long mId;
    private String mName;
    private long mSceneId;

    @Override
    public void setId(long id) {
        mId = id;
    }

    @Override
    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sprite)) {
            return false;
        }

        if (object == this) {
            return true;
        }

        Sprite sprite = (Sprite) object;

        //TODO: check when a sprite is equal to another.
        return this.mName.equals(sprite.mName);
    }

    @Override
    public void prepareForClipboard() throws Exception {
    }

    @Override
    public void cleanupFromClipboard() throws Exception {

    }

    @Override
    public Sprite clone() throws CloneNotSupportedException {
        return null;
    }


    @Override
    public void beforeDestroy() {

    }

    @Override
    public void afterDestroy() {

    }

    public long getSceneId() {
        return mSceneId;
    }

    public void setSceneId(long sceneId) {
        mSceneId = sceneId;
    }
}
