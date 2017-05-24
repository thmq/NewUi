package org.catroid.catrobat.newui.data;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.db.brigde.PersistableRecord;
import org.catroid.catrobat.newui.db.brigde.ProjectBridge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scene implements Serializable, CopyPasteable, PersistableRecord {

    public static final String TAG = Scene.class.getSimpleName();
    private static final long serialVersionUID = 1L;
    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String mName;
    private long mId;
    private long mProjectId;

    private List<Sprite> mSprites = new ArrayList<>();

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Scene)) {
            return false;
        }

        if (object == this) {
            return true;
        }

        Scene scene = (Scene) object;

        //TODO: check when a scene is equal to another.
        return this.mName.equals(scene.mName);
    }

    @Override
    public void prepareForClipboard() throws Exception {
        for (Sprite sprite : mSprites) {
            sprite.prepareForClipboard();
        }
    }

    @Override
    public void cleanupFromClipboard() throws Exception {
        for (Sprite sprite : mSprites) {
            sprite.cleanupFromClipboard();
        }
    }

    @Override
    public Scene clone() throws CloneNotSupportedException {
        Scene clonedScene = (Scene) super.clone();

        List<Sprite> sprites = new ArrayList<>();
        for (Sprite sprite : mSprites) {
            sprites.add(sprite.clone());
        }
        clonedScene.mSprites = sprites;

        return clonedScene;
    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public void setId(long id) {
        mId = id;
    }

    public long getProjectId() {
        return mProjectId;
    }

    public void setProjectId(long mProjectId) {
        this.mProjectId = mProjectId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
