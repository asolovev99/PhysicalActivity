package com.example.physicalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.physicalactivity.Classes.DBInteraction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBInteraction db = new DBInteraction(this);
        db.create_db();
    }

    public void openListOfExercises(View view) {
        Intent intent = new Intent(this, ListOfExercises.class);
        startActivity(intent);
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}