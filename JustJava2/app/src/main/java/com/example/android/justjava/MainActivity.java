package com.example.android.justjava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.duration;
import static android.R.attr.name;
import static android.R.attr.text;
import static android.R.id.text1;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends Activity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity", "@string/topping_names" + hasWhippedCream);

        CheckBox chocolateToppingCheckBox = (CheckBox) findViewById(R.id.chocolate_topping_checkbox);
        boolean hasChocolateTopping = chocolateToppingCheckBox.isChecked();
        Log.v("MainActivity", "Has also chocolate toping:" + hasChocolateTopping);
        EditText userNameInput = (EditText) findViewById(R.id.firstTextInput);
        String nameInserted = userNameInput.getText().toString();
        Log.v("MainActivity", "Inserted name is: " + nameInserted);


        int price = calculatePrice(hasWhippedCream, hasChocolateTopping);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolateTopping, nameInserted);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Send this app to :" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

        /**
         * @return - returns the price, where the price of the whipped cream or topping are calculated
         */

    private int calculatePrice(boolean extraWhippedCream, boolean extraChocolateTopping) {
        int basePrice = 5;
        if (extraWhippedCream == true) {
            basePrice = basePrice + 1;
        }

        if (extraChocolateTopping == true) {
            basePrice = basePrice + 2;
        }


        return quantity * basePrice;
    }

    /**
     * Creates the summary of the order
     *
     * @param price           -
     * @param addWhippedCream - boolean
     * @return
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolateTopping, String insertedName) {
        String priceMessage = "Name of customer: " + insertedName;
        priceMessage += "\n Add Whipped Cream ? : " + addWhippedCream;
        priceMessage += "\n Added Chocolate toping ? : " + addChocolateTopping;
        priceMessage += "\n Quantity:" + quantity;
        priceMessage += "\nTotal:" + price + "euros";
        priceMessage += "\n Thank you!";
        return (priceMessage);

    }

    public void increment(View view) {
        CharSequence text1 = "You can't order more than 100 coffes.";
        int duration = Toast.LENGTH_SHORT;
        Context context1 = getApplicationContext();
        quantity = quantity + 1;
        if (quantity > 100) {
            Toast toast = Toast.makeText(context1, text1, duration);
            toast.show();
            quantity = 100;
        }
        if (quantity < 0) {
            quantity = 1;
            return;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        CharSequence text2 = "You must order at least 1 coffe";
        int duration2 = Toast.LENGTH_SHORT;
        Context context2 = getApplicationContext();
        quantity = quantity - 1;
        if (quantity < 0) {
            Toast toast = Toast.makeText(context2, text2, duration2);
            toast.show();
            quantity = 0;
            return;
        }
        displayQuantity(quantity);
    }

    /**
     * @return price of the coffe
     */

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}