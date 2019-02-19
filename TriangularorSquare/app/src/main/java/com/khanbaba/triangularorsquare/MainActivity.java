package com.khanbaba.triangularorsquare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    class Numbers{

        int number;

        public boolean isSquare(){

            int x=1;
            int squareNumber = 1;
            while (squareNumber < number){
                squareNumber=x*x;
                if(number == squareNumber){
                    return true;
                }
                x++;
            }
            return false;
        }

        public boolean isTriangular(){
            int x = 1;
            int triangularNumber = 1;
            while (triangularNumber < number){

                triangularNumber = (x*(x+1))/2;
                x++;
                if(number == triangularNumber){
                    return true;
                }

            }
            return false;
        }

    }

    public void triangleOrSquare(View view){

        String message;

        Log.i("Info","What are these shapes");

        EditText numberEditText = (EditText) findViewById(R.id.numberEditText);

        String numberEditTextString = numberEditText.getText().toString();

        if(numberEditTextString.isEmpty()){
            message = "Please enter a number";
        }
        else {
            Log.i("Input", numberEditTextString);

            int numberEditTextInt = Integer.parseInt(numberEditTextString);

            Numbers myNumber = new Numbers();

            myNumber.number = numberEditTextInt;


            if (myNumber.isSquare() && myNumber.isTriangular())
                message = numberEditTextString + " is both a Square and Triangular Number";
            else if (myNumber.isSquare())
                message = numberEditTextString + " is Square but not Triangular";
            else if (myNumber.isTriangular())
                message = numberEditTextString + " is Triangular but not Square";
            else
                message = "Neither a Square nor a Triangular Number";
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
