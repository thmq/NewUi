package org.catroid.catrobat.newui.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catroid.catrobat.newui.data.brick.BaseBrick;
import org.catroid.catrobat.newui.data.brick.BrickViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BrickAdapter extends RecyclerView.Adapter<BrickViewHolder> {

    private static final int ALPHA_FULL = 255;
    private static final int ALPHA_GREYED = 100;

    private List<BaseBrick> bricks = new ArrayList<>();
    private RecyclerViewMultiSelectionManager<BaseBrick> multiSelectionManager =
            new RecyclerViewMultiSelectionManager<>();

    @Override
    public BrickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View brickView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new BrickViewHolder(brickView);
    }

    @Override
    public void onBindViewHolder(BrickViewHolder holder, int position) {
        final BaseBrick brick = bricks.get(position);

        if (multiSelectionManager.getIsSelected(brick)) {
            setAlphaOnBrick(holder.getBrickView(), ALPHA_GREYED);
        } else {
            setAlphaOnBrick(holder.getBrickView(), ALPHA_FULL);
        }

        holder.setBrickFieldListeners(brick);
        holder.getBrickView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (multiSelectionManager.isSelectable(brick)) {
                    multiSelectionManager.toggleSelected(brick);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return bricks.get(position).getLayoutResourceId();
    }

    @Override
    public int getItemCount() {
        return bricks.size();
    }

    private void setAlphaOnBrick(View brickView, int alphaValue) {
        brickView.setAlpha(convertAlphaValueToFloat(alphaValue));
    }

    private static float convertAlphaValueToFloat(int alphaValue) {
        return alphaValue / (float) ALPHA_FULL;
    }
}
