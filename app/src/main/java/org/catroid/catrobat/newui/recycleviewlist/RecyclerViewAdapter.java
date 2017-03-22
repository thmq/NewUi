package org.catroid.catrobat.newui.recycleviewlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.ListItem;

import java.util.List;

/**
 * Created by matthee on 22.03.17.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnLongClickListener {

    private List<ListItem> mListItems;
    private int mItemLayoutId;
    private RecyclerViewMultiSelectionManager mMultiselectionManager;

    public RecyclerViewAdapter(List<ListItem> listItems1, int itemLayout) {
        mListItems = listItems1;
        mItemLayoutId = itemLayout;
        mMultiselectionManager = new RecyclerViewMultiSelectionManager();
    }



    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId, parent, false);
        view.setOnLongClickListener(this);

        return new ViewHolder(view, mMultiselectionManager);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem item = mListItems.get(position);

        holder.mImageView.setImageResource(item.getImageRes());
        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText(item.getDetails());
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    @Override
    public boolean onLongClick(View view) {
        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mNameView;
        public TextView mDetailsView;

        private RecyclerViewMultiSelectionManager mMultiselectionManager;

        public ViewHolder(View itemView, RecyclerViewMultiSelectionManager multiselectionManager) {
            super(itemView);

            mMultiselectionManager = multiselectionManager;

            mImageView = (ImageView) itemView.findViewById(R.id.image_view);
            mNameView = (TextView) itemView.findViewById(R.id.name_view);
            mDetailsView = (TextView) itemView.findViewById(R.id.details_view);
        }

    }
}
