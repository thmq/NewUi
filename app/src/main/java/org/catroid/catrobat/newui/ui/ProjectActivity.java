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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.ProjectItem;
import org.catroid.catrobat.newui.ui.adapter.ProjectRecycleViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.ProjectViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.WebViewManager;
import org.catroid.catrobat.newui.ui.comparator.AlphabeticProjectComparator;
import org.catroid.catrobat.newui.ui.comparator.RecentProjectComparator;
import org.catroid.catrobat.newui.ui.listener.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.Collections;

import static android.view.View.GONE;

public class ProjectActivity extends AppCompatActivity {

    private WebView mWebView;
    private BottomNavigationView mBottomNavigationView;
    private RecyclerView mRecyclerView;
    private ProjectViewAdapter mProjectViewAdapter;
    private ArrayList<ProjectItem> mProjectItems = new ArrayList<>();
    private ArrayList<ProjectItem> mDisplayedProjects = new ArrayList<>();
    private OnSwipeTouchListener onSwipeTouchListener;
    private FloatingActionButton fab;

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


        mDisplayedProjects = sortAlphabetic(mProjectItems);
        mDisplayedProjects = (ArrayList<ProjectItem>) mProjectItems.clone();
        mProjectViewAdapter = new ProjectViewAdapter(this, R.layout.project_item, mDisplayedProjects);

        mAdapter = new ProjectRecycleViewAdapter(getApplicationContext(), mDisplayedProjects);

        mLayoutManager = new GridLayoutManager(getApplicationContext(),
                getProjectsColumnCountFromScreenSize());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Replace with Action",
                        Toast.LENGTH_LONG).show();

                for (int i = 0; i < mProjectItems.size(); i++) {
                    Log.wtf("mProjectItems ", mProjectItems.get(i).getTitle()
                            + " Favorite: " + mProjectItems.get(i).getFavorite()
                            + " Last Access: " + mProjectItems.get(i).getLastAccess().toString());
                }

                for (int i = 0; i < mDisplayedProjects.size(); i++) {
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
                    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

                        Animation slide_right = AnimationUtils.loadAnimation(
                                getApplicationContext(), android.R.anim.slide_out_right);
                        slide_right.setDuration(200);

                        final Animation slide_left = AnimationUtils.loadAnimation(getApplicationContext(),
                                android.R.anim.slide_in_left);
                        slide_left.setDuration(200);


                        mRecyclerView.startAnimation(slide_right);

                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //mRecyclerView.setVisibility(View.INVISIBLE);

                                switch (item.getItemId()) {
                                    case R.id.bottom_action_recent:
                                        mDisplayedProjects.clear();
                                        mDisplayedProjects.addAll(sortRecent(mProjectItems));
                                        mAdapter.notifyDataSetChanged();
                                        break;
                                    case R.id.bottom_action_favorites:
                                        mDisplayedProjects.clear();
                                        mDisplayedProjects.addAll(sortFavorite(mProjectItems));
                                        mAdapter.notifyDataSetChanged();
                                        break;
                                    default:
                                        mDisplayedProjects.clear();
                                        mDisplayedProjects.addAll(sortAlphabetic(mProjectItems));
                                        mAdapter.notifyDataSetChanged();
                                        break;
                                }

                                mRecyclerView.startAnimation(slide_left);
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
        for (int i = 0; i < sortList.size(); i++) {

            if (sortList.get(i).getFavorite()) {
                favoriteList.add(sortList.get(i));
            }
        }

        return sortAlphabetic(favoriteList);
    }

}
