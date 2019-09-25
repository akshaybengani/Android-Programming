package com.anshulgoyal.scapark;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Barcode_Reader extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_CAMERA = 0;
    // This is for surface view layout
    SurfaceView cameraPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode__reader);


        // Surface View Variable initialisation assigning ID
        cameraPreview = (SurfaceView) findViewById(R.id.camera_preview);

        //   appPermission();
        createCameraSource();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void createCameraSource() {

        // To access Barcode Detection or Google Camera API we need to create an object of the BarcodeDetector class
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        // To build our camera functionality in such a way to access barcode Detection capabilities we need to pass the feature in the Camera Source
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector) // two arguments current context and feature
                //To fix Autofocus Feature
                .setAutoFocusEnabled(true)
                //To capture the barcode from distance also
                .setRequestedPreviewSize(1600, 1024)
                // Now build this
                .build();

        //Add callback to the source view Holder so we can start and stop the camera source
        // .. on SurfaceView surfaceCreated() and surfaceDestroyed()
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                // To check weather the permissions to access camera are allowed or not

                if (ActivityCompat.checkSelfPermission(Barcode_Reader.this, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Barcode_Reader.this,
                            android.Manifest.permission.CAMERA)) {

                        Toast.makeText(Barcode_Reader.this,"Please Provide me Permission",Toast.LENGTH_SHORT).show();
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                        ActivityCompat.requestPermissions(Barcode_Reader.this,
                                new String[]{android.Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_ACCESS_CAMERA);
                        finish();

                    }
                    return;
                }
                try {

                    // To start the camera hardware
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    // To check any fault in the camera
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                // When task finished to stop the camera hardware
                cameraSource.stop();

            }
        });

        // This is to process barcodes scanned by the camera
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                // Here we collect the barcode scanned Data in a array
                // We used a sparse Array to store the data by using the function detections and method getDetectedItems
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                // Now we have data of our barcode in this array we need to pass this in our next activity or process it
                if (barcodes.size()>0)
                {
                    Intent intent = new Intent();
                    // This will set the first value of the barcode and send it to the next Activity
                    intent.putExtra("Barcode Value",barcodes.valueAt(0));
                    //
                    setResult(CommonStatusCodes.SUCCESS,intent);
                    finish();

                }
                else
                {
                    Toast.makeText(Barcode_Reader.this,"Scan A correct Barcode",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }





}
