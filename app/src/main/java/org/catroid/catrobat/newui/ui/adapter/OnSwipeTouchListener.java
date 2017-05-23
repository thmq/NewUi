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
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityOfX,
                               float velocityOfY) {

            boolean result = false;
            try {
                float diffOfY = event2.getY() - event1.getY();
                float diffOfX = event2.getX() - event1.getX();

                if (Math.abs(diffOfX) > Math.abs(diffOfY)) {
                    if (Math.abs(diffOfX) > Constants.SWIPE_THRESHOLD &&
                            Math.abs(velocityOfX) > Constants.SWIPE_VELOCITY_THRESHOLD) {
                        if (diffOfX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                }
            } catch (Exception ex) {
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
