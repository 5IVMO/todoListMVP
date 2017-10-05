package com.example.owais.todolistmvp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by hii on 2/5/2017.
 */
public class TodoHelper {
    public static String TABLE_NAME = "todo";
    public static String FIELD_TITLE = "title";
    public static String FIELD_DESCRIPTION = "description";
    DBUtil dbUtil;
    long id;

    public TodoHelper(Context context) {
        dbUtil = new DBUtil(context);
    }

    //add todo
    public long insertTodo(Todo todo) {
        SQLiteDatabase sqLiteDatabase = dbUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_TITLE, todo.getTitle());
        contentValues.put(FIELD_DESCRIPTION, todo.getDescription());
        id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return id;
    }

    //get All Todo
    public ArrayList<Todo> getAllTodo() {
        ArrayList<Todo> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbUtil.getWritableDatabase();
        String query = "SELECT * FROM '" + TABLE_NAME + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Todo todo = new Todo();
                todo.setId(cursor.getInt(0));
                todo.setTitle(cursor.getString(1));
                todo.setDescription(cursor.getString(2));
                arrayList.add(todo);
            }
        }
        sqLiteDatabase.close();
        return arrayList;
    }

    // Get single todo
    public Todo getTodo(long id) {
        SQLiteDatabase sqLiteDatabase = dbUtil.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM todo WHERE ID=" + id, null);
        if (cursor != null)
            cursor.moveToFirst();
        Todo todo = new Todo(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        return todo;
    }

    // Update todo
    public long updateTodo(Todo todo) {
        SQLiteDatabase sqLiteDatabase = dbUtil.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_TITLE, todo.getTitle());
        contentValues.put(FIELD_DESCRIPTION, todo.getDescription());
        long rows = sqLiteDatabase.update(TABLE_NAME, contentValues, "ID=" + todo.getId(), null);
        sqLiteDatabase.close();
        return rows;
    }

    // Delete todo
    public int removeTodo(long id) {
        SQLiteDatabase sqLiteDatabase = dbUtil.getWritableDatabase();
        int rows =  sqLiteDatabase.delete(TABLE_NAME, "ID =" + id, null);
        return rows;
    }
}
