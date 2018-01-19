package com.akshaybengani.firestorage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 234;
    private Button buttonChoose,buttonUpload;
    private ImageView imageView;

    private Uri filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here we initialised the buttons
        buttonChoose = (Button)findViewById(R.id.buttonChoose);
        buttonUpload = (Button)findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageview);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showFileChooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fileUploader();
            }
        });




    }
    private void showFileChooser()
    {
        //this is an intent for a result
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);                             // Request Go we have to define it as constant
        startActivityForResult(Intent.createChooser(intent,"Select an image"),PICK_IMAGE_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Request code should match
        // that means user is selected an image Result code
        // Data is also not null
        if(requestCode == PICK_IMAGE_REQUEST && resultCode== RESULT_OK && data != null && data.getData()!= null)
        {
            // This file path defined a url from the system to android to take data
            filepath = data.getData();

            // To show the selected Image on the image view we will use Bitmap
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void fileUploader()
    {

    }





}
