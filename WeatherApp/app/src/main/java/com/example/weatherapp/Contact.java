package com.example.weatherapp;

import com.orm.SugarRecord;

public class Contact extends SugarRecord {
    String name;
    String currentTemperatureField2;

    public Contact() {
    }

    public Contact(String name, String currentTemperatureField2) {
        this.name = name;
        this.currentTemperatureField2 = currentTemperatureField2;
    }

    @Override
    public String toString() {
        return "" +
                "city name:'" + name + '\'' +
                ", Temperature:'" + currentTemperatureField2 + '\'' +"\n";
    }
}