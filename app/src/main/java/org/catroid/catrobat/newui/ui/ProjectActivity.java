package org.catroid.catrobat.newui.ui;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.Toast;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.ui.comparator.AlphabeticProjectComparator;
import org.catroid.catrobat.newui.ui.comparator.RecentProjectComparator;
import org.catroid.catrobat.newui.ui.listener.OnSwipeTouchListener;
import org.catroid.catrobat.newui.ui.adapter.ProjectRecycleViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.ProjectViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.WebViewManager;

import java.util.ArrayList;
import java.util.Collections;

import static android.view.View.GONE;

public class ProjectActivity extends AppCompatActivity {

    private WebView mWebView;
    private BottomNavigationView mBottomNavigationView;
    private RecyclerView mRecyclerView;
    private ProjectViewAdapter mProjectViewAdapter;
    private ArrayList<Project> mProjects = new ArrayList<>();
    private ArrayList<Project> mDisplayedProjects = new ArrayList<>();
    private OnSwipeTouchListener onSwipeTouchListener;
    private FloatingActionButton fab;

    private BottomSheetBehavior mBottomSheetBehavior;
    private View mBottomSheet;

    private int lastSelectedBottomTab = 1;  //0 - Favorite, 1 - All, 2 - Recen
    int newSelectedBottomTab = 1;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        mRecyclerView = (RecyclerView) findViewById(R.id.project_recyclerview);

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
        }


        Constants.PROJECT_IMAGE_SIZE = getSizeForGridViewImages();

        mProjectViewAdapter = new ProjectViewAdapter(this, R.layout.project_item, mProjects);

        // Fill in Test-Data
        for (int i = 0; i < 12; i++) {

            String text = "";

            if (i % 4 == 0) text = "B ";
            if (i % 4 == 1) text = "C ";
            if (i % 4 == 2) text = "D ";
            if (i % 4 == 3) text = "A ";

            text += "Project " + i;
            if (addNewProjectItem(R.drawable.blue_test_pic, text)) {
            } else {
                Toast.makeText(this, "Could not add File: " + text, Toast.LENGTH_LONG).show();
            }
        }


        mDisplayedProjects = sortAlphabetic(mProjects);
        mDisplayedProjects = (ArrayList<Project>) mProjects.clone();
        mProjectViewAdapter = new ProjectViewAdapter(this, R.layout.project_item, mDisplayedProjects);

        mAdapter = new ProjectRecycleViewAdapter(getApplicationContext(), mDisplayedProjects);

        mLayoutManager = new GridLayoutManager(getApplicationContext(),
                getProjectsColumnCountFromScreenSize());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // TODO: Get the Bottomsheet Behavior
        mBottomSheet = (View) findViewById(R.id.bottom_sheet);

        Log.wtf("Bottom Sheet:", "Before Behavior");

        mBottomSheetBehavior = (BottomSheetBehavior) BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(true);

        Log.wtf("Bottom Sheet:", "After Behavior");

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                Toast.makeText(getApplicationContext(), "Replace with Action",
                        Toast.LENGTH_LONG).show();

            }
        });

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.getMenu().getItem(1).setChecked(true);

        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

                        switch(item.getItemId()) {
                            case R.id.bottom_action_favorites:
                                newSelectedBottomTab = 0;
                                break;
                            case R.id.bottom_action_all:
                                newSelectedBottomTab = 1;
                                break;
                            case R.id.bottom_action_recent:
                                 newSelectedBottomTab = 2;
                                break;
                            default:
                                newSelectedBottomTab = 1;
                                break;
                        }

                        if(newSelectedBottomTab == lastSelectedBottomTab) {
                            return true;
                        }

                        final Animation slide_out_right = AnimationUtils.loadAnimation(
                                getApplicationContext(), R.anim.slide_out_right);
                        slide_out_right.setDuration(200);

                        final Animation slide_out_left = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.slide_out_left);
                        slide_out_left.setDuration(200);

                        final Animation slide_in_left = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.slide_in_left);
                        slide_in_left.setDuration(200);

                        final Animation slide_in_right = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.slide_in_right);
                        slide_in_right.setDuration(200);



                        if(newSelectedBottomTab > lastSelectedBottomTab) {
                            mRecyclerView.startAnimation(slide_out_left);
                        } else {
                            mRecyclerView.startAnimation(slide_out_right);
                        }

                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                switch (item.getItemId()) {
                                    case R.id.bottom_action_recent:
                                        mDisplayedProjects.clear();
                                        mDisplayedProjects.addAll(sortRecent(mProjects));
                                        mAdapter.notifyDataSetChanged();
                                        break;
                                    case R.id.bottom_action_favorites:
                                        mDisplayedProjects.clear();
                                        mDisplayedProjects.addAll(sortFavorite(mProjects));
                                        mAdapter.notifyDataSetChanged();
                                        break;
                                    default:
                                        mDisplayedProjects.clear();
                                        mDisplayedProjects.addAll(sortAlphabetic(mProjects));
                                        mAdapter.notifyDataSetChanged();
                                        break;
                                }

                                if(newSelectedBottomTab > lastSelectedBottomTab) {
                                    mRecyclerView.startAnimation(slide_in_right);
                                } else {
                                    mRecyclerView.startAnimation(slide_in_left);
                                }

                                lastSelectedBottomTab = newSelectedBottomTab;
                            }
                        }, 200);

                        return true;
                    }
                });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > Constants.SCROLL_ACTION_MIN_THRESHOLD) {

                    if (mWebView.getVisibility() != View.GONE) {
                        mWebView.setVisibility(GONE);
                    }
                    mBottomNavigationView.setVisibility(View.INVISIBLE);

                    ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(fab, "translationY", getResources().getDimension(R.dimen.bottom_navigation_height));
                    mObjectAnimator.setDuration(300);
                    mObjectAnimator.start();

                } else if(dy < (-1) * Constants.SCROLL_ACTION_MIN_THRESHOLD) {
                    ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(fab, "translationY", (-0.05f) * getResources().getDimension(R.dimen.bottom_navigation_height));
                    mObjectAnimator.setDuration(300);
                    mObjectAnimator.start();

                    mBottomNavigationView.setVisibility(View.VISIBLE);

                }
            }
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

    private int getProjectsColumnCountFromScreenSize() {

        Boolean isTablet = ((getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE);

        if(isTablet) {
            return 3;
        }

        return 2;
    }

    private Boolean addNewProjectItem(int resID, String projectInfo) {

        try {
            Bitmap image = BitmapFactory.decodeResource(this.getResources(),
                    resID);
            Bitmap.createScaledBitmap(image, Constants.PROJECT_IMAGE_SIZE,
                    Constants.PROJECT_IMAGE_SIZE, false);

            mProjects.add(mProjects.size(), new Project(image, projectInfo));
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


    private ArrayList<Project> sortAlphabetic(ArrayList<Project> sortList) {

        Collections.sort(sortList, new AlphabeticProjectComparator());
        return sortList;
    }

    private ArrayList<Project> sortRecent(ArrayList<Project> sortList) {

        Collections.sort(sortList, new RecentProjectComparator());
        return sortList;
    }

    private ArrayList<Project> sortFavorite(ArrayList<Project> sortList) {

        ArrayList<Project> favoriteList = new ArrayList<>();
        for (int i = 0; i < sortList.size(); i++) {

            if (sortList.get(i).getFavorite()) {
                favoriteList.add(sortList.get(i));
            }
        }

        return sortAlphabetic(favoriteList);
    }

}
