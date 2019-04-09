package com.hfad.starbuzz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

class Controller {
    private static Controller instance;
    private StarbuzzDatabaseHelper helper;

    private Controller(Context context) {
        this.helper = new StarbuzzDatabaseHelper(context);
    }

    static synchronized Controller getInstance(Context context) {
        if (instance == null) {
            instance = new Controller(context);
        }
        return instance;
    }

    SimpleCursorAdapter getDrinks(Context context, int layout, int to) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(StarbuzzDatabaseHelper.DRINK_TABLE,
                    new String[]{StarbuzzDatabaseHelper.ID_COL,
                            StarbuzzDatabaseHelper.NAME_COL},
                    null, null, null, null, null);
        return new SimpleCursorAdapter(context,
                    layout,
                    cursor,
                    new String[]{StarbuzzDatabaseHelper.NAME_COL},
                    new int[]{to},
                    0);
    }
}
