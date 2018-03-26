package com.example.sherisesinyeelam.seprojectbase;

import java.util.ArrayList;

/**
 * Created by Michal on 25.03.2018.
 */

public class Entry {
    protected String Name;
    protected int Calories;
    protected String Date;

    public Entry(String name, int calories, String date) {
        Name = name;
        Calories = calories;
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public int getCalories() {
        return Calories;
    }

    public String getDate() {
        return Date;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    public void setDate(String date) {
        Date = date;
    }

}
