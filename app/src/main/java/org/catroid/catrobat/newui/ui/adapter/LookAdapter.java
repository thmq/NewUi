package org.catroid.catrobat.newui.ui.adapter;

import android.view.View;

import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.ListViewHolder;
import org.catroid.catrobat.newui.ui.recyclerview.adapter.RecyclerViewAdapter;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.RecyclerViewHolder;

import java.util.List;

public class LookAdapter extends RecyclerViewAdapter<LookInfo> {

    public LookAdapter(List<LookInfo> lookInfos, int listItem) {
        super(lookInfos, listItem);
    }

    @Override
    public RecyclerViewHolder createViewHolder(final View view) {
        return new ListViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(LookInfo item, RecyclerViewHolder holder, boolean isSelected) {
        ListViewHolder listViewHolder = (ListViewHolder) holder;
        listViewHolder.mNameView.setText(item.getName());
        listViewHolder.mDetailsView.setText("");

        if (isSelected) {
            listViewHolder.mImageSwitcher.setImageResource(CHECK_MARK_IMAGE_RESOURCE);
        } else {
            listViewHolder.mImageSwitcher.setImageDrawable(item.getRoundedDrawable());
        }

        listViewHolder.updateBackground(isSelected);
    }

}
