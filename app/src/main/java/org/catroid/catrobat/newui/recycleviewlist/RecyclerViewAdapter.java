package org.catroid.catrobat.newui.recycleviewlist;

import android.app.LauncherActivity;
import android.graphics.Bitmap;
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

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private int itemLayoutId;

    public RecyclerViewAdapter(List<ListItem> listItems1, int itemLayout) {
        listItems = listItems1;
        itemLayoutId = itemLayout;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem item = listItems.get(position);

        holder.imageView.setImageResource(item.getImageRes());
        holder.nameView.setText(item.getName());
        holder.detailsView.setText(item.getDetails());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView detailsView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            nameView = (TextView) itemView.findViewById(R.id.name_view);
            detailsView = (TextView) itemView.findViewById(R.id.details_view);
        }
    }
}
