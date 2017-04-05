package org.catroid.catrobat.newui.ui.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;

import java.util.List;


public class LookAdapter extends RecyclerViewAdapter<LookInfo> {


    public LookAdapter(List<LookInfo> lookInfos, int listItem) {
        super(lookInfos, listItem);
    }

    @Override
    public void bindDataToViewHolder(LookInfo item, ViewHolder holder, boolean isSelected) {
        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText("");
        //final Context view_context = holder.mItemView.getContext();

        if (isSelected) {
            holder.mImageSwitcher.setInAnimation(in);
            holder.mImageSwitcher.setImageResource(R.drawable.ic_check_circle_black_24dp);
            holder.mImageSwitcher.setOutAnimation(out);
            //holder.mImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);

        } else {

            holder.mImageSwitcher.setInAnimation(in);
            holder.mImageSwitcher.setImageResource(R.drawable.ic_donut_small_black_24dp);
            holder.mImageSwitcher.setOutAnimation(out);
            //imageSwitcher.setImageDrawable(new BitmapDrawable(item.getThumbnail()));

        }
    }
}
