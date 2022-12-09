package com.example.physicalactivity.Classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.physicalactivity.CalendarActivity;
import com.example.physicalactivity.DayDetailed;
import com.example.physicalactivity.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskCalendarAdapter extends ArrayAdapter<DBInteraction.Task> {
    private Context mContext;
    private ArrayList<DBInteraction.Task> listTask;
    private TaskCalendarAdapter myAdapter;
    private boolean isFromView = false;

    public TaskCalendarAdapter(Context context, int resource, List<DBInteraction.Task> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listTask = (ArrayList<DBInteraction.Task>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final TaskCalendarAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.task_calendar, null);
            holder = new TaskCalendarAdapter.ViewHolder();
            holder.mTextViewName = (TextView) convertView
                    .findViewById(R.id.textTaskNameCalendar);
            holder.mRelativeLayout = (RelativeLayout)  convertView
                    .findViewById(R.id.relative_layout_task_calendar);
            convertView.setTag(holder);
        } else {
            holder = (TaskCalendarAdapter.ViewHolder) convertView.getTag();
        }

        java.util.Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(listTask.get(position).dateTime);
        holder.mTextViewName.setText(calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + " - " + listTask.get(position).name);

        return convertView;
    }

    private class ViewHolder {
        private TextView mTextViewName;
        private RelativeLayout mRelativeLayout;
    }
}
