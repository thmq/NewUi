package org.catroid.catrobat.newui.ui;

import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Project;

import java.text.SimpleDateFormat;

public class ProjectBottomSheet {

    private View mBottomSheetView;
    private BottomSheetBehavior mBottomSheetBehavior;

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private TextView mLastAccessTextView;
    private TextView mSizeTextView;
    private TextView mBrickCountTextView;
    private SimpleDateFormat mDateFormat;


    public ProjectBottomSheet(View bottomSheetView) {
        mBottomSheetView = bottomSheetView;

        mTitleTextView = (TextView) bottomSheetView.findViewById(R.id.bottom_sheet_title);
        mDescriptionTextView = (TextView) bottomSheetView.findViewById(R.id.bottom_sheet_description);
        mLastAccessTextView = (TextView) bottomSheetView.findViewById(R.id.bottom_sheet_last_access);
        mSizeTextView = (TextView) bottomSheetView.findViewById(R.id.bottom_sheet_project_size);
        mBrickCountTextView = (TextView) bottomSheetView.findViewById(R.id.bottom_sheet_brick_count);

        setupBehaviour();
        setupDateFormat();

        hide();
    }

    private void setupBehaviour() {
        mBottomSheetBehavior = (BottomSheetBehavior) BottomSheetBehavior.from(mBottomSheetView);
        mBottomSheetBehavior.setHideable(true);
    }

    private void setupDateFormat() {
        mDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    }


    public void hide() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void show() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void updateViewForProject(Project project) {
        mTitleTextView.setText(project.getName());
        mDescriptionTextView.setText(project.getDescription());
        mLastAccessTextView.setText(mDateFormat.format(project.getLastAccess()));
        mSizeTextView.setText("1337 KB");
        mBrickCountTextView.setText("42");
    }

}
