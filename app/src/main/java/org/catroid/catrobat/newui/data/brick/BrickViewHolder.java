package org.catroid.catrobat.newui.data.brick;


import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BrickViewHolder extends RecyclerView.ViewHolder {

    private View brickView;

    public BrickViewHolder(View itemView) {
        super(itemView);
    }

    public View getBrickView() {
        return brickView;
    }

    public void setBrickFieldListeners(BaseBrick brick) {
        for (int brickFieldId : brick.getBrickFieldMap().keySet()) {
            brickView.findViewById(brickFieldId).setOnClickListener(brick);
        }
    }
}
