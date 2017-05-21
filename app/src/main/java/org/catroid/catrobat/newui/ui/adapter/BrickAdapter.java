package org.catroid.catrobat.newui.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.catroid.catrobat.newui.data.bricks.BaseBrick;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrickAdapter extends ArrayAdapter<BaseBrick> {

    public static final int ALPHA_FULL = 255;
    public static final int ALPHA_GREYED = 100;

    private LayoutInflater inflater;
    private Set<BaseBrick> selectedBricks = new HashSet<>();
    private SelectionListener selectionListener;
    private DragAndDropListener dragAndDropListener;

    public BrickAdapter(Context context, int resource, List<BaseBrick> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSelectionListener(SelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final BaseBrick brick = getItem(position);
        View brickView = brick.getBrickView();

        if (brickView == null) {
            brickView = inflater.inflate(brick.getLayoutId(), parent, false);
            brick.setBrickView(brickView);
        }

        brick.setBrickFieldListeners();
        setAlphaOnBrick(brick);

        brickView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (selectedBricks.contains(brick)) {
                    selectedBricks.remove(brick);
                } else {
                    selectedBricks.add(brick);
                }

                setAlphaOnBrick(brick);
                selectionListener.onSelectionChanged();
                return true;
            }
        });

        return brick.getBrickView();
    }

    public Set<BaseBrick> getSelectedBricks() {
        return selectedBricks;
    }

    private void setAlphaOnBrick(BaseBrick brick) {
        if (selectedBricks.contains(brick)) {
            brick.getBrickView().setAlpha(convertAlphaValueToFloat(ALPHA_GREYED));
        } else {
            brick.getBrickView().setAlpha(convertAlphaValueToFloat(ALPHA_FULL));
        }
    }

    public void clearSelection() {
        selectedBricks.clear();
        notifyDataSetChanged();
    }

    private static float convertAlphaValueToFloat(int alphaValue) {
        return alphaValue / (float) ALPHA_FULL;
    }

    public interface DragAndDropListener {

        void onLongClick(int position, View brickView, BaseBrick brick);
    }

    public interface SelectionListener {

        void onSelectionChanged();
    }
}
