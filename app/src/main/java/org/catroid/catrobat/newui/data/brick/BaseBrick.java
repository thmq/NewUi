package org.catroid.catrobat.newui.data.brick;


import android.view.View;

import org.catroid.catrobat.newui.copypaste.CopyPasteable;
import org.catroid.catrobat.newui.formulaeditor.Formula;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseBrick implements Brick, CopyPasteable, View.OnClickListener {

    protected static int layoutResourceId;
    protected Map<Integer, BrickField> brickFieldMap = new HashMap<>();
    protected ConcurrentHashMap<BrickField, Formula> formulaMap = new ConcurrentHashMap<>();

    public BaseBrick() {
        initializeFormulaMap();
    }

    public int getLayoutResourceId() {
        return layoutResourceId;
    }

    public Map<Integer, BrickField> getBrickFieldMap() {
        return brickFieldMap;
    }

    public abstract void initializeFormulaMap();

    public Formula getFormulaForBrickField(BrickField brickField) throws IllegalArgumentException {
        if (formulaMap.containsKey(brickField)) {
            return formulaMap.get(brickField);
        } else {
            throw new IllegalArgumentException("Incompatible Brick Field : " + brickField.toString());
        }
    }

    public void setFormulaForBrickField(BrickField brickField, Formula formula)
            throws IllegalArgumentException {
        if (formulaMap.containsKey(brickField)) {
            formulaMap.replace(brickField, formula);
        } else {
            throw new IllegalArgumentException("Incompatible Brick Field : " + brickField.toString());
        }
    }

    @Override
    public CopyPasteable clone() throws CloneNotSupportedException {
        return (BaseBrick) super.clone();
    }

    @Override
    public void prepareForClipboard() throws Exception {

    }

    @Override
    public void cleanupFromClipboard() throws Exception {

    }
}
