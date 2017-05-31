package org.catroid.catrobat.newui.ui.recyclerview.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.catroid.catrobat.newui.R;

public class ListViewHolder extends RecyclerViewHolder {
    public TextView mNameView;
    public TextView mDetailsView;
    public ImageSwitcher mImageSwitcher;

    public Animation collapseAnimation;
    public Animation expandAnimation;

    public ListViewHolder(final View itemView) {
        super(itemView);

        mNameView = (TextView) itemView.findViewById(R.id.name_view);
        mDetailsView = (TextView) itemView.findViewById(R.id.details_view);
        mImageSwitcher = (ImageSwitcher) itemView.findViewById(R.id.slide_trans_imageswitcher);

        collapseAnimation = AnimationUtils.loadAnimation(mItemView.getContext(),
                R.anim.in_animation);
        expandAnimation = AnimationUtils.loadAnimation(mItemView.getContext(),
                R.anim.out_animation);
        expandAnimation.setStartOffset(collapseAnimation.getDuration());

        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView mImageView = new ImageView(itemView.getContext());

                mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                mImageView.setLayoutParams(new ImageSwitcher
                        .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

                return mImageView;
            }
        });
    }

    public static RecyclerViewHolder newInstance(View view) {
        return new ListViewHolder(view);
    }

    public void enableSelectionAnimations() {
        mImageSwitcher.setOutAnimation(collapseAnimation);
        mImageSwitcher.setInAnimation(expandAnimation);
    }

    public void disableSelectionAnimations() {
        mImageSwitcher.setInAnimation(null);
        mImageSwitcher.setOutAnimation(null);
    }


    public void updateBackground(boolean isSelected) {
        if (isSelected) {
            mItemView.setBackgroundColor(itemView.getResources().getColor(R.color.list_item_selected_bg));
        } else {
            mItemView.setBackgroundColor(0xFF0000FF);
        }
    }
}
