package com.khanbaba.guessthecelebrity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imgURLs = new ArrayList<>();
    DownloadTask htmlTask = new DownloadTask();
    ImageDownloader imgTask = new ImageDownloader();
    ImageView celebImageView;
    Random rand = new Random();
    Button button;
    int answerPosition;
    String answer;

    public int generateRandomNumber (int limit){
        int value;
        value = rand.nextInt(limit);
        return value;
    }

    public void checkAnswer (View view){
        Log.i("TAG","checkAnswer");
        String message = "";
        Button button = (Button) view;
        Log.i("button tag",view.getTag().toString());
        if(Integer.parseInt((String) view.getTag()) == answerPosition){
            message = "Correct!";
        } else {
            message = "Wrong! Answer is: "+answer;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        //htmlTask.cancel(true);
        //imgTask.cancel(true);
        setCelebrity();
    }

    public void setCelebrity() {
        Log.i("TAG","setCelebrity");
        //Code to parse image into ImageView
        Bitmap celebImage;
        int chosenCeleb = generateRandomNumber(names.size());
        ImageDownloader imageTask = new ImageDownloader();
        answer = names.get(chosenCeleb);
        answerPosition = generateRandomNumber(4);
        try{
            celebImage = imageTask.execute(imgURLs.get(chosenCeleb)).get();
            celebImageView.setImageBitmap(celebImage);
        } catch (Exception e){
            e.printStackTrace();
        }

        //Code for setting Button text
        for(int i=0; i<=3; i++){
            int id = getResources().getIdentifier("button"+i,"id",getPackageName());
            button = findViewById(id);
            int incorrectAnswerPosition;
            if(i == answerPosition) {
                button.setText(answer);
            } else {
                incorrectAnswerPosition = generateRandomNumber(names.size());

                while(incorrectAnswerPosition == chosenCeleb){
                    incorrectAnswerPosition = generateRandomNumber(names.size());
                }
                button.setText(names.get(incorrectAnswerPosition));
            }


        }

    }

    //Background thread that downloads the image URL data and then converts it into a bitmap
    public class ImageDownloader extends AsyncTask<String,Void, Bitmap> {

        @Override

        protected Bitmap doInBackground(String... urls) {
            Log.i("TAG","Image Downloader");
                try {
                    URL url = new URL(urls[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream in = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(in);

                    return myBitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }


    //Background thread that downloads the html data and then converts it into a string
    public class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            Log.i("TAG", "HTML Reader");
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "Something went wrong";
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        celebImageView = findViewById(R.id.celebImageView);

        String result = null;
        try {
            result = htmlTask.execute("http://www.posh24.se/kandisar").get();

            String[] splitResult = result.split("<div class=\"listedArticles\">");

            Pattern p = Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(splitResult[0]);

            while (m.find()){
                //System.out.println(m.group(1));
                imgURLs.add(m.group(1));
            }

            Pattern p1 = Pattern.compile("alt=\"(.*?)\"");
            Matcher m1 = p1.matcher(splitResult[0]);

            while (m1.find()){
                names.add(m1.group(1));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println(names);
        Log.i("TAG","Initial phase complete");
        setCelebrity();

    }
}
