package com.khanbaba.timestables;

import android.print.pdf.PrintedPdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView tableListView;

    public void generateTimesTable(int timesTableNumber){

        ArrayList<String> myValues = new ArrayList<String>();

        for(int i=1 ;i<=21; i++){
            myValues.add(Integer.toString(i*timesTableNumber));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myValues);

        tableListView.setAdapter(arrayAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableListView = findViewById(R.id.tableListVIew);

        final SeekBar timeSeekBar = findViewById(R.id.timeSeekBar);

        int max = 20, startingPosition = 10;

        timeSeekBar.setMax(max);
        timeSeekBar.setProgress(startingPosition);

        generateTimesTable(startingPosition);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Index ", String.valueOf(progress));
                int min = 1;
                int timesTableNumber;

                if(progress<min){
                    timesTableNumber = min;
                    timeSeekBar.setProgress(min);
                } else {
                    timesTableNumber = progress;
                }

                generateTimesTable(timesTableNumber);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
