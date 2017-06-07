package org.catroid.catrobat.newui.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.db.brigde.SceneBridge;
import org.catroid.catrobat.newui.ui.fragment.SpriteListFragment;


public class SpriteActivity extends AppCompatActivity {
    public static final String SCENE_ID_KEY = "scene_id";
    public static final String SCENE_NAME_KEY = "scene_name";
    private static final String TAG = SpriteActivity.class.getSimpleName();

    private long mSceneId;
    private SpriteListFragment mSpriteFragment;
    private Scene mScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sprite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            mSceneId = intent.getLongExtra(SCENE_ID_KEY, -1);

            Log.d(TAG, "Setting scene id: " + mSceneId);

            if (mSceneId == -1) {
                throw new UnsupportedOperationException();
            }
        }

        loadScene();
        setupFAB();
        setupRecyclerListFragment();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void loadScene() {
        SceneBridge bridge = new SceneBridge(this);

        mScene = bridge.find(mSceneId);
    }

    private void setupRecyclerListFragment() {
        mSpriteFragment = (SpriteListFragment) getSupportFragmentManager().findFragmentById(R.id.sprite_fragment);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonClicked();
            }
        });
    }

    private void addButtonClicked() {
        mSpriteFragment.onAddButtonClicked();
    }

    public long getSceneId() {
        return mSceneId;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent sceneIntent = getParentActivityIntent();

                sceneIntent.putExtra(SceneActivity.PROJECT_ID_KEY, mScene.getProjectId());

                NavUtils.navigateUpTo(this, sceneIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
