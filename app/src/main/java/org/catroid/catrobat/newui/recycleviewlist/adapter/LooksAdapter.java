package org.catroid.catrobat.newui.recycleviewlist.adapter;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.ui.adapter.RecyclerViewAdapter;

import java.util.List;


public class LooksAdapter extends RecyclerViewAdapter<LookInfo> {

    public LooksAdapter(List<LookInfo> listItems, int itemLayout) {
        super(listItems, itemLayout);
    }

    @Override
    public void bindDataToViewHolder(LookInfo item, RecyclerViewAdapter.ViewHolder holder,
                                     boolean isSelected) {

        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText("");

        if (isSelected) {
            holder.mImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            holder.mImageView.setImageBitmap(item.getThumbnail());
        }
    }
}
