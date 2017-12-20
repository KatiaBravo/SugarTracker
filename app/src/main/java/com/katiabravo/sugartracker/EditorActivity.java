package com.katiabravo.sugartracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.katiabravo.sugartracker.data.SugarContract.SugarEntry;
import com.katiabravo.sugartracker.data.SugarDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mSugarEditText;
    private Spinner mTypeSpinner;
    private EditText mAmountEditText;

    private int mType = SugarEntry.TYPE_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mSugarEditText = (EditText) findViewById(R.id.edit_sugar_item);
        mTypeSpinner = (Spinner) findViewById(R.id.spinner_type);
        mAmountEditText = (EditText) findViewById(R.id.edit_sugar_amount);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter sugarTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_type_options, android.R.layout.simple_spinner_item);

        sugarTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mTypeSpinner.setAdapter(sugarTypeSpinnerAdapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_carb))) {
                        mType = SugarEntry.TYPE_CARB;
                    } else if (selection.equals(getString(R.string.type_artificial))) {
                        mType = SugarEntry.TYPE_ARTIFICIAL;
                    } else if (selection.equals(getString(R.string.type_natural))) {
                        mType = SugarEntry.TYPE_NATURAL;
                    }else {
                        mType = SugarEntry.TYPE_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = SugarEntry.TYPE_UNKNOWN;
            }
        });
    }

    private void insertSugar(){
        String sugarString = mSugarEditText.getText().toString().trim();
        String amountOfSugarString = mAmountEditText.getText().toString().trim();
        int amountOfSugar = Integer.parseInt(amountOfSugarString);

        SugarDbHelper mDbHelper = new SugarDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SugarEntry.COLUMN_SUGAR_ITEM, sugarString);
        values.put(SugarEntry.COLUMN_TYPE_OF_SUGAR, mType);
        values.put(SugarEntry.COLUMN_AMOUNT_OF_SUGAR, amountOfSugar);

        long newRowId = db.insert(SugarEntry.TABLE_NAME, null, values);

        if(newRowId == -1){
            Toast.makeText(this, "Error with saving sugary item", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Sugary item saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertSugar();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
