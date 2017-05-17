package org.catroid.catrobat.newui.ui;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.ProjectItem;
import org.catroid.catrobat.newui.ui.listener.OnSwipeTouchListener;
import org.catroid.catrobat.newui.ui.adapter.ProjectViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.WebViewManager;

import java.util.ArrayList;

import static android.view.View.GONE;

public class ProjectActivity extends AppCompatActivity {

    private WebView mWebView;
    private BottomNavigationView mBottomNavigationView;
    private GridView mGridView;
    private ProjectViewAdapter mProjectViewAdapter;
    private ArrayList<ProjectItem> mProjectItems = new ArrayList<>();
    private OnSwipeTouchListener onSwipeTouchListener;
    private int myLastVisiblePos;
    private FloatingActionButton fab;
    private ViewGroup.MarginLayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.project_activity_view);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActivityCompat.requestPermissions(ProjectActivity.this, new String[]
                {Manifest.permission.INTERNET}, 1);
        ActivityCompat.requestPermissions(ProjectActivity.this, new String[]
                {Manifest.permission.ACCESS_WIFI_STATE}, 1);
        ActivityCompat.requestPermissions(ProjectActivity.this, new String[]
                {Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        mWebView = (WebView) findViewById(R.id.webview);
        try {
            if (!(WebViewManager.loadFromURL(mWebView, Constants.PROJECT_NEWS_URL, this))) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            } else {
                DisplayMetrics mDisplayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

                mWebView.getLayoutParams().height = mDisplayMetrics.heightPixels / 4;


            }
        } catch (Exception exception) {
            mWebView.setVisibility(GONE);
            mWebView.getLayoutParams().height = 0;
            Toast.makeText(this, "NOT connected !!!", Toast.LENGTH_LONG).show();
        }


        Constants.PROJECT_IMAGE_SIZE = getSizeForGridViewImages();

        mProjectViewAdapter = new ProjectViewAdapter(this, R.layout.project_item, mProjectItems);

        mGridView = (GridView) findViewById(R.id.project_gridview);
        mGridView.setAdapter(mProjectViewAdapter);

        // Fill in Test-Data
        for (int i = 0; i < 12; i++) {
            String text = "Project " + i;
            if (addNewProjectItem(R.drawable.blue_test_pic, text)) {
                mProjectViewAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Could not add File: " + text, Toast.LENGTH_LONG).show();
            }
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Replace with Action",
                        Toast.LENGTH_LONG).show();
            }
        });

        myLastVisiblePos = mGridView.getFirstVisiblePosition();
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        params = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();

        mGridView.setOnScrollListener( new AbsListView.OnScrollListener() {

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Auto-generated method stub
                int currentFirstVisPos = view.getFirstVisiblePosition();
                if(currentFirstVisPos > myLastVisiblePos) {
                    //scroll down
                    if(mWebView.getVisibility() != View.GONE) {
                        mWebView.setVisibility(GONE);
                    }
                    mBottomNavigationView.setVisibility(View.INVISIBLE);

                    ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(fab, "translationY", getResources().getDimension(R.dimen.bottom_navigation_height));
                    mObjectAnimator.setDuration(300);
                    mObjectAnimator.start();

                }
                if(currentFirstVisPos < myLastVisiblePos) {
                    //scroll up
                    ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(fab, "translationY", (-0.05f)*getResources().getDimension(R.dimen.bottom_navigation_height));
                    mObjectAnimator.setDuration(300);
                    mObjectAnimator.start();

                    mBottomNavigationView.setVisibility(View.VISIBLE);
                }
                myLastVisiblePos = currentFirstVisPos;
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }
        });

        setSwipeThresholdForWebView();
        onSwipeTouchListener = setUpSwipeListener();
        mWebView.setOnTouchListener(onSwipeTouchListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private int getSizeForGridViewImages() {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        float dp = (mDisplayMetrics.widthPixels * 1.5f) / density;

        return (int) dp;
    }

    private Boolean addNewProjectItem(int resID, String projectInfo) {

        try {
            Bitmap image = BitmapFactory.decodeResource(this.getResources(),
                    resID);
            Bitmap.createScaledBitmap(image, Constants.PROJECT_IMAGE_SIZE,
                    Constants.PROJECT_IMAGE_SIZE, false);

            mProjectItems.add(mProjectItems.size(), new ProjectItem(image, projectInfo));
        } catch (Exception ex) {
            Log.wtf("ADD NEW PROJECT ", ex.getMessage());
            return false;
        }

        return true;
    }

    private void setSwipeThresholdForWebView() {

        try {
            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

            double windowWidth = mDisplayMetrics.widthPixels * 0.5;

            Constants.SWIPE_THRESHOLD = (int) windowWidth;
            Constants.SWIPE_VELOCITY_THRESHOLD = (int) windowWidth;
        } catch (Exception ex) {
            Log.wtf("DISPLAY METRICS ERROR", ex.getMessage());
            Constants.SWIPE_THRESHOLD = 300;
            Constants.SWIPE_VELOCITY_THRESHOLD = 300;
        }
    }

    private OnSwipeTouchListener setUpSwipeListener() {
        OnSwipeTouchListener touchListener = new OnSwipeTouchListener() {

            @Override
            public void onSwipeRight() {
                mWebView.setVisibility(GONE);
            }

            @Override
            public void onSwipeLeft() {
                mWebView.setVisibility(GONE);
            }

            @Override
            public void onClick() {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(Constants.PROJECT_FULL_NEWS_URL));
                startActivity(httpIntent);
            }
        };

        return touchListener;
    }

}
