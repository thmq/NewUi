package org.catroid.catrobat.newui.data.bricks;

import android.view.View;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Sprite;

import java.util.List;

public class SetLookBrick extends BaseBrick {

    public SetLookBrick() {
        super();
        layoutId = R.layout.brick_set_look;
    }

    @Override
    protected void setBrickFields() {
        //NO brick fields
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public List<SequenceAction> addActionToSequence(Sprite sprite, SequenceAction sequence) {
        return null;
    }
}
