package org.catroid.catrobat.newui.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.Project;

import java.util.ArrayList;
import java.util.Date;

public class ProjectRecycleViewAdapter extends RecyclerView.Adapter<ProjectRecycleViewAdapter.ViewHolder> {
    private ArrayList<Project> mProjects;
    private Context mContext;

    public ProjectRecycleViewAdapter(Context context, ArrayList<Project> projects) {
        mContext = context;
        mProjects = projects;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mBackground;
        private TextView mTxtView;
        private ImageView mImgView;
        private ImageView mFavoriteView;

        public ViewHolder(View v) {
            super(v);

            mBackground = (RelativeLayout) v.findViewById(R.id.project_background);
            mTxtView = (TextView) v.findViewById(R.id.project_title_view);
            mImgView = (ImageView) v.findViewById(R.id.project_image_view);
            mFavoriteView = (ImageView) v.findViewById(R.id.favorite_project_image_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.project_item, parent, false);
        ViewHolder mProjectViewHolder = new ViewHolder(v);
        return mProjectViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder mViewHolder, int position) {
        final Project tmp = mProjects.get(position);

        mViewHolder.mImgView.getLayoutParams().height = Constants.PROJECT_IMAGE_SIZE;
        mViewHolder.mImgView.getLayoutParams().width = Constants.PROJECT_IMAGE_SIZE;
        mViewHolder.mImgView.setImageBitmap(tmp.getThumbnail());

        mViewHolder.mTxtView.setWidth(Constants.PROJECT_IMAGE_SIZE);
        mViewHolder.mTxtView.setBackgroundColor(Color.BLACK);
        mViewHolder.mTxtView.getBackground().setAlpha(123);
        mViewHolder.mTxtView.setText(tmp.getInfoText());

        if (tmp.getFavorite()) {
            mViewHolder.mFavoriteView.setImageResource(R.drawable.favorite_white_selected);
        } else {
            mViewHolder.mFavoriteView.setImageResource(R.drawable.favorite_white_empty);
        }

        mViewHolder.mFavoriteView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tmp.setFavorite(!tmp.getFavorite());
                notifyDataSetChanged();
            }
        });

        mViewHolder.mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp.setLastAccess(new Date());
            }
        });

        mViewHolder.mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp.setLastAccess(new Date());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }
}
