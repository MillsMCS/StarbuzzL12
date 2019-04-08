package com.hfad.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class DrinkCategoryActivity extends Activity {
    private Controller controller;
    private Set<CursorAdapter> adapters;

    public DrinkCategoryActivity() {
        this.controller = Controller.getInstance(this);
        adapters = new HashSet<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        ListView listDrinks = (ListView) findViewById(R.id.list_drinks);
        try {
            CursorAdapter adapter = controller.getDrinkNames(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1);
            adapters.add(adapter);
            listDrinks.setAdapter(adapter);
        } catch(ModelException e) {
            Toast toast = Toast.makeText(this, "Unable to retrieve drink information", Toast.LENGTH_SHORT);
            toast.show();
        }

        //Create the listener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> listDrinks,
                                            View itemView,
                                            int position,
                                            long id) {
                        //Pass the drink the user clicks on to DrinkActivity
                        Intent intent = new Intent(DrinkCategoryActivity.this,
                                DrinkActivity.class);
                        intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                        startActivity(intent);
                    }
                };

        //Assign the listener to the list view
        listDrinks.setOnItemClickListener(itemClickListener);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Controller.closeCursors(adapters);
    }
}
