package org.catroid.catrobat.newui.data.brick;

import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.formulaeditor.Formula;

public class SetXBrick extends BaseBrick {

    public SetXBrick() {
        layoutResourceId = R.layout.brick_set_x;
        brickFieldMap.put(R.layout.brick_set_x, BrickField.X_POSITION);
    }

    @Override
    public void initializeFormulaMap() {
        formulaMap.putIfAbsent(BrickField.X_POSITION, new Formula());
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
