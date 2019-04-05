package com.khanbaba.whatstheweather;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    TextView outputTextView;
    EditText cityEditText;
    Button weatherButton;
    final Handler handler = new Handler();

    public void ErrorToast(){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "Could not retrieve weather :(" , Toast.LENGTH_LONG).show();
            }
        });
        outputTextView.setVisibility(View.VISIBLE);
    }

    public void weatherCheck(View view){
        try{
            String encodedCityName = URLEncoder.encode(cityEditText.getText().toString(),"UTF-8");

            String webPage = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCityName + "&appid=1fcb171a563b639363618f0e1ae42e11";
            DownloadJSON jsonCity = new DownloadJSON();

            jsonCity.execute(webPage).get();
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(outputTextView.getWindowToken(),0);

        } catch(Exception e){
            e.printStackTrace();
            ErrorToast();
        }
    }

    public class DownloadJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            Log.i("TAG", "JSON Retriever");
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection =(HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                return result;

            } catch (Exception e){
                e.printStackTrace();

                ErrorToast();

                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                Log.i("JSON:",s);
                JSONObject jsonObject = new JSONObject(s);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content", weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                String message ="";

                for(int i=0; i < arr.length(); i++){
                    JSONObject jsonPart = arr.getJSONObject(i);

                    String main = jsonPart.getString("main");
                    String description = jsonPart.getString("description");

                    if(!main.equals("") && !description.equals("")){
                        message += main + ": " + description + "\r\n";
                    }

                    Log.i("main", jsonPart.getString("main"));
                    Log.i("description", jsonPart.getString("description"));
                }
                if(!message.equals("")){
                    outputTextView.setText(message);

                } else {
                    ErrorToast();
                }

            } catch (Exception e){
                e.printStackTrace();
                ErrorToast();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = findViewById(R.id.cityEditText);
        outputTextView = findViewById(R.id.outputTextView);
        weatherButton = findViewById(R.id.weatherButton);

        DownloadJSON jsonTask = new DownloadJSON();

        String webPage = "https://api.openweathermap.org/data/2.5/weather?q=London&appid=1fcb171a563b639363618f0e1ae42e11";
        DownloadJSON jsonCity = new DownloadJSON();
        try{
            jsonCity.execute(webPage).get();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
