package org.catroid.catrobat.newui.data.brick;


import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;

public class SetLookBrick extends BaseBrick {

    private LookInfo lookInfo;

    public SetLookBrick() {
        layoutResourceId = R.layout.brick_set_look;
        brickFieldMap.put(R.id.set_look, BrickField.SET_LOOK);
    }

    public SetLookBrick(LookInfo lookInfo) {
        layoutResourceId = R.layout.brick_set_look;
        brickFieldMap.put(R.id.set_look, BrickField.SET_LOOK);
        this.lookInfo = lookInfo;
    }

    public LookInfo getLookInfo() {
        return lookInfo;
    }

    @Override
    public void initializeFormulaMap() {
        // NO FORMULA FOR THIS BRICK
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void prepareForClipboard() throws Exception {

    }

    @Override
    public void cleanupFromClipboard() throws Exception {

    }
}
