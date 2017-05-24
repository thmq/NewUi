package org.catroid.catrobat.newui.ui;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import org.catroid.catrobat.newui.ui.comparator.AlphabeticProjectComparator;
import org.catroid.catrobat.newui.ui.comparator.RecentProjectComparator;
import org.catroid.catrobat.newui.ui.listener.OnSwipeTouchListener;
import org.catroid.catrobat.newui.ui.adapter.ProjectViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.WebViewManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static android.view.View.GONE;

public class ProjectActivity extends AppCompatActivity {

    private WebView mWebView;
    private BottomNavigationView mBottomNavigationView;
    private GridView mGridView;
    private ProjectViewAdapter mProjectViewAdapter;
    private ArrayList<ProjectItem> mProjectItems = new ArrayList<>();
    private ArrayList<ProjectItem> mDisplayedProjects = new ArrayList<>();
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

        mGridView = (GridView) findViewById(R.id.project_gridview);
        mWebView = (WebView) findViewById(R.id.webview);

        try {
            if (!(WebViewManager.loadFromURL(mWebView, Constants.PROJECT_NEWS_URL, this))) {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
            } else {
                DisplayMetrics mDisplayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

                mWebView.getLayoutParams().height = mDisplayMetrics.heightPixels / 4;
            }
        } catch (Exception exception) {
            mWebView.setVisibility(GONE);
            mWebView.getLayoutParams().height = 0;
        }


        Constants.PROJECT_IMAGE_SIZE = getSizeForGridViewImages();

        // Fill in Test-Data
        for (int i = 0; i < 12; i++) {

            String text = "";

            if(i % 4 == 0) text = "B ";
            if(i % 4 == 1) text = "C ";
            if(i % 4 == 2) text = "D ";
            if(i % 4 == 3) text = "A ";

            text += "Project " + i;
            if (addNewProjectItem(R.drawable.blue_test_pic, text)) {
            } else {
                Toast.makeText(this, "Could not add File: " + text, Toast.LENGTH_LONG).show();
            }
        }


        //mDisplayedProjects = sortAlphabetic(mProjectItems);
        mDisplayedProjects = (ArrayList<ProjectItem>) mProjectItems.clone();
        mProjectViewAdapter = new ProjectViewAdapter(this, R.layout.project_item, mDisplayedProjects);
        mGridView.setAdapter(mProjectViewAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Replace with Action",
                        Toast.LENGTH_LONG).show();

                for(int i = 0; i < mProjectItems.size(); i++) {
                    Log.wtf("mProjectItems ", mProjectItems.get(i).getTitle()
                            + " Favorite: " + mProjectItems.get(i).getFavorite()
                            + " Last Access: " + mProjectItems.get(i).getLastAccess().toString());
                }

                for(int i = 0; i < mDisplayedProjects.size(); i++) {
                    Log.wtf("displayedProjects ", mDisplayedProjects.get(i).getTitle()
                            + " Favorite: " + mDisplayedProjects.get(i).getFavorite()
                    + " Last Access: " + mDisplayedProjects.get(i).getLastAccess().toString());
                }
            }
        });

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.getMenu().getItem(1).setChecked(true);

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.bottom_action_recent:
                        mDisplayedProjects.clear();
                        mDisplayedProjects.addAll(sortRecent(mProjectItems));
                        mProjectViewAdapter.notifyDataSetChanged();
                        break;
                    case R.id.bottom_action_favorites:
                        mDisplayedProjects.clear();
                        mDisplayedProjects.addAll(sortFavorite(mProjectItems));
                        mProjectViewAdapter.notifyDataSetChanged();
                        break;
                    default:
                        mDisplayedProjects.clear();
                        mDisplayedProjects.addAll(sortAlphabetic(mProjectItems));
                        mProjectViewAdapter.notifyDataSetChanged();
                        break;
                }

                return true;
            }
        });


        myLastVisiblePos = mGridView.getFirstVisiblePosition();


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


    private ArrayList<ProjectItem> sortAlphabetic(ArrayList<ProjectItem> sortList) {

        Log.wtf("Starting ", "Alphapetic Sort ...");
        Collections.sort(sortList, new AlphabeticProjectComparator());
        return sortList;
    }

    private ArrayList<ProjectItem> sortRecent(ArrayList<ProjectItem> sortList) {

        Log.wtf("Starting ", "Alphapetic Sort ...");
        Collections.sort(sortList, new RecentProjectComparator());
        return sortList;
    }

    private ArrayList<ProjectItem> sortFavorite(ArrayList<ProjectItem> sortList) {

        Log.wtf("Starting ", "Favorite Sort ...");

        ArrayList<ProjectItem> favoriteList = new ArrayList<>();
        for(int i = 0; i < sortList.size(); i++) {

            if(sortList.get(i).getFavorite()) {
                favoriteList.add(sortList.get(i));
            }
        }

        return sortAlphabetic(favoriteList);
    }

}
