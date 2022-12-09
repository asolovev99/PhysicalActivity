package com.example.physicalactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import android.view.View;

import com.example.physicalactivity.Classes.DBInteraction;
import com.example.physicalactivity.Classes.Filter;
import com.example.physicalactivity.Classes.FilterAdapter;
import com.example.physicalactivity.Classes.PhysicalActivityListOfExercisesAdapter;

import java.util.ArrayList;

public class ListOfExercises extends AppCompatActivity {
    private DBInteraction db;

    private void setFilters() {
        ArrayList<DBInteraction.Tag> tags = db.get_tags();
        tags.add(0, new DBInteraction.Tag(-1, "Фильтры"));

        Spinner spinner = (Spinner) findViewById(R.id.spinnerFilter);

        ArrayList<Filter> listVOs = new ArrayList<>();

        for (int i = 0; i < tags.size(); i++) {
            Filter Filter = new Filter();
            Filter.setTitle(tags.get(i).name);
            Filter.setSelected(false);
            Filter.setId(tags.get(i).id);
            listVOs.add(Filter);
        }
        FilterAdapter myAdapter = new FilterAdapter(ListOfExercises.this, 0,
                listVOs);
        spinner.setAdapter(myAdapter);
    }

    private void setSorts() {
        String[] sorts = { "По возрастанию", "По убыванию"};

        Spinner spinner = findViewById(R.id.spinnerSort);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sorts);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshListOfActivities();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    public void refreshListOfActivities() {
        Spinner spinnerTags = (Spinner) findViewById(R.id.spinnerFilter);
        String sort;

        ArrayList<DBInteraction.Tag> tags = new ArrayList<>();
        for (int i = 1; i < spinnerTags.getAdapter().getCount(); i++) {
            Filter currentFilter = (Filter)spinnerTags.getAdapter().getItem(i);
            if (currentFilter.isSelected()) {
                tags.add(new DBInteraction.Tag(currentFilter.getId(), currentFilter.getTitle()));
            }
        }

        Spinner spinner = findViewById(R.id.spinnerSort);

        if (spinner.getSelectedItem().toString().equals("По возрастанию")) {
            sort = "asc";
        }
        else {
            sort = "desc";
        }

        ArrayList<DBInteraction.PhysicalActivity> physicalActivities = db.get_activities(tags, sort, ((EditText) findViewById(R.id.editTextFind)).getText().toString());

        ListView listView = (ListView) findViewById(R.id.list_view_activities);
        PhysicalActivityListOfExercisesAdapter myAdapter = new PhysicalActivityListOfExercisesAdapter(ListOfExercises.this, 0, physicalActivities);
        listView.setAdapter(myAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_exercises);

        db = new DBInteraction(this);
        setSorts();
        setFilters();
        setFindListener();
        refreshListOfActivities();
    }

    private void setFindListener() {
        EditText editText = findViewById(R.id.editTextFind);

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,
            int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refreshListOfActivities();
            }
        });
    }
}