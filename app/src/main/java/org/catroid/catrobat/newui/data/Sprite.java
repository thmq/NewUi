package org.catroid.catrobat.newui.data;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sprite implements Serializable, CopyPasteable {

    public static final String TAG = Sprite.class.getSimpleName();
    private static final long serialVersionUID = 1L;
    //TODO: uncomment after XStream integration
    //@XStreamAsAttribute
    private String mName;

    private List<LookInfo> mLooks = new ArrayList<>();
    private List<SoundInfo> mSounds = new ArrayList<>();

    public String getName() {
        return mName;
    }

    public List<LookInfo> getLooks() {
        return mLooks;
    }

    public void setLooks(List<LookInfo> looks) {
        mLooks = looks;
    }


    public List<SoundInfo> getSounds() {
        return mSounds;
    }

    public void setSounds(List<SoundInfo> sounds) {
        mSounds = sounds;
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
        for (LookInfo look : mLooks) {
            look.prepareForClipboard();
        }

        for (SoundInfo sound : mSounds) {
            sound.prepareForClipboard();
        }
    }

    @Override
    public void cleanupFromClipboard() throws Exception {
        for (LookInfo look : mLooks) {
            look.cleanupFromClipboard();
        }

        for (SoundInfo sound : mSounds) {
            sound.cleanupFromClipboard();
        }
    }

    @Override
    public Sprite clone() throws CloneNotSupportedException {
        Sprite clonedSprite = (Sprite) super.clone();

        List<LookInfo> looks = new ArrayList<>();
        for (LookInfo look : mLooks) {
            looks.add(look.clone());
        }
        clonedSprite.mLooks = looks;

        List<SoundInfo> sounds = new ArrayList<>();
        for (SoundInfo sound : mSounds) {
            sounds.add(sound.clone());
        }
        clonedSprite.mSounds = sounds;

        return clonedSprite;
    }
}
