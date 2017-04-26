package org.catroid.catrobat.newui.ui;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.Toast;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.ui.adapter.ProjectViewAdapter;
import org.catroid.catrobat.newui.ui.adapter.WebViewManager;

public class ProjectActivity extends AppCompatActivity {

    private WebView mWebView;
    private GridView mGridView;
    private ProjectViewAdapter mProjectViewAdapter;

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
            if (!(WebViewManager.loadFromURL(mWebView, "https://www.catrobat.org/", this))) {
                Toast.makeText(this, "NOT connected !!!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Connected !!!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception exception) {
            mWebView.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "NOT connected !!!", Toast.LENGTH_LONG).show();
        }

        mProjectViewAdapter = new ProjectViewAdapter();

        for (int i = 0; i < 7; i++) {
            Bitmap image = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.blue_square);
            String text = "Item " + i;
            mProjectViewAdapter.addItem(R.drawable.blue_square, image, text);
        }

        mGridView = (GridView) findViewById(R.id.project_gridview);
        mGridView.setAdapter(mProjectViewAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
