package com.example.physicalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openListOfExercises(View view) {
        Intent intent = new Intent(this, ListOfExercises.class);
        startActivity(intent);
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this, Calendar.class);
        startActivity(intent);
    }
}