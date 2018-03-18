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

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.lang.Runnable;

/**
 * Created by SheriseSinYeeLam on 21/1/2018.
 */

public class Page_02  extends Fragment {

    AppCompatActivity aca;

    ImageView currentPet;

    public Page_02(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View pageTwo = inflater.inflate(R.layout.page2, container, false);

        dateAndTime(pageTwo);

//        TextView date = (TextView) pageTwo.findViewById(R.id.textDate);
//        String Date= DateFormat.getDateTimeInstance().format(new Date());
//        date.setText(Date);

        final ImageButton hatchThePet = (ImageButton) pageTwo.findViewById(R.id.tamago);
        hatchThePet.setVisibility(View.VISIBLE);

        final ImageView thePet = (ImageView) pageTwo.findViewById(R.id.thePet);
        thePet.setVisibility(View.INVISIBLE);

        final ImageButton petThePet = (ImageButton) pageTwo.findViewById(R.id.tap_Pet);
        petThePet.setVisibility(View.INVISIBLE);

        final ImageView theEmoji = (ImageView) pageTwo.findViewById(R.id.sb_emoji);
        theEmoji.setVisibility(View.INVISIBLE);

        final int[] emojiArray = {
                R.mipmap.sb_emoji1,
                R.mipmap.sb_emoji2,
                R.mipmap.sb_emoji3,
                R.mipmap.sb_emoji4,
                R.mipmap.sb_emoji5,
                R.mipmap.sb_emoji6,
                R.mipmap.sb_emoji7
        };

        final int[] petStartType = {
                R.drawable.babycat_normalsize
                //R.drawable.babydog_normal
        };

        hatchThePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random randomNumberGenerator = new Random();
                int number = randomNumberGenerator.nextInt(2);

                
                thePet.setVisibility(View.VISIBLE);
                thePet.setImageResource(petStartType[number]);

                //when play is clicked hide hatch button
                hatchThePet.setVisibility(View.INVISIBLE);
                theEmoji.setVisibility(View.VISIBLE);
                petThePet.setVisibility(View.VISIBLE);
            }
        });

        petThePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Pet", "De Button has been pressed!");

                Random randomNumberGenerator = new Random();
                int number = randomNumberGenerator.nextInt(7);

                theEmoji.setImageResource(emojiArray[number]);

            }
        });

        return pageTwo;
    }

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

