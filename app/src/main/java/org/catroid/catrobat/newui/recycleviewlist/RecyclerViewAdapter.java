package org.catroid.catrobat.newui.recycleviewlist;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.ListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthee on 22.03.17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnLongClickListener {

    private List<ListItem> mListItems;
    private int mItemLayoutId;
    private RecyclerViewMultiSelectionManager<ListItem> mMultiSelectionManager;
    private List<RecyclerViewAdapterDelegate> mObservers;

    private static int SELECTED_ITEM_BACKGROUND_COLOR = 0xFFDDDDDD;

    public RecyclerViewAdapter(List<ListItem> listItems, int itemLayout) {
        mListItems = listItems;
        mItemLayoutId = itemLayout;
        mMultiSelectionManager = new RecyclerViewMultiSelectionManager<ListItem>();
        mObservers = new ArrayList<>();
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
        view.setOnLongClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem item = mListItems.get(position);

        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText(item.getDetails());

        if (mMultiSelectionManager.getSelected(item)) {
            holder.mItemView.setBackgroundColor(SELECTED_ITEM_BACKGROUND_COLOR);
            holder.mImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            holder.mItemView.setBackgroundColor(0x00000000);
            holder.mImageView.setImageResource(item.getImageRes());

        }
    }

    public void addObserver(RecyclerViewAdapterDelegate obs) {
        mObservers.add(obs);
    }

    public void removeObserver(RecyclerViewAdapterDelegate obs) {
        mObservers.remove(obs);
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    @Override
    public boolean onLongClick(View child) {
        RecyclerView recyclerView = (RecyclerView) child.getParent();

        int position = recyclerView.getChildAdapterPosition(child);

        if (0 <= position && position < getItemCount()) {
            ListItem item = mListItems.get(position);

            mMultiSelectionManager.toggleSelected(item);

            notifyDataSetChanged();
            notifySelectionChanged();

            return true;
        } else {
            return false;
        }

    }

    public RecyclerViewMultiSelectionManager<ListItem> getMultiSelectionManager() {
        return mMultiSelectionManager;
    }

    private void notifySelectionChanged() {
        for (RecyclerViewAdapterDelegate obs : mObservers) {
            obs.onSelectionChanged(this);
        }
    }

    public List<ListItem> getSelectedItems() {
        return mMultiSelectionManager.getSelectedItems();
    }

    public void clearSelection() {
        mMultiSelectionManager.clearSelection();
        notifyDataSetChanged();
        notifySelectionChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mNameView;
        public TextView mDetailsView;
        public View mItemView;

        public ViewHolder(View itemView) {
            super(itemView);

            mItemView = itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.image_view);
            mNameView = (TextView) itemView.findViewById(R.id.name_view);
            mDetailsView = (TextView) itemView.findViewById(R.id.details_view);
        }


    }
}
