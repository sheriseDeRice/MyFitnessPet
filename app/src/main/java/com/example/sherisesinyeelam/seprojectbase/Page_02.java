package com.example.sherisesinyeelam.seprojectbase;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.SystemClock;
import android.content.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.lang.Runnable;
import java.util.*;
import java.io.*;


/**
 * Created by SheriseSinYeeLam on 21/1/2018.
 */

public class Page_02  extends Fragment{

    final String startingDate; // store the starting date.

    String currentDate; // check current date

    String goalDate; // the end date for the fitness plan

    AppCompatActivity aca;

    Calendar c;

    SimpleDateFormat sdf;

    public Page_02(){

        c = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        // storing the starting date.
        startingDate = sdf.format(c.getTime());

        goalDate = "25 Mar 2018\n05-31-00 PM"; // get form setting
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View pageTwo = inflater.inflate(R.layout.page2, container, false);

        // testing start date
        final TextView tv = (TextView) pageTwo.findViewById(R.id.testStartDate);
        tv.setText(startingDate);
        //...

        setDefaultBackground(pageTwo); // set starting background, according to the time (day/night)

        dateAndTime(pageTwo); // the digital clock in the app

        final ImageButton hatchThePet = (ImageButton) pageTwo.findViewById(R.id.tamago);
        hatchThePet.setVisibility(View.VISIBLE);

        final ImageButton thePet = (ImageButton) pageTwo.findViewById(R.id.thePet);
        thePet.setVisibility(View.INVISIBLE);

        final ImageView theEmoji = (ImageView) pageTwo.findViewById(R.id.sb_emoji);
        theEmoji.setVisibility(View.INVISIBLE);

        hatchThePet.setOnClickListener(new View.OnClickListener() {
            // hatch the pet when started the application.
            @Override
            public void onClick(View view) {

                setDefaultHome(pageTwo); // set new background

                autoChangeBackground(pageTwo); // auto change background according to the time (day/night)

                thePet.setVisibility(View.VISIBLE); // make pet button visible

                showPetImage(pageTwo, thePet); // show the current pet type and body size

                hatchThePet.setVisibility(View.INVISIBLE);
                theEmoji.setVisibility(View.VISIBLE);
            }
        });

        thePet.setOnClickListener(new View.OnClickListener() {
            // change pet emoji when you touch it.

            private long mLastClickTime = 0; // variable to track event time

            @Override
            public void onClick(View view) {

                Log.d("Pet", "De Button has been pressed!");

                //In onClick check that if current time and last click time difference are less than i second
                // then don't do anything(return) else go for click event
                // Preventing multiple clicks, using threshold of 5 second
                if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                    return;
                }

                mLastClickTime = SystemClock.elapsedRealtime();

                theEmoji.setImageResource(getMood());

            }
        });

