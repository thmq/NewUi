package org.catroid.catrobat.newui.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.ProjectItem;
import org.catroid.catrobat.newui.ui.comparator.AlphabeticProjectComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ProjectViewAdapter extends ArrayAdapter {

    protected LayoutInflater inflater;
    private ArrayList<ProjectItem> objects;

    public ProjectViewAdapter(Context context, int textViewResourceId,
                              ArrayList<ProjectItem> objects) {
        super(context, textViewResourceId, objects);

        this.objects = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View projectView = convertView;
        final ListItemViewHolder viewHolder;
        final ProjectItem tmp = objects.get(position);


        if (projectView == null) {
            projectView = inflater.inflate(R.layout.project_item, null);
            viewHolder = new ListItemViewHolder(projectView);
            projectView.setTag(viewHolder);
        } else {
            viewHolder = (ListItemViewHolder) projectView.getTag();
        }

        viewHolder.imgView.getLayoutParams().height = Constants.PROJECT_IMAGE_SIZE;
        viewHolder.imgView.getLayoutParams().width = Constants.PROJECT_IMAGE_SIZE;
        viewHolder.imgView.setImageBitmap(tmp.getThumbnail());

        viewHolder.txtView.setWidth(Constants.PROJECT_IMAGE_SIZE);
        viewHolder.txtView.setBackgroundColor(Color.BLACK);
        viewHolder.txtView.getBackground().setAlpha(123);
        viewHolder.txtView.setText(tmp.getTitle());

        if (tmp.getFavorite()) {
            viewHolder.favoriteView.setImageResource(R.drawable.favorite_white_selected);
        } else {
            viewHolder.favoriteView.setImageResource(R.drawable.favorite_white_empty);
        }

        viewHolder.favoriteView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tmp.setFavorite(!tmp.getFavorite());
                notifyDataSetChanged();
            }
        });

        viewHolder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp.setLastAccess(new Date());
            }
        });

        viewHolder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp.setLastAccess(new Date());
            }
        });

        return projectView;
    }

    protected class ListItemViewHolder {
        protected RelativeLayout background;
        protected TextView txtView;
        protected ImageView imgView;
        protected ImageView favoriteView;

        public ListItemViewHolder(View projectView) {
            this.background = (RelativeLayout) projectView.findViewById(R.id.project_background);
            this.imgView = (ImageView) projectView.findViewById(R.id.project_image_view);
            this.txtView = (TextView) projectView.findViewById(R.id.project_title_view);
            this.favoriteView = (ImageView) projectView
                    .findViewById(R.id.favorite_project_image_view);
        }


    }

}
