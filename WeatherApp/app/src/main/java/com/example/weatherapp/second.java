package com.example.weatherapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class second extends AppCompatActivity {

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    ProgressBar loader;
    Typeface weatherFont;
    Button btnAdd;
    String cityName;
    String temp;
    String unit;

    String OPEN_WEATHER_MAP_API = "a9e4ac307b4e3342077d4e2518079b33";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_second);

        loader = (ProgressBar) findViewById(R.id.loader);
        cityField = (TextView) findViewById(R.id.city_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        btnAdd = (Button) findViewById(R.id.add);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);

        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        unit = intent.getStringExtra("unit");
        taskLoadUp(cityName);

        Button save = (Button) findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(second.this, third.class);
                intent2.putExtra("cityField", cityField.getText().toString());
                intent2.putExtra("currentTemperatureField", currentTemperatureField.getText().toString());
                startActivity(intent2);
            }
        });
    }

    public void taskLoadUp(String query) {
        if (Function.isNetworkAvailable(getApplicationContext())) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public class DownloadWeather extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);
        }

      public String doInBackground(String...args) {
          String xml = null;
          if (!unit.isEmpty()) {
              switch (unit) {
                  case "units=metric":
                      xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                              "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
                      return xml;
                  case "units=imperial":
                      xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                              "&units=imperial&appid=" + OPEN_WEATHER_MAP_API);
                      return xml;
                  default:
                      xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                              "&units=metric&appid=" + OPEN_WEATHER_MAP_API);
                      return xml;
              }
          }
          return xml;
      }

        @Override
        public void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")));
                    humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
                    pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    weatherIcon.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)));

                    loader.setVisibility(View.GONE);

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }


        }


    }
}