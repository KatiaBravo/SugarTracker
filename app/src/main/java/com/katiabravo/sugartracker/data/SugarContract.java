package com.katiabravo.sugartracker.data;

import android.provider.BaseColumns;

public final class SugarContract {

    private SugarContract() {}

    public static final class SugarEntry implements BaseColumns{
        //name of database table for habits
        public final static String TABLE_NAME = "sugar_items";

        //unique id for the habit
        // Type: INTEGER
        public final static String _ID = BaseColumns._ID;

        //the habit itself
        // Type: TEXT
        public final static String COLUMN_SUGAR_ITEM = "sugar_item";

        //type of sugar
        //possible values: TYPE_CARB, TYPE_ARTIFICIAL, TYPE_NATURAL, TYPE_UNKNOWN
        //Type: INTEGER
        public final static String COLUMN_TYPE_OF_SUGAR = "type_of_sugar";

        //amount of sugar in grams
        //Type: INTEGER
        public final static String COLUMN_AMOUNT_OF_SUGAR = "amount_of_sugar";

        //possible values for the type of sugar
        public static final int TYPE_UNKNOWN = 0;
        public static final int TYPE_CARB = 1;
        public static final int TYPE_ARTIFICIAL = 2;
        public static final int TYPE_NATURAL = 3;


    }
}
