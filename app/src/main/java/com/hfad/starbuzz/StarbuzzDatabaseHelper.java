package com.hfad.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "starbuzz"; // the name of our database
    private static final String DRINK_TABLE = "DRINK";
    private static final String NAME_COL = "NAME";
    private static final String ID_COL = "_id";
    private static final String DESCRIPTION_COL = "DESCRIPTION";
    private static final String IMAGE_ID_COL = "IMAGE_RESOURCE_ID";
    private static final String FAVORITE_COL = "FAVORITE";
    private static final int DB_VERSION = 2; // the version of the database

    StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private static void insertDrink(SQLiteDatabase db, String name, String description,
                                    int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put(NAME_COL, name);
        drinkValues.put(DESCRIPTION_COL, description);
        drinkValues.put(IMAGE_ID_COL, resourceId);
        db.insert(DRINK_TABLE, null, drinkValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE " + DRINK_TABLE + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME_COL + " TEXT, "
                    + DESCRIPTION_COL + " TEXT, "
                    + IMAGE_ID_COL + " INTEGER);");
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + DRINK_TABLE + " ADD COLUMN " + FAVORITE_COL + " NUMERIC;");
        }
    }
}
