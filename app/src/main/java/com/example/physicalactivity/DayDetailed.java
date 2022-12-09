package com.example.physicalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.physicalactivity.Classes.DBInteraction;
import com.example.physicalactivity.Classes.TaskCalendarAdapter;
import com.example.physicalactivity.Classes.TaskDayDetailedAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class DayDetailed extends AppCompatActivity {
    Calendar dateTime = Calendar.getInstance();
    DBInteraction db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detailed);

        db = new DBInteraction(this);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            dateTime.setTimeInMillis(arguments.getLong("dateTimeStart"));

            Log.i("dateTimeStart", String.valueOf(arguments.getLong("dateTimeStart")));
        }

        ((TextView)findViewById(R.id.text_view_day_detailed_date)).setText(dateTime.get(Calendar.DAY_OF_MONTH) + "." + String.valueOf(dateTime.get(Calendar.MONTH) + 1) + "." + dateTime.get(Calendar.YEAR));
        RefreshListOfTasks();
    }

    public void RefreshListOfTasks() {
        Calendar dateAndTimeNextDay = (Calendar) dateTime.clone();
        dateAndTimeNextDay.add(Calendar.DAY_OF_YEAR, 1);

        ArrayList<DBInteraction.Task> tasksOfDay = db.get_tasks(dateTime.getTimeInMillis(), dateAndTimeNextDay.getTimeInMillis());

        TaskDayDetailedAdapter myAdapter = new TaskDayDetailedAdapter(this, 0,
                tasksOfDay);
        ((ListView)findViewById(R.id.list_view_day_detailed)).setAdapter(myAdapter);
    }
}