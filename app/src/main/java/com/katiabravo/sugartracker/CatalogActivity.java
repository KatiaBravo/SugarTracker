package com.katiabravo.sugartracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.katiabravo.sugartracker.data.SugarContract.SugarEntry;
import com.katiabravo.sugartracker.data.SugarDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private SugarDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new SugarDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                SugarEntry._ID,
                SugarEntry.COLUMN_SUGAR_ITEM,
                SugarEntry.COLUMN_TYPE_OF_SUGAR,
                SugarEntry.COLUMN_AMOUNT_OF_SUGAR};

        Cursor cursor = db.query(
                SugarEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_sugar);
        try {
            displayView.setText("The sugar item table contains " + cursor.getCount() + " sugary items.\n\n");
            displayView.append(
                    SugarEntry._ID + " - " +
                    SugarEntry.COLUMN_SUGAR_ITEM + " - " +
                    SugarEntry.COLUMN_TYPE_OF_SUGAR + " - " +
                    SugarEntry.COLUMN_AMOUNT_OF_SUGAR + "\n");

            int idColumnIndex = cursor.getColumnIndex(SugarEntry._ID);
            int sugarColumnIndex = cursor.getColumnIndex(SugarEntry.COLUMN_SUGAR_ITEM);
            int sugarTypeColumnIndex = cursor.getColumnIndex(SugarEntry.COLUMN_TYPE_OF_SUGAR);
            int amountOfSugarColumnIndex = cursor.getColumnIndex(SugarEntry.COLUMN_AMOUNT_OF_SUGAR);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentSugar = cursor.getString(sugarColumnIndex);
                int currentSugarType = cursor.getInt(sugarTypeColumnIndex);
                int currentAmountOfSugar = cursor.getInt(amountOfSugarColumnIndex);
                displayView.append(("\n" +
                        currentID + " - " +
                        currentSugar + " - " +
                        currentSugarType + " - " +
                        currentAmountOfSugar));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertSugarDummyItem() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SugarEntry.COLUMN_SUGAR_ITEM, "Orange");
        values.put(SugarEntry.COLUMN_TYPE_OF_SUGAR, SugarEntry.TYPE_NATURAL);
        values.put(SugarEntry.COLUMN_AMOUNT_OF_SUGAR, 9);

        long newRowId = db.insert(SugarEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertSugarDummyItem();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
