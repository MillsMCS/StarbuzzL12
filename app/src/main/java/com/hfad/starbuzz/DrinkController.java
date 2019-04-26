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
class DrinkController {
    private static DrinkController instance;
    private StarbuzzDatabaseHelper helper;

    private DrinkController(Context context) {
        this.helper = new StarbuzzDatabaseHelper(context);
    }

    synchronized static DrinkController getInstance(Context context) {
        if (instance == null) {
            instance = new DrinkController(context);
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

    Drink getDrinkById(int drinkId) throws ModelException {
        try {
            Drink drink = StarbuzzDatabaseHelper.getDrinkById(helper.getReadableDatabase(), drinkId);
            if (drink != null) {
                return drink;
            }
        } catch (SQLiteException e) {
            throw new ModelException(e.getMessage(), e.getCause());
        }
        throw new ModelException("Unable to retrieve drink with id " + drinkId);
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
