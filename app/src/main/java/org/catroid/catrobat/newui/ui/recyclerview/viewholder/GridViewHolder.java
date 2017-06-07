package org.catroid.catrobat.newui.ui.recyclerview.viewholder;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;

public class GridViewHolder extends RecyclerViewHolder {
    public View mView;
    public RelativeLayout mBackground;
    public TextView mTextView;
    public ImageView mImageView;
    public ImageView mFavoriteView;
    public ImageView mSelectedView;

    public static RecyclerViewHolder newInstance(View view) {
        return new GridViewHolder(view);
    }

    public GridViewHolder(View projectView) {
        super(projectView);

        mView = projectView;
        mBackground = (RelativeLayout) projectView.findViewById(R.id.project_background);
        mImageView = (ImageView) projectView.findViewById(R.id.project_image_view);
        mTextView = (TextView) projectView.findViewById(R.id.project_title_view);
        mFavoriteView = (ImageView) projectView.findViewById(R.id.favorite_project_image_view);
        mSelectedView = (ImageView) projectView.findViewById(R.id.selected_project_view);
    }

    @Override
    public void enableSelectionAnimations() {

    }

    @Override
    public void disableSelectionAnimations() {

    }
}
