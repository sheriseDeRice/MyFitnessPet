package com.example.sherisesinyeelam.seprojectbase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.os.SystemClock;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.lang.Runnable;
import java.util.*;
import java.io.*;
import android.content.*;

/**
 * Created by SheriseSinYeeLam on 21/1/2018.
 */

public class Page_02  extends Fragment{

    String startingDate; // store the starting date.

    AppCompatActivity aca;

    ImageView currentPet; // change image according to a period of time ( goal date and calories)

    public Page_02(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View pageTwo = inflater.inflate(R.layout.page2, container, false);

        // storing the starting date.
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
        startingDate = sdf.format(c.getTime());

        try {
            // storing the starting date to a .txt file.
            String fileName = "AppStartDate.txt";
            OutputStreamWriter outputWriter = new OutputStreamWriter(getContext().openFileOutput( fileName, Context.MODE_PRIVATE));
            String fileContent = startingDate;
            outputWriter.write(fileContent);
            outputWriter.close();
        } catch (IOException e){}

        // the digital clock in the application.
        dateAndTime(pageTwo);

        final ImageButton hatchThePet = (ImageButton) pageTwo.findViewById(R.id.tamago);
        hatchThePet.setVisibility(View.VISIBLE);

        //final ImageView thePet = (ImageView) pageTwo.findViewById(R.id.thePet);
        // change to pet the pet directly to the pet image to change it's mood.
        final ImageButton thePet = (ImageButton) pageTwo.findViewById(R.id.thePet);
        thePet.setVisibility(View.INVISIBLE);

        // an extra button to pet the pet.
//        final ImageButton petThePet = (ImageButton) pageTwo.findViewById(R.id.tap_Pet);
//        petThePet.setVisibility(View.INVISIBLE);

        final ImageView theEmoji = (ImageView) pageTwo.findViewById(R.id.sb_emoji);
        theEmoji.setVisibility(View.INVISIBLE);

        hatchThePet.setOnClickListener(new View.OnClickListener() {
            // hatch the pet when started the application.
            @Override
            public void onClick(View view) {

                pageTwo.setBackgroundResource(R.drawable.default_home);

                thePet.setVisibility(View.VISIBLE);
                thePet.setImageResource(R.drawable.babycat_normal); //getPetType()

                //when play is clicked hide hatch button
                hatchThePet.setVisibility(View.INVISIBLE);
                theEmoji.setVisibility(View.VISIBLE);
                //petThePet.setVisibility(View.VISIBLE);
            }
        });

        //petThePet.setOnClickListener(new View.OnClickListener() {
        thePet.setOnClickListener(new View.OnClickListener() {
            // change pet emoji when you touch it.

            private long mLastClickTime = 0; // variable to track event time

            @Override
            public void onClick(View view) {

                Log.d("Pet", "De Button has been pressed!");

//              2. In onClick check that if current time and last click time difference are less than i second
//              then don't do anything(return) else go for click event

                // Preventing multiple clicks, using threshold of 1 second
                if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                theEmoji.setImageResource(getMood());

            }
        });

        return pageTwo;
    }

    // The following only get method as they are not a value that can be change by the user.!

    // get the type of pet - dog or cat
    public int getPetType(){
        Random randomNumberGenerator = new Random();
        int number = randomNumberGenerator.nextInt(2);

        final int[] petStartType = {
                R.drawable.babycat_normal,
                R.drawable.babydog_normal
        };

        return petStartType[number];
    }

//    public int getBodySize(){
//        int bodySize;
//        // check the period of time, compare it with the starting date and goal date
//        // compare the calories over time and change the pet body size if the user over ate for a period of time etc.
//        if(){
//            bodySize = R.drawable.babycat_fats;
//        }
//        else if(){
//            bodySize = R.drawable.babycat_skinny;
//        }
//        else{
//            bodySize = R.drawable.babycat_normal;
//        }
//
//        return bodySize;
//    }

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

    // show current date and time with seconds,
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
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }
}

