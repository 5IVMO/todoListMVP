package com.example.owais.todolistmvp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hii on 2/5/2017.
 */

public class DBUtil extends SQLiteOpenHelper {
    public static String DB_NAME="TodoList";
    public static int DB_VERSION=1;
    String query="CREATE TABLE IF NOT EXISTS todo(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,title TEXT,description TEXT)";

    public DBUtil(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS todo");
        sqLiteDatabase.execSQL(query);
    }
}
