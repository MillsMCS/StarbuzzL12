package com.hfad.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "starbuzz"; // the name of our database
    private static final String DRINK_TABLE = "DRINK";
    static final String NAME_COL = "NAME";
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

    static Cursor getDrinkNames(SQLiteDatabase db) {
        return db.query(DRINK_TABLE,
                new String[]{ID_COL, NAME_COL},
                null, null, null, null, null);
    }

    static Drink getDrinkById(SQLiteDatabase db, int drinkId) throws SQLiteException {
        Cursor cursor = db.query(DRINK_TABLE,
                new String[]{NAME_COL, DESCRIPTION_COL, IMAGE_ID_COL},
                ID_COL + " = ?",
                new String[]{Integer.toString(drinkId)},
                null, null, null);
        //Move to the first record in the Cursor
        if (cursor.moveToFirst()) {
            //Get the drink details from the cursor
            String nameText = cursor.getString(0);
            String descriptionText = cursor.getString(1);
            int photoId = cursor.getInt(2);
            cursor.close();
            return new Drink(nameText, descriptionText, photoId);
        }
        return null;
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE " + DRINK_TABLE + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME_COL + " TEXT, "
                    + DESCRIPTION_COL + " TEXT, "
                    + IMAGE_ID_COL + " INTEGER);");
            for (Drink drinks : Drink.DRINKS) {
                insertDrink(db, drinks.getName(), drinks.getDescription(), drinks.getImageResourceId());
            }
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + DRINK_TABLE + " ADD COLUMN " + FAVORITE_COL + " NUMERIC;");
        }
    }
}
