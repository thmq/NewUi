package org.catroid.catrobat.newui.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.io.Serializable;
import java.util.ArrayList;


public class AddItemActivity extends AppCompatActivity {
    ImageView addImage;
    EditText itemName;
    Button createBtn;
    Boolean firstRun = false;
    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lookitem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_add_item);

        addImage = (ImageView) findViewById(R.id.addItemImage);
        createBtn = (Button) findViewById(R.id.createItemBtn);
        itemName = (EditText) findViewById(R.id.lookitemNameEdit);
        names = getIntent().getStringArrayListExtra("names_list");
        firstRun = true;

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new AlertDialog.Builder(AddItemActivity.this).create();

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog_select_image, null);
                dialog.setView(layout);

                LinearLayout cameraOption = (LinearLayout) layout.findViewById(R.id.option_camera);
                cameraOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);
                        dialog.dismiss();
                    }
                });

                LinearLayout galleryOption = (LinearLayout) layout.findViewById(R.id.option_gallery);
                galleryOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = itemName.getText().toString();
                    Bitmap bitmap = ((BitmapDrawable) addImage.getDrawable()).getBitmap();


                    if(isNameValid(name) && bitmap != null) {
                        Intent result = new Intent();
                        result.putExtra("name", name);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArrayBitmap = stream.toByteArray();
                        result.putExtra("image", byteArrayBitmap);

                        setResult(Activity.RESULT_OK, result);
                        finish();
                    } else {
                        itemName.setError(getString(R.string.error_invalid_item_name));
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                    //TODO
                    //itemName.setError(getString(R.string.error_invalid_item_name));
                }
            }
        });
    }

    private boolean isNameValid(String name) {
        if (name == null || name.equals("") || names.contains(name)) {
                return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0: //Camera
                if(resultCode == RESULT_OK){
                    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    addImage.setImageBitmap(photo);
                    //Uri selectedImage = imageReturnedIntent.getData();
                    //addImage.setImageURI(selectedImage);
                    addImage.setImageTintMode(null);
                    addImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    addImage.setImageURI(selectedImage);
                    addImage.setImageTintMode(null);
                    addImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(firstRun) {
            firstRun = false;
            int width = addImage.getWidth();
            addImage.setMinimumHeight(width);
            addImage.setMaxHeight(width);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
