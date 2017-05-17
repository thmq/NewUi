package org.catroid.catrobat.newui.data.bricks;

import android.view.View;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Sprite;

import java.util.List;

public class PlaceAtBrick extends BaseBrick {

    public PlaceAtBrick() {
        super();
        layoutId = R.layout.brick_place_at;
    }

    @Override
    protected void setBrickFields() {
        brickFields.put(R.id.x_position, BrickField.X_POSITION);
        brickFields.put(R.id.y_position, BrickField.Y_POSITION);
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
