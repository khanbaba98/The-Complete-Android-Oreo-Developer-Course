package com.khanbaba.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int value;

    public void convertCurrency(View view){
        Log.i("Info","Things are about to get real");
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        String amountEditTextString = amountEditText.getText().toString();
        double amountInPounds = Double.parseDouble(amountEditTextString);
        amountInPounds*=1.3;
        String amountInDollars = String.format("%.2f", amountInPounds);

        Log.i("Pound",amountEditText.getText().toString());

        Log.i("Dollar", amountInDollars);

        Toast.makeText(this, "£"+amountEditTextString+" is $"+amountInDollars, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
