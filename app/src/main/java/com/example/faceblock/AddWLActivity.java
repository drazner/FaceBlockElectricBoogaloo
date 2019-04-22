package com.example.faceblock;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;



public class AddWLActivity extends AppCompatActivity {

    Button btnTakePicture;
    Button btnAddFromGallery;

    public static final int PICK_IMAGE = 1;
    public static final int USE_CAMERA = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wl);

        btnAddFromGallery = findViewById(R.id.btnAddFromGallery);
        btnTakePicture = findViewById(R.id.btnTakePicture);

        btnAddFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE);
            }
        });


        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, USE_CAMERA);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Toast.makeText(AddWLActivity.this, "You picked an image", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == USE_CAMERA){
            Toast.makeText(AddWLActivity.this, "You took a picture", Toast.LENGTH_SHORT).show();
        }
    }
}
