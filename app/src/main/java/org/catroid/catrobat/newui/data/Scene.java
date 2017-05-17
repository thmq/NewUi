package org.catroid.catrobat.newui.data;

import java.io.Serializable;
import java.util.List;

public class Scene implements Serializable, Cloneable {

    public static final String TAG = Scene.class.getSimpleName();
    private static final long serialVersionUID = 1L;
    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String name;
    private List<Sprite> spriteList;

    public String getName() {
        return name;
    }

    public List<Sprite> getSpriteList() {
        return spriteList;
    }

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
        return this.name.equals(scene.name);
    }
}
