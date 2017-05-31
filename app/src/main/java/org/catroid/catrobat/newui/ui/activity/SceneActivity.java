package org.catroid.catrobat.newui.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.Project;
import org.catroid.catrobat.newui.data.Scene;
import org.catroid.catrobat.newui.data.Sprite;
import org.catroid.catrobat.newui.db.brigde.SceneBridge;
import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragment;
import org.catroid.catrobat.newui.ui.fragment.BaseRecyclerListFragmentDelegate;
import org.catroid.catrobat.newui.ui.fragment.SceneListFragment;

public class SceneActivity extends AppCompatActivity implements BaseRecyclerListFragmentDelegate<Scene> {
    public static final String PROJECT_ID_KEY = "project_id";
    public static final String PROJECT_NAME_KEY = "project_name";
    private static final String TAG = SceneActivity.class.getSimpleName();
    private long mProjectId;
    private SceneListFragment mSceneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scene);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            mProjectId = intent.getLongExtra(PROJECT_ID_KEY, -1);

            Log.d(TAG, "Setting project id: " + mProjectId);

            if (mProjectId == -1) {
                throw new UnsupportedOperationException();
            }
        }

        setupFAB();
        setupRecyclerListFragment();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setupRecyclerListFragment() {
        mSceneFragment = (SceneListFragment) getSupportFragmentManager().findFragmentById(R.id.scene_fragment);
        mSceneFragment.setBaseRecyclerListFragmentDelegate(this);
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
        mSceneFragment.onAddButtonClicked();
    }

    public long getProjectId() {
        return mProjectId;
    }

    @Override
    public void onItemClicked(BaseRecyclerListFragment<Scene> fragment, Scene scene) {
        Intent scenesActivityIntent = new Intent(this, SpriteActivity.class);

        scenesActivityIntent.putExtra(SpriteActivity.SCENE_ID_KEY, scene.getId());
        scenesActivityIntent.putExtra(SpriteActivity.SCENE_NAME_KEY, scene.getName());

        startActivity(scenesActivityIntent);
    }
}
