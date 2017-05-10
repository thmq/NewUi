package org.catroid.catrobat.newui.ui.adapter;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.catroid.catrobat.newui.data.Constants;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e)
        {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event_1, MotionEvent event_2, float velocity_x,
                               float velocity_y) {

            boolean result = false;
            try {
                float diff_y = event_2.getY() - event_1.getY();
                float diff_x = event_2.getX() - event_1.getX();

                if(Math.abs(diff_x) > Math.abs(diff_y)) {
                    if(Math.abs(diff_x) > Constants.SWIPE_THRESHOLD &&
                            Math.abs(velocity_x) > Constants.SWIPE_VELOCITY_THRESHOLD) {
                        if(diff_x > 0) {
                            onSwipeRight();
                        }
                        else {
                            onSwipeLeft();
                        }
                    }
                }
            }
            catch (Exception ex) {
                Log.wtf("ERROR AT SWIPE ACTION:", ex.getMessage());
            }

            return result;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onClick();
            return true;
        }
    }

    public void onSwipeRight() { }

    public void onSwipeLeft() { }

    public void onClick() {}

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }
}
