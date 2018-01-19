package com.akshaybengani.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


     }
    int quantity=1;

    public void order_button_pressed(View view)
    {
        boolean hasWhippedCream=checkForWhippedCream();
        boolean hasChocklate=checkForChocklate();

        int totalPrice=0;
        totalPrice=calculatePrice();

        String name=nameEditText();

        String orderMessage=orderSummaryMessage(hasWhippedCream,hasChocklate,name,totalPrice);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Coffee Order");
        intent.putExtra(Intent.EXTRA_TEXT, ""+orderMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }
    public void displayQuantity(int number)
    {
        TextView quantity_text_view=(TextView)findViewById(R.id.quantity_textview);
        quantity_text_view.setText(""+number);
    }

    /**
     * This method displays the given text on the screen.
     */
    public int calculatePrice()
    {
        int totalPrice=0;
        int extraCost=0;
        boolean hasWhippedCream=checkForWhippedCream();
        boolean hasChocklate=checkForChocklate();
        if (hasWhippedCream==true)
        {
            extraCost=extraCost+5;
        }
        if (hasChocklate==true)
        {
            extraCost=extraCost+10;
        }
     totalPrice = quantity*30+quantity*extraCost;

        return totalPrice;
    }

    public String orderSummaryMessage(boolean hasWhippedCream, boolean hasChocklate, String name, int totalPrice) {

        String order_message=
                "Name = "+name+
                "\nWhipped Cream "+hasWhippedCream+
                "\nChocklate "+hasChocklate+
                "\nTotal: "+NumberFormat.getCurrencyInstance().format(totalPrice)+
                "\nThank you!";

//        TextView priceTextView = (TextView) findViewById(R.id.orderSummary_textview);
//        priceTextView.setText(order_message);
          return ""+order_message;
    }

    public void increment_button_pressed(View view)
    {
        if (quantity<100)
        {
            quantity=quantity+1;
            displayQuantity(quantity);
        }
        else
        {
            Toast.makeText(MainActivity.this,"You can order maximum of 100 cups at a time ",Toast.LENGTH_SHORT).show();
        }

    }
    public void decrement_button_pressed(View view)
    {
        if (quantity>1)
        {
            quantity=quantity-1;
            displayQuantity(quantity);
        }
        else
        {
            Toast.makeText(MainActivity.this,"You Cannot Reduce Quantity from 1",Toast.LENGTH_SHORT).show();
        }

    }
    public boolean checkForWhippedCream()
    {
        boolean whippedCream=false;
        CheckBox whippedCreamChecker= (CheckBox)findViewById(R.id.whippedCream);
        if (whippedCreamChecker.isChecked()==true)
        {
            whippedCream=true;
        }
        else if (whippedCreamChecker.isChecked()==false)
        {
            whippedCream=false;
        }
        return whippedCream;
    }
    public boolean checkForChocklate()
    {
        boolean chocklate=false;
        CheckBox chocklateChecker=(CheckBox)findViewById(R.id.chocklate);
        if(chocklateChecker.isChecked()==true)
        {
            chocklate=true;
        }
        else
        {
         chocklate=false;
        }
        return chocklate;
    }
    public String nameEditText()
    {
        EditText namePicker = (EditText)findViewById(R.id.nameEditText);
        String name= String.valueOf(namePicker.getText());
        return name;
    }

}
