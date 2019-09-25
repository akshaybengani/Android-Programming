package com.anshulgoyal.scapark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {

    //Declaration of the Result TextView
    TextView button_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_result =(TextView)findViewById(R.id.barcode_result);


    }
    public void scanBarcode(View v)
    {
        Intent intent = new Intent(this,Barcode_Reader.class);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0)
        {
            if (requestCode == CommonStatusCodes.SUCCESS)
            {
                if(data!=null)
                {
                    Barcode barcode = data.getParcelableExtra("Barcode Value");
                    button_result.setText("Barcode Value : "+barcode.displayValue);

                }
                else {
                    button_result.setText("No Barcode Found");

                }
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}
