package com.katiabravo.sugartracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.katiabravo.sugartracker.data.SugarContract.SugarEntry;

public class SugarDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sugars.db";
    private static final int DATABASE_VERSION = 1;

    public SugarDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_SUGARS_TABLE =  "CREATE TABLE "
                + SugarEntry.TABLE_NAME + " ("
                + SugarEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SugarEntry.COLUMN_SUGAR_ITEM + " TEXT NOT NULL, "
                + SugarEntry.COLUMN_TYPE_OF_SUGAR + " INTEGER NOT NULL, "
                + SugarEntry.COLUMN_AMOUNT_OF_SUGAR + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_SUGARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
