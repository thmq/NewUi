package org.catroid.catrobat.newui.data.bricks;

import android.view.View;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Sprite;

import java.util.List;

public class SetXBrick extends BaseBrick {

    public SetXBrick() {
        super();
        layoutId = R.layout.brick_set_x;
    }

    @Override
    protected void setBrickFields() {
        brickFields.put(R.id.set_x, BrickField.X_POSITION);
    }

    @Override
    public void onClick(View view) {
        BrickField brickField = brickFields.get(view.getId());
        //call formulaEditorFragment
        //FormulaEditorFragment.showFragment(view, this, brickField);
    }

    @Override
    public List<SequenceAction> addActionToSequence(Sprite sprite, SequenceAction sequence) {
        return null;
    }
}
