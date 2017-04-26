package org.catroid.catrobat.newui.ui.adapter;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;

import java.util.List;


public class LookAdapter extends RecyclerViewAdapter<LookInfo> {

    public LookAdapter(List<LookInfo> lookInfos, int listItem) {
        super(lookInfos, listItem);
    }

    @Override
    public void bindDataToViewHolder(LookInfo item, ViewHolder holder, boolean isSelected, boolean wasChanged) {
        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText("");

        if (isSelected) {
            holder.mImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            holder.mImageView.setImageBitmap(item.getThumbnail());
        }
    }
}
