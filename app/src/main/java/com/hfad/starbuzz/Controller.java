package com.hfad.starbuzz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.Set;

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

    static void closeCursors(Set<CursorAdapter> adapters) {
        for (CursorAdapter adapter : adapters) {
            if (adapter.getCursor() != null) {
                adapter.getCursor().close();
            }
        }
    }

    CursorAdapter getDrinkNames(Context context, int layout, int to)
        throws ModelException {
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = StarbuzzDatabaseHelper.getDrinkNames(db);
            return new SimpleCursorAdapter(context,
                    layout,
                    cursor,
                    new String[]{StarbuzzDatabaseHelper.NAME_COL},
                    new int[]{to},
                    0);
        } catch (SQLiteException e) {
            throw new ModelException(e.getMessage(), e.getCause());
        }
    }
}
