package org.catroid.catrobat.newui.ui.recyclerview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

abstract public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public final View mItemView;

    public RecyclerViewHolder(final View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    public static RecyclerViewHolder newInstance(View view) {
        return null;
    }

    public abstract void enableSelectionAnimations();
    public abstract void disableSelectionAnimations();
}
