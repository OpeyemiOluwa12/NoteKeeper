package com.example.git.plurasightlearn1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.git.plurasightlearn1.NoteKeeperDatabaseContract.CourseInfoEntry;
import com.example.git.plurasightlearn1.NoteKeeperDatabaseContract.NoteInfoEntry;

public class NoteKeeperOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Notekeeper.db";
    public static final int DATABASE_VERSION = 2;


    public NoteKeeperOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CourseInfoEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(NoteInfoEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(CourseInfoEntry.SQL_CREATE_INDEX1);
        sqLiteDatabase.execSQL(NoteInfoEntry.SQL_CREATE_INDEX1);

        DatabaseDataWorker databaseDataWorker = new DatabaseDataWorker(sqLiteDatabase);
        databaseDataWorker.insertCourses();
        databaseDataWorker.insertSampleNotes();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < 2){
            sqLiteDatabase.execSQL(CourseInfoEntry.SQL_CREATE_INDEX1);
            sqLiteDatabase.execSQL(NoteInfoEntry.SQL_CREATE_INDEX1);
        }
    }
}
