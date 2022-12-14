package com.example.physicalactivity.Classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.physicalactivity.PhysicalActivityDetailed;
import com.example.physicalactivity.R;

import java.util.ArrayList;
import java.util.List;




/*public class PhysicalActivityListOfExercisesAdapter {
}*/

public class PhysicalActivityListOfExercisesAdapter extends ArrayAdapter<DBInteraction.PhysicalActivity> {
    private Context mContext;
    private ArrayList<DBInteraction.PhysicalActivity> listPhysicalActivity;
    private PhysicalActivityListOfExercisesAdapter myAdapter;
    private boolean isFromView = false;

    public PhysicalActivityListOfExercisesAdapter(Context context, int resource, List<DBInteraction.PhysicalActivity> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listPhysicalActivity = (ArrayList<DBInteraction.PhysicalActivity>) objects;
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

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.physical_activity_list_of_exercises, null);
            holder = new ViewHolder();
            holder.mTextViewName = (TextView) convertView
                    .findViewById(R.id.text_name);
            holder.mTextViewDescription = (TextView) convertView
                    .findViewById(R.id.text_description);
            holder.mGifWebViewActivity = (WebView) convertView
                    .findViewById(R.id.gif_activity_list_of_exercises);
            holder.mRelativeLayout = (RelativeLayout) convertView
                    .findViewById(R.id.relative_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextViewName.setText(listPhysicalActivity.get(position).name);

        // To check weather checked event fire from getview() or user input
        //isFromView = true;
        holder.mTextViewDescription.setText(listPhysicalActivity.get(position).description);
        //isFromView = false;

        holder.mTextViewDescription.setVisibility(View.VISIBLE);
        holder.mTextViewDescription.setTag(position);



        holder.mGifWebViewActivity.loadUrl("file:///android_asset/Exercises gifs/" + listPhysicalActivity.get(position).nameOfGif);
        holder.mGifWebViewActivity.getSettings().setLoadWithOverviewMode(true);
        holder.mGifWebViewActivity.getSettings().setUseWideViewPort(true);




        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PhysicalActivityDetailed.class);

                intent.putExtra("id", listPhysicalActivity.get(position).id);
                intent.putExtra("name", listPhysicalActivity.get(position).name);
                intent.putExtra("description", listPhysicalActivity.get(position).description);
                intent.putExtra("gifName", listPhysicalActivity.get(position).nameOfGif);

                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView mTextViewName;
        private WebView mGifWebViewActivity;
        private TextView mTextViewDescription;
        private RelativeLayout mRelativeLayout;
    }
}