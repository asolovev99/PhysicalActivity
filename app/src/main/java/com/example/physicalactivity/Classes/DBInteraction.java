package com.example.physicalactivity.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.physicalactivity.PhysicalActivityDetailed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBInteraction extends SQLiteOpenHelper {
    private static String DB_PATH; // полный путь к базе данных
    private static String DB_NAME = "PhysicalActivityDB.db";
    private static final int SCHEMA = 1; // версия базы данных

    private Context myContext;

    public DBInteraction(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getDatabasePath(DB_NAME).getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void create_db(){

        File file = new File(DB_PATH);
        if (!file.exists()) {
            //получаем локальную бд как поток
            try(InputStream myInput = myContext.getAssets().open(DB_NAME);
                // Открываем пустую бд
                OutputStream myOutput = new FileOutputStream(DB_PATH)) {

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }

    public void add_task(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", task.name);
        cv.put("description", task.description);
        cv.put("date_of_start", task.dateTime);

        db.insert("tasks", null, cv);
    }

    public ArrayList<Task> get_tasks(long dateTimeStart, long dateTimeEnd) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        SQLiteDatabase db = getReadableDatabase();
        String request = "select * from tasks " +
                "where tasks.date_of_start < " + dateTimeEnd + " and tasks.date_of_start >= " + dateTimeStart;
        Cursor taskCursor = db.rawQuery(request, null);
        if (taskCursor.moveToFirst()) {
            do {
                int id = taskCursor.getInt(0);
                String name = taskCursor.getString(1);
                String description = taskCursor.getString(2);
                long dateTime = taskCursor.getLong(3);
                tasks.add(new Task(id, name, description, dateTime));
            } while (taskCursor.moveToNext());
        }

        db.close();
        taskCursor.close();
        return tasks;
    }

    public void delete_task(int id) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("tasks", "id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Tag> get_tags() {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        SQLiteDatabase db = getReadableDatabase();
        String request = "select * from tags";
        Cursor tagCursor = db.rawQuery(request, null);
        if (tagCursor.moveToFirst()) {
            do {
                int id = tagCursor.getInt(0);
                String name = tagCursor.getString(1);
                tags.add(new Tag(id, name));
            } while (tagCursor.moveToNext());
        }

        db.close();
        tagCursor.close();
        return tags;
    }

    public ArrayList<PhysicalActivity> get_activities(ArrayList<Tag> tagsFilter, String sort, String find) {
        ArrayList<PhysicalActivity> physicalActivities = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String request = "select DISTINCT activities.id, activities.name, activities.description " +
                "from activities " +
                "join tags_of_activities " +
                "on activities.id = tags_of_activities.id_activity ";
        if (tagsFilter.size() > 0) {
            request += "join tags " +
                    "on tags.id = tags_of_activities.id_tag " +
                    "where (tags.id = " + tagsFilter.get(0).id + " ";
            for (int i = 1; i < tagsFilter.size(); i++) {
                request += "or tags.id = " + tagsFilter.get(i).id + " ";
            }
            request += ") ";

            if (!Objects.equals(find, "")) {
                request += "and activities.name like '%" + find + "%' ";
            }
        }
        else {
            if (!Objects.equals(find, "")) {
                request += "where activities.name like '%" + find + "%' ";
            }
        }
        request += "order by activities.name " + sort + " ";
        
        Cursor activityCursor = db.rawQuery(request, null);
        if (activityCursor.moveToFirst()) {
            do {
                int idActivity = activityCursor.getInt(0);
                String nameActivity = activityCursor.getString(1);
                String descriptionActivity = activityCursor.getString(2);
                ArrayList<Tag> tags = new ArrayList<>();

                request = "select tags.id, tags.name " +
                        "from tags " +
                        "join tags_of_activities " +
                        "on tags.id = tags_of_activities.id_tag " +
                        "where tags_of_activities.id_activity = " + idActivity;
                Cursor tagCursor = db.rawQuery(request, null);
                if (tagCursor.moveToFirst()) {
                    do {
                        int id = tagCursor.getInt(0);
                        String name = tagCursor.getString(1);
                        tags.add(new Tag(id, name));
                    } while (tagCursor.moveToNext());
                }
                tagCursor.close();

                physicalActivities.add(new PhysicalActivity(idActivity, nameActivity, descriptionActivity, tags));
            } while (activityCursor.moveToNext());
        }

        db.close();
        activityCursor.close();
        return physicalActivities;
    }

    public static class Task {
        public int id;
        public String name;
        public String description;
        public long dateTime;

        public Task(String name, String description, long dateTime) {
            this.id = -1;
            this.name = name;
            this.description = description;
            this.dateTime = dateTime;
        }

        public Task(int id, String name, String description, long dateTime) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.dateTime = dateTime;
        }
    }

    public static class Tag {
        public int id;
        public String name;

        public Tag(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public class PhysicalActivity {
        public int id;
        public String name;
        public String description;
        public ArrayList<Tag> tags;

        public PhysicalActivity(int id, String name, String description, ArrayList<Tag> tags) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.tags = tags;
        }
    }

    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
