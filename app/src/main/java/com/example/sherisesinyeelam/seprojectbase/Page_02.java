package com.example.sherisesinyeelam.seprojectbase;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

    DatabaseHandler dbh;

    ArrayList<Entry> list;

    SharedPreferences prefs;

    public Page_02(){

        c = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        // storing the starting date.
        startingDate = sdf.format(c.getTime());

        goalDate = "27 Mar 2018\n11-50-00 PM"; // get form setting
        //goalDate = prefs.getString("EndingDate", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View pageTwo = inflater.inflate(R.layout.page2, container, false);
        super.onCreate(savedInstanceState);

        prefs = getContext().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);

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

                thePet.setVisibility(View.VISIBLE); // make pet button visible

                autoChangeBackground(pageTwo); // auto change background according to the time (day/night)

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

//        Log.d("startLand", "create1");
//        ////LANDING PAGE <----->
//        if(prefs.getString("FitnessPlan","").equals("")){
//
//            Log.d("Land", "create2");
//            replaceFragment(new LandingPage());
//            Log.d("Landed", "create3");
//
//        }
//        Log.d("EndLand", "create4");
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

        dbh = new DatabaseHandler(getContext());
        list = dbh.readFromDB("SELECT DISTINCT * FROM CaloriesIntake");

        int bodySize;

        // below is temporary value, get data from entry part.
        //int[] caloriesList = {1275, 1300, 1100, 978, 1689, 2000, 1500}; // test normal body size change
        // sum = 9842
        // avg = sum/7 = 1406
        //int[] caloriesList = {1875, 2221, 2800, 1978, 1689, 2160, 2751}; // test fat body size
        // sum = 15474
        // avg = 2210.57143
        //int[] caloriesList = {875, 1000, 1100, 978, 789, 1132, 1045}; // test skinny body size change
        // sum = 6919
        // avg = sum/7 = 988.428571

        int sumCal = getSumCalories();

        // used to change pet image after a period of time (takes time), but now is not needed, used default as sample.
        long diffTillNow = dayDiffCalculator(startingDate, currentDate);
        long diffTillEnd = dayDiffCalculator(startingDate, goalDate);


        // to use default value, uncomment the array above, and change sumcal to caloriesList
        if(caloriesCalculator(sumCal) == 1.0){
            // && diffTillNow == diffTillEnd/3 +- 3)
            bodySize = R.drawable.babycat_fat;
        }
        else if(caloriesCalculator(sumCal) == -1.0){
            // && diffTillNow == diffTillEnd/3 +- 3)
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
        //int[] caloriesList = {1275, 1300, 1100, 978, 1689, 2000, 1500}; // test normal body size change
        // sum = 9842
        // avg = sum/7 = 1406
        //int[] caloriesList = {1875, 2221, 2800, 1978, 1689, 2160, 2751}; // test fat body size
        // sum = 15474
        // avg = 2210.57143
        //int[] caloriesList = {875, 1000, 1100, 978, 789, 1132, 1045}; // test skinny body size change
        // sum = 6919
        // avg = sum/7 = 988.428571

        int sumCal = getSumCalories();

        if(caloriesCalculator(sumCal) == 1){
            bodySize = R.drawable.adultcat_fat;
        }
        else if(caloriesCalculator(sumCal) == -1){
            bodySize = R.drawable.adultcat_skinny;
        }
        else{
            bodySize = R.drawable.adultcat_normal;
        }

        return bodySize;
    }

    // calculate the entry calories and compare with the goal.
    public int caloriesCalculator(int sumCal){
        //int[] caloriesList

        // 0 = maintain; 1 = over ate; -1 = not ate enough.

        int healthyStatus;

        // used to add up to calories when using default value.
//        int sum = 0;
//        for(int i = 0; i < caloriesList.length; i++){
//            sum = sum + caloriesList[i];
//        }

        double caloriesNeeded; // the healthy calories range.

        String gender = prefs.getString("Gender", "");
        int age = prefs.getInt("Age", 0);
        double height = Float.parseFloat(prefs.getString("Height", ""));
        double weight = Float.parseFloat(prefs.getString("Weight",""));

        // temporary value
//        String gender = "woman";
//        double height = 172; // cm
//        double weight = 75; // kg
//        double age = 20;
        //calories needed = (717.75 + 318.267888) - (20 * 4.7) + 655 = 1597.01789

        if(gender.equals("Female")){
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

        double weeklyAvg = sumCal/7;

        if (weeklyAvg > (caloriesNeeded + 500)){
            healthyStatus = 1;
        }
        else if (weeklyAvg < (caloriesNeeded - 500)){
            healthyStatus = -1;
        }
        else{
            healthyStatus = 0;
        }

        return healthyStatus;
    }

    public int getSumCalories(){
        ArrayList<Integer> calories = new ArrayList<>();
        int perDay = 0;

        String oldest;
        String newest;

        for(int i = 0; i < list.size(); i++){

            if(dayDiffCalculator(list.get(i).getDate(), "18/03/2018") <= 7){
                perDay+= list.get(i).getCalories();
            }
        }
        return perDay;
    }

    // calculate the date differences between two date (in string) in days
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

            dateDiff = diffDays;

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
    public void showPetImage(final View pageTwo, final ImageButton thePet){ //final ImageView theEmoji

        aca = new AppCompatActivity();

        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // change to weekly

                    while (!isInterrupted()) {

                        try {
                            aca.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (getPetType() == R.drawable.babycat_normal) {
                                        thePet.setImageResource(getBabyBodySize());

                                        if (currentDate.equals(goalDate)) {
                                            thePet.setImageResource(getAdultBodySize());
                                            //theEmoji.setImageResource(R.drawable.);
                                            return; // return to the while loop
                                        }
                                    }

                                }
                            });

                            Thread.sleep(1000);

                            if (thePet.getDrawable().getConstantState() == getResources().getDrawable(getAdultBodySize()).getConstantState()) {
                                // stop the threads when the pet became a adult pet
                                thePet.setImageResource(getAdultBodySize());
                                return;
                            }
                        } catch (Exception e){}

                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        t.start();

    }

    // set the background when first run the app --> before hatching the pet
    public void setDefaultBackground(final View pageTwo){

        Calendar c = Calendar.getInstance();

        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 7 && timeOfDay < 17){
            pageTwo.setBackgroundResource(R.drawable.default_background_day);
        }
        else{
            pageTwo.setBackgroundResource(R.drawable.default_background_night);
        }
    }

    // auto change background according to the time (day/night)
    public void autoChangeBackground(final View pageTwo){

        aca = new AppCompatActivity();

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {

                        aca.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar c = Calendar.getInstance();
                                int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
                                if(timeOfDay >= 7 && timeOfDay < 17){
                                    pageTwo.setBackgroundResource(R.drawable.default_home_day);
                                }
                                else{
                                    pageTwo.setBackgroundResource(R.drawable.default_home_night);
                                }
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {}
            }
        };
        t.start();

    }

//    public void replaceFragment(Fragment fragment) {
//
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.home, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

}

