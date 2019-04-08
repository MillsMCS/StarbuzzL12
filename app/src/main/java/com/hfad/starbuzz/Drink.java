package com.hfad.starbuzz;

/**
 * Created by davidg on 30/04/2017.
 */
class Drink {
    private String name; private String description; private int imageResourceId;
    //drinks is an array of Drinks
    static final Drink[] DRINKS = {
            new Drink("Latte", "A couple of espresso shots with steamed milk", R.drawable.latte),
            new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino),
            new Drink("Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter)
    };

    //Each Drink has a name, description, and an image resource
    Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    String getDescription() {
        return description;
    }

    String getName() {
        return name;
    }

    int getImageResourceId() {
        return imageResourceId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
