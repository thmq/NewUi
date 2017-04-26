package org.catroid.catrobat.newui.ui.adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
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

    private List<T> mListItems;
    private int mItemLayoutId;
    private RecyclerViewMultiSelectionManager<T> mMultiSelectionManager =
            new RecyclerViewMultiSelectionManager<T>();
    private RecyclerViewCurrentSelectionManager<T> mCurrentSelectionManager = new RecyclerViewCurrentSelectionManager<>();
    private RecyclerViewAdapterDelegate<T> delegate = null;
    private  View view;
    private boolean showingBack = false;

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
        boolean isSelected = mMultiSelectionManager.getIsSelected(item);
        boolean wasChanged = mCurrentSelectionManager.getWasChanged(item);

        if (isSelected) {
            holder.mItemView.setBackgroundColor(SELECTED_ITEM_BACKGROUND_COLOR);
        } else {
            holder.mItemView.setBackgroundColor(0x00000000);
        }

        bindDataToViewHolder(item, holder, isSelected, wasChanged);
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

            updateNewSelection();

            notifyItemChanged(position);
            notifySelectionChanged();

            updateCurrentSelection();

            return true;
        }

        return false;
    }

    private void updateNewSelection() {
        mCurrentSelectionManager.setNewSelection(mMultiSelectionManager.getSelectedItems());
    }

    public void updateCurrentSelection() {
        mCurrentSelectionManager.setCurrentSelection(mMultiSelectionManager.getSelectedItems());
    }

    public abstract void bindDataToViewHolder(T item, ViewHolder holder,
                                              boolean isSelected, boolean wasChanged);

    public void addItem(T item) {
        mListItems.add(item);

        int pos = mListItems.indexOf(item);
        notifyItemInserted(pos);
    }

    public void removeItem(T item) {
        int pos = mListItems.indexOf(item);
        mListItems.remove(item);
        mMultiSelectionManager.removeItem(item);

        updateCurrentSelection();
        notifyItemRemoved(pos);
    }

    public void itemChanged(T item) {
        if (mListItems.contains(item)) {
            int pos = mListItems.indexOf(item);
            notifyItemChanged(pos);
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
