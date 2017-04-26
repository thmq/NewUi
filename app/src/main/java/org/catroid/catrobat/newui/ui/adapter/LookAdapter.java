package org.catroid.catrobat.newui.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.animation.Animation;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.LookInfo;

import java.util.List;

public class LookAdapter extends RecyclerViewAdapter<LookInfo> {


    public LookAdapter(List<LookInfo> lookInfos, int listItem) {
        super(lookInfos, listItem);
    }

    @Override
    public void bindDataToViewHolder(LookInfo item, ViewHolder holder, final boolean isSelected) {
        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText("");
        final ViewHolder holderCopy = holder;
        final LookInfo itemCopy = item;

        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isSelected) {
                    holderCopy.mImageSwitcher.setImageResource(R.drawable.ic_check_circle_black_24dp);

                } else {
                    Bitmap croppedBitmap = itemCopy.getThumbnail();
                    holderCopy.mImageSwitcher.setImageDrawable(new BitmapDrawable(holderCopy.mItemView.getResources(), croppedBitmap));
                }
                holderCopy.mImageSwitcher.startAnimation(out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        holder.mImageSwitcher.startAnimation(in);
    }
}
