package com.example.physicalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.physicalactivity.Classes.DBInteraction;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {
    Calendar dateAndTime = Calendar.getInstance();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        SetInitialValues();
        setInitialDateTime();
    }

    void SetInitialValues() {
        TextView name = findViewById(R.id.text_activity_name);
        TextView description = findViewById(R.id.text_activity_description);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            if (arguments.getString("name") != null) {
                name.setText(arguments.getString("name"));
            }
            if (arguments.getString("description") != null) {
                description.setText(arguments.getString("description"));
            }
            if (arguments.getLong("dateTime") != 0L) {
                dateAndTime.setTimeInMillis(arguments.getLong("dateTime"));
            }
        }
    }

    public void BtnAddTaskOnClick(View v) {
        TextView name = findViewById(R.id.text_activity_name);
        TextView description = findViewById(R.id.text_activity_description);


        DBInteraction db = new DBInteraction(this);
        db.add_task(new DBInteraction.Task(name.getText().toString(), description.getText().toString(), dateAndTime.getTimeInMillis()));

        finish();
    }

    public void BtnDeclineTaskOnClick(View v) {
        finish();
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(AddTask.this, d,
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(AddTask.this, t,
            dateAndTime.get(Calendar.HOUR_OF_DAY),
            dateAndTime.get(Calendar.MINUTE), true).show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {
        TextView currentDateTime = findViewById(R.id.date_time);
        currentDateTime.setText(DateUtils.formatDateTime(this,
            dateAndTime.getTimeInMillis(),
            DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
            | DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };
}