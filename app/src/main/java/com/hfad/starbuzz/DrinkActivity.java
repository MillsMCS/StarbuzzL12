package com.hfad.starbuzz;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends Activity {
    static final String EXTRA_DRINKID = "drinkId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //Get the drinkId from the intent
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);

        try {
            Drink drink = Controller.getInstance(this).getDrinkById(drinkId);

            //Populate the drink name
            TextView name = findViewById(R.id.name);
            name.setText(drink.getName());

            //Populate the drink description
            TextView description = findViewById(R.id.description);
            description.setText(drink.getDescription());

            //Populate the drink image
            ImageView photo = findViewById(R.id.photo);
            photo.setImageResource(drink.getImageResourceId());
            photo.setContentDescription(drink.getName());
        } catch (ModelException e) {
            Toast toast = Toast.makeText(this, "Unable to retrieve drink", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
