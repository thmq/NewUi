package org.catroid.catrobat.newui.data.brick;


import android.view.View;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseBrick implements Brick, CopyPasteable, View.OnClickListener {

    protected static int layoutResourceId;
    protected Map<Integer, BrickField> brickFieldMap = new HashMap<>();

    public int getLayoutResourceId() {
        return layoutResourceId;
    }

    public Map<Integer, BrickField> getBrickFieldMap() {
        return brickFieldMap;
    }

    @Override
    public CopyPasteable clone() throws CloneNotSupportedException {
        return (BaseBrick) super.clone();
    }
}
