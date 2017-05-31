package org.catroid.catrobat.newui.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.ui.adapter.SpriteViewPagerAdapter;

public class SpriteActivity extends AppCompatActivity {

    public static final String TAG = SpriteActivity.class.getSimpleName();
    SpriteViewPagerAdapter mSpriteViewPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSpriteViewPagerAdapter = new SpriteViewPagerAdapter(this);

        final AppCompatActivity activity = this;

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSpriteViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mSpriteViewPagerAdapter.onPageSelected(position);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });

        ActivityCompat.requestPermissions(SpriteActivity.this, new String[]
                {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    private void onAddButtonClicked() {
        mSpriteViewPagerAdapter.onAddButtonClicked();
    }
}
