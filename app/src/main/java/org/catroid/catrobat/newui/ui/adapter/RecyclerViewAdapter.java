package org.catroid.catrobat.newui.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.catroid.catrobat.newui.R;

import java.util.List;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnLongClickListener {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mItemView;
        public ImageView mImageView;
        public TextView mNameView;
        public TextView mDetailsView;
        public ImageSwitcher mImageSwitcher;
        public boolean mHasToBeAnimated;
        public ViewHolder(View itemView) {
            super(itemView);

            mItemView = itemView;
           // mImageView = (ImageView) itemView.findViewById(R.id.image_view);
            mNameView = (TextView) itemView.findViewById(R.id.name_view);
            mDetailsView = (TextView) itemView.findViewById(R.id.details_view);
            mImageSwitcher = (ImageSwitcher) itemView.findViewById(R.id.slide_trans_imageswitcher);
            mHasToBeAnimated = true;
        }
    }

    protected  Animation in, out;
    //protected AnimatorSet mSetRightOut, mSetLeftIn;
    private List<T> mListItems;
    private int mItemLayoutId;
    private RecyclerViewMultiSelectionManager<T> mMultiSelectionManager =
            new RecyclerViewMultiSelectionManager<T>();

    private RecyclerViewAdapterDelegate<T> delegate = null;
    private  View view;
    private boolean showingBack = false;

    private static int SELECTED_ITEM_BACKGROUND_COLOR = 0xFFDDDDDD;

    public RecyclerViewAdapter(List<T> listItems, int itemLayout) {
        mListItems = listItems;
        mItemLayoutId = itemLayout;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
        in = AnimationUtils.loadAnimation(parent.getContext(), R.anim.in_animation);
        out = AnimationUtils.loadAnimation(parent.getContext(), R.anim.out_animation);

        //mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(parent.getContext(), R.animator.out_animation);
        //mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(parent.getContext(), R.animator.in_animation);

        ViewHolder holder = new ViewHolder(view);
        holder.mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                //return holder.mItemView;
               ImageView mImageView = new ImageView(parent.getContext());
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return mImageView;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = mListItems.get(position);

        holder.itemView.setOnLongClickListener(this);
        boolean isSelected = mMultiSelectionManager.getSelected(item);
        if (isSelected) {
            holder.mItemView.setBackgroundColor(SELECTED_ITEM_BACKGROUND_COLOR);
        } else {
            holder.mItemView.setBackgroundColor(0x00000000);
        }

        bindDataToViewHolder(item, holder, isSelected);
    }

    public void setDelegate(RecyclerViewAdapterDelegate del) {
        delegate = del;
    }

    @Override
    public boolean onLongClick(View child) {
        RecyclerView recyclerView = (RecyclerView) child.getParent();

        int position = recyclerView.getChildAdapterPosition(child);

        if (position == RecyclerView.NO_POSITION) {
            return false;
        }

        T item = mListItems.get(position);

        if (mMultiSelectionManager.isSelectable(item)) {
            mMultiSelectionManager.toggleSelected(item);

            notifyItemChanged(position);
            //notifyDataSetChanged();
            //notifySelectionChanged();

            return true;
        }

        return false;
    }

    public abstract void bindDataToViewHolder(T item, RecyclerViewAdapter.ViewHolder holder, boolean isSelected);

    public void addItem(T item) {
        mListItems.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(T item) {
        mListItems.remove(item);
        mMultiSelectionManager.removeItem(item);
        notifyDataSetChanged();
    }

    public void itemChanged(T item) {
        if (mListItems.contains(item)) {
            notifyDataSetChanged();
        }
    }

    public List<T> getItems() {
        return mListItems;
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public List<T> getSelectedItems() {
        return mMultiSelectionManager.getSelectedItems();
    }

    public void clearSelection() {
        mMultiSelectionManager.clearSelection();
        notifyDataSetChanged();
        notifySelectionChanged();
    }

    private void notifySelectionChanged() {
        if (delegate != null) {
            delegate.onSelectionChanged(this);
        }
    }
}
