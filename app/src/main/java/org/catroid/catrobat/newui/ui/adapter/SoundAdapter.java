package org.catroid.catrobat.newui.ui.adapter;

import android.content.res.Resources;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.SoundInfo;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.ListViewHolder;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.RecyclerViewHolder;

import java.util.ArrayList;

public class SoundAdapter extends RecyclerViewAdapter<SoundInfo> {

    public SoundAdapter(ArrayList<SoundInfo> soundInfos, int listItem) {
        super(soundInfos, listItem);
    }

    @Override
    public RecyclerViewHolder createViewHolder(View view) {
        return new ListViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(SoundInfo item, RecyclerViewHolder holder, boolean isSelected) {
        ListViewHolder listViewHolder = (ListViewHolder) holder;
        Resources res = listViewHolder.mItemView.getResources();

        String durationDescription = item.getDuration();
        if (durationDescription == null) {
            durationDescription = res.getString(R.string.unknown_duration);
        }

        String durationLabel = res.getString(R.string.sound_duration_label);
        String duration = durationLabel + ": " + durationDescription;

        listViewHolder.mNameView.setText(item.getName());
        listViewHolder.mDetailsView.setText(duration);

        if (isSelected) {
            listViewHolder.mImageSwitcher.setImageResource(CHECK_MARK_IMAGE_RESOURCE);
        } else {
            listViewHolder.mImageSwitcher.setImageDrawable(item.getThumbnailDrawable());
        }

        listViewHolder.updateBackground(isSelected);
    }
}
