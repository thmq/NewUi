package org.catroid.catrobat.newui.recycleviewlist.adapter;

import android.support.v7.widget.RecyclerView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;


public class LooksAdapter extends RecyclerViewAdapter<LookInfo> {

    @Override
    public void bindDataToViewHolder(LookInfo item, ViewHolder holder, boolean isSelected) {
        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText("");

        if (isSelected) {
            holder.mImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            holder.mImageView.setImageBitmap(item.getThumbnail());
        }
    }
}
