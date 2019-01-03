package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
//import static com.example.weatherapp.R.id.selectCity;

public class MainActivity extends AppCompatActivity {

    public Button butt;
    public EditText editTXT;
    public RadioGroup radioGroup;
    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        butt = (Button)findViewById(R.id.butt);
        editTXT = (EditText) findViewById(R.id.editTXT);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unit = getUnit(radioGroup);
                Intent intent = new Intent(MainActivity.this, second.class);
                intent.putExtra("editTXT", editTXT.getText().toString());
                intent.putExtra("unit", unit);
                startActivity(intent);
            }
        });

    }


    private String getUnit(RadioGroup radioGroup) {
        switch (radioGroup.getCheckedRadioButtonId()) {
        case R.id.Celsius:
        return "units=metric";
        case R.id.Fahrenheit:
        return "units=imperial";
        default:
        return "units=metric"; }
        }

    public void onClickStart(View v) {
        startService(new Intent(this, MyService.class));
    }

    public void onClickStop(View v) {
        stopService(new Intent(this, MyService.class));
    }
}

