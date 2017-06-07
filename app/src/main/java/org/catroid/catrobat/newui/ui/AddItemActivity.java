package org.catroid.catrobat.newui.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.catroid.catrobat.newui.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class AddItemActivity extends AppCompatActivity {
    private ImageView exploreItem;
    private EditText itemName;
    private Button btnCreate;
    private ArrayList<String> names;

    private Boolean firstRun = false;
    private Boolean itemChosen = false;
    private String caller_tag;

    private final String LOOKS = "Looks";
    private final String SOUNDS = "Sounds";
    private final String SPRITES = "Sprites";
    private final String PROJECTS = "Projects";

    private final int CAMERA = 0;
    private final int GALLERY = 1;
    private final int PLACE_HOLDER1 = 2;
    private final int PLACE_HOLDER2 = 3;
    private final int MIC = 4;
    private final int AUDIO_PICK = 5;

    private Uri item_uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_add_item);

        exploreItem = (ImageView) findViewById(R.id.addItemImage);
        btnCreate = (Button) findViewById(R.id.btnCreateItem);
        itemName = (EditText) findViewById(R.id.addItemNameTxt);

        Intent callingIntent = getIntent();
        names = callingIntent.getStringArrayListExtra("names_list");
        caller_tag = callingIntent.getStringExtra("caller_tag");
        firstRun = true;

        itemName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String name = itemName.getText().toString();
                if(!isNameValid(name)) {
                    itemName.setError(getString(R.string.error_invalid_item_name));
                } else {
                    itemName.setError(null);
                }
                return false;
            }
        });

        exploreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(AddItemActivity.this).create();
                switch (caller_tag) {
                    case LOOKS:
                        setListenerForExploreLooks(dialog);
                        break;
                    case SOUNDS:
                        setListenerForExploreSounds(dialog);
                        break;
                    case PROJECTS:
                        setListenerForExploreProjects(dialog);
                        break;
                    case SPRITES:
                        setListenerForExploreSprites(dialog);
                }
            dialog.show();
        }});

        itemName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String name = itemName.getText().toString();
                if(!isNameValid(name)) {
                    itemName.setError(getString(R.string.error_invalid_item_name));
                } else {
                    itemName.setError(null);
                }
                return false;
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = itemName.getText().toString();

                    if(!isNameValid(name)) {
                        itemName.setError(getString(R.string.error_invalid_item_name));
                        return;
                    }
                    Intent result = new Intent();
                    result.putExtra("name", name);

                    Bitmap bitmapToByteArray = ((BitmapDrawable) exploreItem.getDrawable()).getBitmap();
                    byte[] byteArrayBitmap = bitmapToByteArray(bitmapToByteArray);
                    result.putExtra("image", byteArrayBitmap);

                     if(caller_tag.equals(SOUNDS)){
                        result.putExtra("sound_uri", getRealPathFromURI(item_uri));
                     }

                    setResult(Activity.RESULT_OK, result);
                    finish();

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(firstRun) {
            firstRun = false;
            int width = exploreItem.getWidth();
            exploreItem.setMinimumHeight(width);
            exploreItem.setMaxHeight(width);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnedIntent) {
        super.onActivityResult(requestCode, resultCode, returnedIntent);
        if(resultCode != RESULT_OK) {
            return;
        }
        switch(requestCode) {
            case CAMERA: //Camera
                    Bitmap photo = (Bitmap) returnedIntent.getExtras().get("data");
                    if(photo != null) {
                        exploreItem.setImageBitmap(photo);
                        exploreItem.setImageTintMode(null);
                        exploreItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        itemChosen = true;
                    } else {
                        itemChosen = false;
                    }
                break;
            case GALLERY:
                    item_uri = returnedIntent.getData();
                    if(!item_uri.toString().isEmpty()) {
                        exploreItem.setImageURI(item_uri);
                        exploreItem.setImageTintMode(null);
                        exploreItem.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        itemChosen = true;
                    } else {
                        itemChosen = false;
                    }
                break;
            case PLACE_HOLDER1:
                break;
            case PLACE_HOLDER2:
                break;
            case MIC:
                //TODO: implement SoundRecorder onResult
                setAudioWaveThumbnail(item_uri);
                break;
            case AUDIO_PICK:
                item_uri = returnedIntent.getData();
                if(!item_uri.toString().isEmpty()) {
                    setAudioWaveThumbnail(item_uri);
                    itemChosen = true;
                } else {
                    itemChosen = false;
                }
                break;
        }

        if(itemChosen) { btnCreate.setEnabled(true); }
    }

    private boolean isNameValid(String name) {
        if (name == null || name.equals("") || names.contains(name)) {
            return false;
        }
        return true;
    }

    void setListenerForExploreLooks(final AlertDialog dialog) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_select_image, null);
        dialog.setView(layout);

        LinearLayout cameraOption = (LinearLayout) layout.findViewById(R.id.option_camera);
        cameraOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, CAMERA);
                dialog.dismiss();
            }
        });

        LinearLayout galleryOption = (LinearLayout) layout.findViewById(R.id.option_gallery);
        galleryOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, GALLERY);
                dialog.dismiss();
            }
        });

        LinearLayout defaultOption = (LinearLayout) layout.findViewById(R.id.option_set_default_picture);
        defaultOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exploreItem.setImageResource(R.drawable.blue_square);
                itemChosen = true;
                btnCreate.setEnabled(true);
                dialog.dismiss();
            }
        });
    }

    void setListenerForExploreSounds(final AlertDialog dialog) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_select_sound, null);
        dialog.setView(layout);

        LinearLayout micOption = (LinearLayout) layout.findViewById(R.id.option_mic);
        micOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: implement soundrecording - see soundrecorder
            }
        });

        LinearLayout storageOption = (LinearLayout) layout.findViewById(R.id.option_storage);
        storageOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickSound = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickSound, AUDIO_PICK);
                dialog.dismiss();
            }

        });
    }

    void setListenerForExploreProjects(final AlertDialog dialog) {
        //TODO: implement the listener for exploring/selecting Projects
    }

    void setListenerForExploreSprites(final AlertDialog dialog) {
        //TODO: implement the listener for exploring/selecting Sprites
    }

    private void setAudioWaveThumbnail(Uri sound) {
        //TODO: calcualte soundwave and make image out of it - delete default image
        exploreItem.setImageResource(R.drawable.ic_volume_up_black_24dp);
        itemChosen = true;
        btnCreate.setEnabled(true);
    }

    private byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
