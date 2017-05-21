package org.catroid.catrobat.newui.data.bricks;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseBrick implements Brick, View.OnClickListener {

    private static final long serialVersionUID = 1L;
    private static final String TAG = BaseBrick.class.getSimpleName();

    protected transient int layoutId;
    protected transient View brickView;
    protected Map<Integer, BrickField> brickFields = new HashMap();

    public BaseBrick() {
        setBrickFields();
    }

    public int getLayoutId() {
        return layoutId;
    }

    public View getBrickView() {
        return brickView;
    }

    public void setBrickView(View brickView) {
        this.brickView = brickView;
    }

    protected abstract void setBrickFields();

    public void setBrickFieldListeners() {
        for (Integer viewId : brickFields.keySet()) {
            brickView.findViewById(viewId).setOnClickListener(this);
        }
    }
}
