package com.example.physicalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PhysicalActivityDetailed extends AppCompatActivity {
    int currentActivityId;
    String currentActivityName;
    String currentActivityDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_detailed);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            currentActivityId = arguments.getInt("id");
            currentActivityName = arguments.getString("name");
            currentActivityDescription = arguments.getString("description");
        }

        SetTextViews();
    }
    void SetTextViews() {
        TextView textViewActivityName = findViewById(R.id.text_activity_name);
        textViewActivityName.setText(currentActivityName);

        TextView textViewActivityDescription = findViewById(R.id.text_activity_description);
        textViewActivityDescription.setText(currentActivityDescription);
    }

    public void BtnAddTaskOnClick(View view) {
        Intent intent = new Intent(this, AddTask.class);

        intent.putExtra("id", currentActivityId);
        intent.putExtra("name", currentActivityName);
        intent.putExtra("description", currentActivityDescription);

        startActivity(intent);
    }
}