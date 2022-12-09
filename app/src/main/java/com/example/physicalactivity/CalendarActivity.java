package com.example.physicalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.physicalactivity.Classes.DBInteraction;
import com.example.physicalactivity.Classes.TaskCalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private ArrayList<ListView> daysTasks = new ArrayList<>();
    private ArrayList<TextView> daysDates = new ArrayList<>();
    private ArrayList<RelativeLayout> daysLayouts = new ArrayList<>();

    private Calendar dateAndTime = Calendar.getInstance();
    private DBInteraction db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        db = new DBInteraction(this);
        FillCalendarList();
        SetInitialDate();
        setChooseDaysListeners();
        FillDays();
    }

    @Override
    public void onRestart() {
        super.onRestart();

        FillDays();
    }

    private void FillCalendarList() {
        daysTasks.add(findViewById(R.id.list_view_1_week_1_day));
        daysTasks.add(findViewById(R.id.list_view_1_week_2_day));
        daysTasks.add(findViewById(R.id.list_view_1_week_3_day));
        daysTasks.add(findViewById(R.id.list_view_1_week_4_day));
        daysTasks.add(findViewById(R.id.list_view_1_week_5_day));
        daysTasks.add(findViewById(R.id.list_view_1_week_6_day));
        daysTasks.add(findViewById(R.id.list_view_1_week_7_day));
        daysTasks.add(findViewById(R.id.list_view_2_week_1_day));
        daysTasks.add(findViewById(R.id.list_view_2_week_2_day));
        daysTasks.add(findViewById(R.id.list_view_2_week_3_day));
        daysTasks.add(findViewById(R.id.list_view_2_week_4_day));
        daysTasks.add(findViewById(R.id.list_view_2_week_5_day));
        daysTasks.add(findViewById(R.id.list_view_2_week_6_day));
        daysTasks.add(findViewById(R.id.list_view_2_week_7_day));
        daysTasks.add(findViewById(R.id.list_view_3_week_1_day));
        daysTasks.add(findViewById(R.id.list_view_3_week_2_day));
        daysTasks.add(findViewById(R.id.list_view_3_week_3_day));
        daysTasks.add(findViewById(R.id.list_view_3_week_4_day));
        daysTasks.add(findViewById(R.id.list_view_3_week_5_day));
        daysTasks.add(findViewById(R.id.list_view_3_week_6_day));
        daysTasks.add(findViewById(R.id.list_view_3_week_7_day));

        daysDates.add(findViewById(R.id.text_view_1_week_1_day));
        daysDates.add(findViewById(R.id.text_view_1_week_2_day));
        daysDates.add(findViewById(R.id.text_view_1_week_3_day));
        daysDates.add(findViewById(R.id.text_view_1_week_4_day));
        daysDates.add(findViewById(R.id.text_view_1_week_5_day));
        daysDates.add(findViewById(R.id.text_view_1_week_6_day));
        daysDates.add(findViewById(R.id.text_view_1_week_7_day));
        daysDates.add(findViewById(R.id.text_view_2_week_1_day));
        daysDates.add(findViewById(R.id.text_view_2_week_2_day));
        daysDates.add(findViewById(R.id.text_view_2_week_3_day));
        daysDates.add(findViewById(R.id.text_view_2_week_4_day));
        daysDates.add(findViewById(R.id.text_view_2_week_5_day));
        daysDates.add(findViewById(R.id.text_view_2_week_6_day));
        daysDates.add(findViewById(R.id.text_view_2_week_7_day));
        daysDates.add(findViewById(R.id.text_view_3_week_1_day));
        daysDates.add(findViewById(R.id.text_view_3_week_2_day));
        daysDates.add(findViewById(R.id.text_view_3_week_3_day));
        daysDates.add(findViewById(R.id.text_view_3_week_4_day));
        daysDates.add(findViewById(R.id.text_view_3_week_5_day));
        daysDates.add(findViewById(R.id.text_view_3_week_6_day));
        daysDates.add(findViewById(R.id.text_view_3_week_7_day));

        daysLayouts.add(findViewById(R.id.relative_layout_1_week_1_day));
        daysLayouts.add(findViewById(R.id.relative_layout_1_week_2_day));
        daysLayouts.add(findViewById(R.id.relative_layout_1_week_3_day));
        daysLayouts.add(findViewById(R.id.relative_layout_1_week_4_day));
        daysLayouts.add(findViewById(R.id.relative_layout_1_week_5_day));
        daysLayouts.add(findViewById(R.id.relative_layout_1_week_6_day));
        daysLayouts.add(findViewById(R.id.relative_layout_1_week_7_day));
        daysLayouts.add(findViewById(R.id.relative_layout_2_week_1_day));
        daysLayouts.add(findViewById(R.id.relative_layout_2_week_2_day));
        daysLayouts.add(findViewById(R.id.relative_layout_2_week_3_day));
        daysLayouts.add(findViewById(R.id.relative_layout_2_week_4_day));
        daysLayouts.add(findViewById(R.id.relative_layout_2_week_5_day));
        daysLayouts.add(findViewById(R.id.relative_layout_2_week_6_day));
        daysLayouts.add(findViewById(R.id.relative_layout_2_week_7_day));
        daysLayouts.add(findViewById(R.id.relative_layout_3_week_1_day));
        daysLayouts.add(findViewById(R.id.relative_layout_3_week_2_day));
        daysLayouts.add(findViewById(R.id.relative_layout_3_week_3_day));
        daysLayouts.add(findViewById(R.id.relative_layout_3_week_4_day));
        daysLayouts.add(findViewById(R.id.relative_layout_3_week_5_day));
        daysLayouts.add(findViewById(R.id.relative_layout_3_week_6_day));
        daysLayouts.add(findViewById(R.id.relative_layout_3_week_7_day));


    }

    private void setChooseDaysListeners() {
        Calendar dateAndTimeCurrentDay = (Calendar) dateAndTime.clone();

        int dayOfWeek = dateAndTime.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }

        dateAndTimeCurrentDay.add(Calendar.DAY_OF_YEAR, -6 - dayOfWeek);

        Calendar dateAndTimeNextDay = (Calendar) dateAndTimeCurrentDay.clone();
        dateAndTimeNextDay.add(Calendar.DAY_OF_YEAR, 1);

        Log.i("dateAndTimeCurrentDay", String.valueOf(dateAndTimeCurrentDay.getTimeInMillis()));

        for (int i = 0; i < daysLayouts.size(); i++) {
            long dateAndTimeCurrentDayMillis = dateAndTimeCurrentDay.getTimeInMillis();
            long dateAndTimeNextDayMillis = dateAndTimeNextDay.getTimeInMillis();

            daysLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CalendarActivity.this, DayDetailed.class);

                    intent.putExtra("dateTimeStart", dateAndTimeCurrentDayMillis);
                    intent.putExtra("dateTimeEnd", dateAndTimeNextDayMillis);

                    startActivity(intent);
                }
            });

            dateAndTimeCurrentDay.add(Calendar.DAY_OF_YEAR, 1);
            dateAndTimeNextDay.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private void SetInitialDate() {
        dateAndTime.setFirstDayOfWeek(Calendar.MONDAY);
        dateAndTime.set(Calendar.HOUR, 0);
        dateAndTime.set(Calendar.MINUTE, 0);
        dateAndTime.set(Calendar.SECOND, 0);
        dateAndTime.set(Calendar.MILLISECOND, 0);

        int dayOfWeek = dateAndTime.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }

        Calendar dateAndTimeCurrentDay = (Calendar) dateAndTime.clone();
        dateAndTimeCurrentDay.add(Calendar.DAY_OF_YEAR, -6 - dayOfWeek);

        for (int i = 0; i < daysDates.size(); i++) {
            daysDates.get(i).setText(dateAndTimeCurrentDay.get(Calendar.DAY_OF_MONTH) + "." + String.valueOf(dateAndTimeCurrentDay.get(Calendar.MONTH) + 1) + "." + dateAndTimeCurrentDay.get(Calendar.YEAR));
            dateAndTimeCurrentDay.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private void FillDays() {
        ArrayList<DBInteraction.Task> tasksOfDay;

        for (int i = 0; i < daysTasks.size(); i++) {
            Calendar dateAndTimeCurrentDay = (Calendar) dateAndTime.clone();
            Calendar dateAndTimeNextDay = (Calendar) dateAndTime.clone();

            int dayOfWeek = dateAndTime.get(Calendar.DAY_OF_WEEK) - 1;
            if (dayOfWeek == 0) {
                dayOfWeek = 7;
            }
            dateAndTimeCurrentDay.add(Calendar.DAY_OF_YEAR, -6 - dayOfWeek + i);
            dateAndTimeNextDay.add(Calendar.DAY_OF_YEAR, -5 - dayOfWeek + i);

            tasksOfDay = db.get_tasks(dateAndTimeCurrentDay.getTimeInMillis(), dateAndTimeNextDay.getTimeInMillis());

            TaskCalendarAdapter myAdapter = new TaskCalendarAdapter(CalendarActivity.this, 0,
                    tasksOfDay);
            daysTasks.get(i).setAdapter(myAdapter);
        }
    }

    public void BtnPreviousWeeks(View view) {
        dateAndTime.add(Calendar.DAY_OF_YEAR, -21);
        setChooseDaysListeners();
        SetInitialDate();
        FillDays();
    }

    public void BtnNextWeeks(View view) {
        dateAndTime.add(Calendar.DAY_OF_YEAR, 21);
        setChooseDaysListeners();
        SetInitialDate();
        FillDays();
    }

    public void BtnAddTask(View view) {
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }
}