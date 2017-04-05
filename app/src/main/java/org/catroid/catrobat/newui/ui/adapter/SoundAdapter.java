package org.catroid.catrobat.newui.ui.adapter;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.SoundInfo;

import java.util.ArrayList;

public class SoundAdapter extends RecyclerViewAdapter<SoundInfo> {

    public SoundAdapter(ArrayList<SoundInfo> soundInfos, int listItem) {
        super(soundInfos, listItem);
    }

    @Override
    public void bindDataToViewHolder(SoundInfo item, ViewHolder holder, boolean isSelected) {
        holder.mNameView.setText(item.getName());

        String durationLabel = item.getDuration();

        if (durationLabel == null) {
            durationLabel = holder.mImageView.getResources().getString(R.string.unknown_duration);
        }

        holder.mDetailsView.setText("Duration: " + durationLabel);

        if (isSelected) {
            holder.mImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            holder.mImageView.setImageResource(item.getThumbnailResource());
        }
    }
}
