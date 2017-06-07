package org.catroid.catrobat.newui.ui.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import org.catroid.catrobat.newui.data.LookInfo;
import org.catroid.catrobat.newui.ui.recyclerview.multiselection.RecyclerViewMultiSelectionManager;
import org.catroid.catrobat.newui.ui.recyclerview.multiselection.RecyclerViewMultiSelectionManagerDelegate;
import org.catroid.catrobat.newui.ui.recyclerview.viewholder.RecyclerViewHolder;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnLongClickListener, View.OnClickListener, RecyclerViewMultiSelectionManagerDelegate<T> {

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private List<T> mListItems;
    private T mItemToAnimate;
    private int mItemLayoutId;
    private RecyclerViewMultiSelectionManager<T> mMultiSelectionManager =
            new RecyclerViewMultiSelectionManager<T>();

    private RecyclerViewAdapterDelegate<T> mDelegate = null;

    private static int SELECTED_ITEM_BACKGROUND_COLOR = 0xFFDDDDDD;
    protected static int CHECK_MARK_IMAGE_RESOURCE = R.drawable.ic_check_circle_black_24dp;

    public RecyclerViewAdapter(List<T> listItems, int itemLayout) {
        mListItems = listItems;
        mItemLayoutId = itemLayout;
        mMultiSelectionManager.setDelegate(this);
    }

    public RecyclerViewAdapter(int itemLayout) {
        mListItems = new ArrayList<>();
        mItemLayoutId = itemLayout;
        mMultiSelectionManager.setDelegate(this);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
        return createViewHolder(view);
    }

    public abstract RecyclerViewHolder createViewHolder(final View view);

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Log.d("RVA", "onBindViewHolder called");
        T item = mListItems.get(position);

        boolean isSelected = mMultiSelectionManager.getIsSelected(item);

        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);

        boolean shouldAnimate = shouldAnimateItem(item);

        if (shouldAnimate) {
            resetItemToAnimate();
            holder.enableSelectionAnimations();
        } else {
            holder.disableSelectionAnimations();
        }

        bindDataToViewHolder(item, holder, isSelected);
    }

    public abstract void bindDataToViewHolder(T item, RecyclerViewHolder holder, boolean isSelected);

    public void setDelegate(RecyclerViewAdapterDelegate del) {
        mDelegate = del;
    }

    protected T getItemAtPosition(int position) {
        return mListItems.get(position);
    }


    protected void addItems(List<T> items) {
        for (T item : items) {
            addItem(item);
        }
    }

    public void addItem(T item) {
        mListItems.add(item);
        int pos = mListItems.indexOf(item);
        notifyItemInserted(pos);
    }

    private void removeItem(T item) {
        int pos = mListItems.indexOf(item);
        mListItems.remove(item);
        mMultiSelectionManager.removeItem(item);

        if (shouldAnimateItem(item)) {
            resetItemToAnimate();
        }

        notifyItemRemoved(pos);
    }

    private void itemChanged(T item) {
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
    }

    public void clear() {
        mMultiSelectionManager.clearSelection();
        mListItems.clear();
        notifyDataSetChanged();
    }

    private void setItemToAnimate(T item) {
        mItemToAnimate = item;
    }

    private boolean shouldAnimateItem(T item) {
        return mItemToAnimate == item;
    }

    private void resetItemToAnimate() {
        mItemToAnimate = null;
    }


    public void onSelectionChanged(RecyclerViewMultiSelectionManager multiSelectionManager) {
        notifySelectionChanged();
    }

    private void notifySelectionChanged() {
        if (mDelegate != null) {
            mDelegate.onSelectionChanged(this);
        }
    }

    private void notifyItemClicked(T item) {
        if (mDelegate != null) {
            mDelegate.onItemClicked(this, item);
        }
    }

    public void cleanup() {}

    // Data Manipulation
    public void insertItem(T item) {
        addItem(item);
    }


    public void updateItem(T item) {
        itemChanged(item);
    }

    public void destroyItem(T item) {
        removeItem(item);
    }


    @Override
    public boolean onLongClick(View child) {
        Log.d(TAG, "On long click detected!");

        RecyclerView recyclerView = (RecyclerView) child.getParent();

        int position = recyclerView.getChildAdapterPosition(child);

        if (position == RecyclerView.NO_POSITION) {
            return false;
        }

        T item = getItemAtPosition(position);

        if (mMultiSelectionManager.isSelectable(item)) {
            mMultiSelectionManager.toggleSelected(item);
            setItemToAnimate(item);
            notifyItemChanged(position);
            return true;
        }

        return false;
    }

    @Override
    public void onClick(View child) {
        Log.d(TAG, "On click detected!");
        RecyclerView recyclerView = (RecyclerView) child.getParent();

        int position = recyclerView.getChildAdapterPosition(child);

        if (position == RecyclerView.NO_POSITION) {
            return;
        }

        T item = getItemAtPosition(position);
        notifyItemClicked(item);
    }
}
