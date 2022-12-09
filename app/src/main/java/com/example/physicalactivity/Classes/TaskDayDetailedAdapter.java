package com.example.physicalactivity.Classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.physicalactivity.AddTask;
import com.example.physicalactivity.DayDetailed;
import com.example.physicalactivity.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskDayDetailedAdapter extends ArrayAdapter<DBInteraction.Task> {
    private Context mContext;
    private ArrayList<DBInteraction.Task> listTask;
    private TaskDayDetailedAdapter myAdapter;
    private boolean isFromView = false;

    public TaskDayDetailedAdapter(Context context, int resource, List<DBInteraction.Task> objects) {
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

        final TaskDayDetailedAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.task_day_detailed, null);
            holder = new TaskDayDetailedAdapter.ViewHolder();
            holder.mTextViewName = (TextView) convertView
                    .findViewById(R.id.text_task_name_day_detailed);
            holder.mRelativeLayout = (RelativeLayout)  convertView
                    .findViewById(R.id.relative_layout_task_calendar);
            holder.mBtnDeleteTask = (Button)  convertView
                    .findViewById(R.id.btn_delete_task_day_detailed);
            convertView.setTag(holder);
        } else {
            holder = (TaskDayDetailedAdapter.ViewHolder) convertView.getTag();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(listTask.get(position).dateTime);
        holder.mTextViewName.setText(calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + " - " + listTask.get(position).name);

        holder.mBtnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBInteraction db = new DBInteraction(mContext);
                db.delete_task(listTask.get(position).id);
                ((DayDetailed) mContext).RefreshListOfTasks();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView mTextViewName;
        private Button mBtnDeleteTask;
        private RelativeLayout mRelativeLayout;
    }
}
