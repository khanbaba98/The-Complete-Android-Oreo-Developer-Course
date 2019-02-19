package com.khanbaba.higherorlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int value;
    public void generateRandomNumber() {
        Random rand = new Random();
        value = rand.nextInt(50) + 1;
    }
    public void higherLower(View view){

        Log.i("Info","Smoke more or less?");
        EditText numberEditText = (EditText) findViewById(R.id.numberEditText);
        String numberEditTextString = numberEditText.getText().toString();
        int numberEditTextInt = Integer.parseInt(numberEditTextString);

        Log.i("Number Input",numberEditTextString);
        Log.i("Random Integer",Integer.toString(value));

        String message;

        if(numberEditTextInt>value)
            message = "Go Lower!";
        else if(numberEditTextInt<value)
            message = "Go Higher!";
        else {
            message = "You've got it! Try Again?";
            generateRandomNumber();
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateRandomNumber();

    }
}
