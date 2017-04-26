package org.catroid.catrobat.newui.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sprite implements Serializable, Cloneable {

    public static final String TAG = Sprite.class.getSimpleName();
    private static final long serialVersionUID = 1L;
    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String name;

    private List<LookInfo> lookList = new ArrayList<>();
    private List<SoundInfo> soundList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public List<LookInfo> getLookList() {
        return lookList;
    }

    public List<SoundInfo> getSoundList() {
        return soundList;
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
        return this.name.equals(sprite.name);
    }
}
