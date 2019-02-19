package com.khanbaba.hideandseek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView helloTextView;

    public void hide(View view){
        helloTextView.setVisibility(View.INVISIBLE);

    }
    public void show(View view){
        helloTextView.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloTextView = findViewById(R.id.helloTextView);
    }
}