        return pageTwo;
    }

    // The following only get method as they are not a value that can be change by the user.

    // get the type of pet - dog or cat
    public int getPetType(){

        Random randomNumberGenerator = new Random();
        int number = randomNumberGenerator.nextInt(2);

        final int[] petStartType = {
                R.drawable.babycat_normal,
                R.drawable.babydog_normal
        };

        return petStartType[0]; // petStartType[number]
    }

    // get mood image
    public int getMood(){

        Random randomNumberGenerator = new Random();
        int number = randomNumberGenerator.nextInt(7);

        // an array of all the emoji image
        final int[] emojiArray = {
                R.drawable.sb_emoji1,
                R.drawable.sb_emoji2,
                R.drawable.sb_emoji3,
                R.drawable.sb_emoji4,
                R.drawable.sb_emoji5,
                R.drawable.sb_emoji6,
                R.drawable.sb_emoji7
        };

        return emojiArray[number];
    }

    // check the period of time, compare it with the starting date and goal date
    // compare the calories over time and change the pet body size if the user over ate for a period of time etc.
    public int getBabyBodySize(){

        // 0 = maintain; 1 = over ate; -1 = not ate enough.

        int bodySize;

        // below is temporary value, get data from entry part.
        //int[] caloriesList = {1275, 1300, 1100, 978, 1689, 2000, 1500}; // test skinny body size change
        // sum = 8742
        // avg = sum/7 = 1248.85714
        int[] caloriesList = {1675, 1600, 1500, 1978, 1689, 2000, 1751};
        // sum = 12193
        // avg = 1741.85714

        String goalDate = "26/04/2018"; // get from setting
        long diffTillNow = dayDiffCalculator(startingDate, currentDate);
        long diffTillEnd = dayDiffCalculator(startingDate, goalDate);

        if(caloriesCalculator(caloriesList) == 1 ){ //&& diffTillNow == diffTillEnd/3
            bodySize = R.drawable.babycat_fat;
        }
        else if(caloriesCalculator(caloriesList) == -1){ //&& diffTillNow == diffTillEnd/3
            bodySize = R.drawable.babycat_skinny;
        }
        else{
            bodySize = R.drawable.babycat_normal;
        }

        return bodySize;
    }

    // get the result of the fitness plan.
    // check by the over calories over time.
    // check the average of the whole database calories
    public int getAdultBodySize(){

        int bodySize;

        // below is temporary value, get data from entry part.
        int[] caloriesList = {1275, 1300, 1100, 978, 1689, 2000, 1500}; // test skinny body size change
        // sum = 8742
        // avg = sum/7 = 1248.85714
        //int[] caloriesList = {1675, 1600, 1500, 1978, 1689, 2000, 1751};
        // sum = 12193
        // avg = 1741.85714

        if(caloriesCalculator(caloriesList) == 1){
            bodySize = R.drawable.adultcat_fat;
        }
        else if(caloriesCalculator(caloriesList) == -1){
            bodySize = R.drawable.adultcat_skinny;
        }
        else{
            bodySize = R.drawable.adultcat_normal;
        }

        return bodySize;
    }

    // calculate the entry calories and compare with the goal.
    public int caloriesCalculator(int[] caloriesList){

        // 0 = maintain; 1 = over ate; -1 = not ate enough.

        int healthyStatus;

        int sum = 0;
        for(int i = 0; i < caloriesList.length; i++){
            sum = sum + caloriesList[i];
        }

        double goal = -2.5; // lose weight: negative, maintain use 0; gain weight use positive;
        // getGoal() from setting

        double caloriesNeeded; // the healthy calories range.

        // temporary value
        // getGender(), getWeight(), getHeight(), getAge from setting
        String gender = "woman";
        double height = 172; // cm
        double weight = 75; // kg
        double age = 20;
        //calories needed = (717.75 + 318.267888) - (20 * 4.7) + 655 = 1597.01789

        if(gender.equals("woman")){
            // b.m for women
            double w = (weight * 2.2) * 4.35;
            double h = (height * 0.393701) * 4.7;
            double add = w + h;
            double sub = add - (age * 4.7);
            caloriesNeeded = sub + 655;
        }
        else {
            // b.m for men
            double w = (weight*2.2) * 6.23;
            double h = (height * 0.393701) * 12.7;
            double add = w + h;
            double sub = add - (age * 6.8);
            caloriesNeeded = sub + 66;
        }

        // calculate the calories range
        // way 1:
        // no activity = bodyweight(kg) * 22 or (lbs) * 10 -> b.m
        // activity multiplier --> a.m
        // lightly active + 3-6days = 1.5 - 1.8
        // active + 3-6days = 1.7 - 2.0
        // very active + 3-6days = 1.9 - 2.2
        // m.c = b.m * a.m cal
        // way 2:
        // weight (lbs)
        // height (ins)
        // b.m: women - w = weight*4.35, h = height*4.7, add = w+h, subtract = add - age*4.7, result = subtract + 655
        // b.m: men - w = weight*6.23, h = height*12.7, add = w+h, subtract = add - age*6.8, result = subtract + 66

        double weeklyAvg = sum/7;

        if (weeklyAvg > caloriesNeeded){
            healthyStatus = 1;
        }
        else if (weeklyAvg < caloriesNeeded){
            healthyStatus = -1;
        }
        else{
            healthyStatus = 0;
        }

        return healthyStatus;
    }

    // calculate the date differences between two date (in string)
    public long dayDiffCalculator(String a, String b){

        long dateDiff = 0;

        Date d1 = null;
        Date d2 = null;

        try{
            d1 = sdf.parse(a);
            d2 = sdf.parse(b);

            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            dateDiff = diff; //in milliseconds

//            System.out.print(diffDays + " days, ");
//            System.out.print(diffHours + " hours, ");
//            System.out.print(diffMinutes + " minutes, ");
//            System.out.print(diffSeconds + " seconds.");

        } catch (Exception e){}

        return dateDiff;
    }

    // show current date and time with seconds -- the digital clock
    public void dateAndTime(final View pageTwo){

        aca = new AppCompatActivity();

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        aca.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tdate = (TextView) pageTwo.findViewById(R.id.textDate);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy\nhh-mm-ss a");
                                currentDate = sdf.format(date);
                                tdate.setText(currentDate);
                            }
                        });
                    }
                } catch (InterruptedException e) {}
            }
        };
        t.start();
    }

    // change image button over a period of time depends on the weekly calories that the user ate and the goal date
    public void showPetImage(final View pageTwo, final ImageButton thePet){

        aca = new AppCompatActivity();

        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {

                        aca.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(getPetType() == R.drawable.babycat_normal){
                                    thePet.setImageResource(getBabyBodySize());
                                }

                                if(currentDate.equals(goalDate)){
                                    thePet.setImageResource(getAdultBodySize());
                                    return; // return to the while loop
                                }
                            }
                        });
                        Thread.sleep(1000);

                        if (thePet.getDrawable().getConstantState() == getResources().getDrawable(getAdultBodySize()).getConstantState()){
                            // stop the threads when the pet became a adult pet
                            thePet.setImageResource(getAdultBodySize());
                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        t.start();

    }

    public void setDefaultBackground(final View pageTwo){

        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 7 && timeOfDay < 17){
            pageTwo.setBackgroundResource(R.drawable.default_background_day);
        }
        else{
            pageTwo.setBackgroundResource(R.drawable.default_background_night);
        }
    }

    public void setDefaultHome(final View pageTwo){

        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 7 && timeOfDay < 17){
            pageTwo.setBackgroundResource(R.drawable.default_home_day);
        }
        else{
            pageTwo.setBackgroundResource(R.drawable.default_home_night);
        }
    }

    public void autoChangeBackground(final View pageTwo){

        aca = new AppCompatActivity();

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        aca.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                setDefaultHome(pageTwo);
                            }
                        });
                    }
                } catch (InterruptedException e) {}
            }
        };
        t.start();

    }
}

