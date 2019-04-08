package com.hfad.starbuzz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

/**
 * MVC controller.
 *
 * @author Ellen Spertus
 */
class Controller {
    private static Controller instance;
    private StarbuzzDatabaseHelper helper;

    private Controller(Context context) {
        this.helper = new StarbuzzDatabaseHelper(context);
    }

    synchronized static Controller getInstance(Context context) {
        if (instance == null) {
            instance = new Controller(context);
        }
        return instance;
    }

    CursorAdapter getDrinkNames(Context context, int layout, int to) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = StarbuzzDatabaseHelper.getDrinkNames(db);
        return new SimpleCursorAdapter(context,
                layout,
                cursor,
                new String[]{StarbuzzDatabaseHelper.NAME_COL},
                new int[]{to},
                0);

    }
}
