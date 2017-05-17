package org.catroid.catrobat.newui.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.catroid.catrobat.newui.data.bricks.BaseBrick;

import java.util.List;

public class BrickAdapter extends ArrayAdapter<BaseBrick> {

    private LayoutInflater inflater;

    public BrickAdapter(Context context, int resource, List<BaseBrick> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseBrick brick = getItem(position);
        View brickView = brick.getBrickView();

        if (brickView == null) {
            brickView = inflater.inflate(brick.getLayoutId(), parent, false);
            brick.setBrickView(brickView);
        }

        brick.setOnClickListeners();
        return brickView;
    }
}
