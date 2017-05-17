package org.catroid.catrobat.newui.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.Toast;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Constants;
import org.catroid.catrobat.newui.data.Project;

import org.catroid.catrobat.newui.db.brigde.ProjectBridge;
import org.catroid.catrobat.newui.db.util.DataContract;
import org.catroid.catrobat.newui.ui.adapter.OnSwipeTouchListener;
import org.catroid.catrobat.newui.ui.adapter.ProjectViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.ProjectViewAdapterDelegate;
import org.catroid.catrobat.newui.ui.adapter.WebViewManager;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class ProjectActivity extends AppCompatActivity implements ProjectViewAdapterDelegate {

    private WebView mWebView;
    private GridView mGridView;
    private ProjectViewAdapter mProjectViewAdapter;
    private ArrayList<Project> mProjects = new ArrayList<>();
    private OnSwipeTouchListener onSwipeTouchListener;
    private Boolean flinged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.project_activity_view);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Constants.PROJECT_IMAGE_SIZE = getSizeForGridViewImages();

        requestAndroidPermissions();

        setupWebView();
        setupProjectsListView();
        setupFAB();
    }

    private void setupProjectsListView() {
        mProjectViewAdapter = new ProjectViewAdapter(this, R.layout.project_item, mProjects);
        mProjectViewAdapter.setDelegate(this);
        mGridView = (GridView) findViewById(R.id.project_gridview);
        mGridView.setAdapter(mProjectViewAdapter);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProjectClicked();
            }
        });
    }

    private void addProjectClicked() {
        String text = "My Project";
        Project p = new Project();

        p.setInfoText(text);

        ProjectBridge bridge = new ProjectBridge(this);
        bridge.insert(p);
    }

    private void setupWebView() {
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

        setSwipeThresholdForWebView();
        onSwipeTouchListener = setupSwipeListener();
        mWebView.setOnTouchListener(onSwipeTouchListener);
    }

    private void requestAndroidPermissions() {
        ActivityCompat.requestPermissions(ProjectActivity.this, new String[]
                {Manifest.permission.INTERNET}, 1);
        ActivityCompat.requestPermissions(ProjectActivity.this, new String[]
                {Manifest.permission.ACCESS_WIFI_STATE}, 1);
        ActivityCompat.requestPermissions(ProjectActivity.this, new String[]
                {Manifest.permission.ACCESS_NETWORK_STATE}, 1);

    }

    private void setupTestData() {
        for (int i = 0; i < 3; i++) {
            String text = "Project " + i;
            Project p = new Project();

            p.setInfoText(text);
        }

        mProjectViewAdapter.notifyDataSetChanged();
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

    private OnSwipeTouchListener setupSwipeListener() {
        OnSwipeTouchListener touchListener = new OnSwipeTouchListener() {

            @Override
            public void onSwipeRight() {
                Animation outAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.out_animation);
                mWebView.setVisibility(GONE);
            }

            @Override
            public void onSwipeLeft() {
                Animation outAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.out_animation);
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

    @Override
    public void onProjectClick(ProjectViewAdapter adapter, Project project) {
        Intent scenesActivityIntent = new Intent(this, SceneActivity.class);

        scenesActivityIntent.putExtra(SceneActivity.PROJECT_URI_KEY, DataContract.ProjectEntry.getProjectUri(project).toString());

        startActivity(scenesActivityIntent);
    }
}
