package com.example.weatherapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class third extends AppCompatActivity implements View.OnClickListener{

        Button btnAdd, btnRead, btnClear;
        TextView textView, city, temp;
        String cityField;
        String currentTemperatureField;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_third);

                btnAdd = (Button) findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(third.this);

                btnRead = (Button) findViewById(R.id.btnRead);
                btnRead.setOnClickListener(third.this);

                btnClear = (Button) findViewById(R.id.btnClear);
                btnClear.setOnClickListener(third.this);

                textView = (TextView) findViewById(R.id.textView);

                Intent intent2 = getIntent();
                cityField = intent2.getStringExtra("cityField");
                currentTemperatureField = intent2.getStringExtra("currentTemperatureField");

                city = (TextView)findViewById(R.id.city);
                city.setText(cityField);

                temp = (TextView)findViewById(R.id.temp);
                temp.setText(currentTemperatureField);

                notifications();

            }

    public void notifications() {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setContentTitle("weather")
                .setContentText("City: " + cityField+ " " + "Temperature:" + currentTemperatureField)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mBuilder.build());
    }

            @Override
            public void onClick(View v) {
                String name = city.getText().toString();
                String currentTemperatureField2 = temp.getText().toString();

                switch (v.getId()) {

                    case R.id.btnAdd:
                        Contact contact = new Contact(name, currentTemperatureField2);
                        contact.save();
                        break;

                    case R.id.btnRead:
                        List<Contact> allContacts = Contact.listAll(Contact.class);
                        textView.setText(allContacts.toString());
                        break;

                    case R.id.btnClear:
                        Contact.deleteAll(Contact.class);
                        break;
                }
            }
        }