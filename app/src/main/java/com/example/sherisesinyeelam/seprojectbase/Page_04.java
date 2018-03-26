package com.example.sherisesinyeelam.seprojectbase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.jjoe64.graphview.*;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rafat on 26/03/2018.
 */

public class Page_04 extends Fragment {

    GraphView graphView;

    SimpleDateFormat sdf;
    TextView txt;

    DatabaseHandler dbh;

    ArrayList<Entry> list;

    int [] calPerDay;
    int average;

    public Page_04() {

        sdf = new SimpleDateFormat("dd/MM/yyyy");

        calPerDay = new int[30];
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View pageFour = inflater.inflate(R.layout.page4, container, false);
        super.onCreate(savedInstanceState);

        graphView = pageFour.findViewById(R.id.graph);
        getDailyCalories();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataPoint());
        graphView.addSeries(series);

        average = getAverage();

        txt = (TextView) pageFour.findViewById(R.id.bot_text);

        if(average < 1000){
            txt.setText("\nThe monthly average is " + average + ".\n\nYou are under eating!! You need to eat more!");
        }
        else if(average > 3000){
            txt.setText("\nThe monthly average is " + average + ".\n\nYou are over eating!! You need to eat less!");
        }
        else{
            txt.setText("\nThe monthly average is " + average + ".\n\nGood job!! You should keep it up!");
        }

        return pageFour;
    }
    public int getAverage(){

        dbh = new DatabaseHandler(getContext());
        list = dbh.readFromDB("SELECT DISTINCT * FROM CaloriesIntake");

        int sum = 0;

        for(int i = 0; i < list.size(); i++){

            //if(dayDiffCalculator(list.get(i).getDate(), "18/03/2018") <= 7){
            //    perDay+= list.get(i).getCalories();
            //}
            if(dayDiffCalculator("18/03/2018",list.get(i).getDate()) <= 30){

                sum += list.get(i).getCalories();

            }
        }

        average = sum/30;

        return average;
    }

    public void getDailyCalories(){

        dbh = new DatabaseHandler(getContext());
        list = dbh.readFromDB("SELECT DISTINCT * FROM CaloriesIntake");

        for(int i = 0; i < list.size(); i++){

            //if(dayDiffCalculator(list.get(i).getDate(), "18/03/2018") <= 7){
            //    perDay+= list.get(i).getCalories();
            //}
            if(dayDiffCalculator("18/03/2018",list.get(i).getDate()) <= 20){

                long diff = dayDiffCalculator("18/03/2018",list.get(i).getDate());
                Log.d("diff", String.valueOf(diff));
                Log.d("day", list.get(i).getDate());
                calPerDay[(int)diff] += list.get(i).getCalories();
            }
        }
        return;
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

    private DataPoint[] getDataPoint(){

        DataPoint[] dp = new DataPoint[] {

                new DataPoint(0,calPerDay[0]),
                new DataPoint(1,calPerDay[1]),
                new DataPoint(2,calPerDay[2]),
                new DataPoint(3,calPerDay[3]),
                new DataPoint(4,calPerDay[4]),
                new DataPoint(5,calPerDay[5]),
                new DataPoint(6,calPerDay[6]),
                new DataPoint(7,calPerDay[7]),
                new DataPoint(8,calPerDay[8]),
                new DataPoint(9,calPerDay[9]),
                new DataPoint(10,calPerDay[10]),
                new DataPoint(11,calPerDay[11]),
                new DataPoint(12,calPerDay[12]),
                new DataPoint(13,calPerDay[13]),
                new DataPoint(14,calPerDay[14]),
                new DataPoint(15,calPerDay[15]),
                new DataPoint(16,calPerDay[16]),
                new DataPoint(17,calPerDay[17]),
                new DataPoint(18,calPerDay[18]),
                new DataPoint(19,calPerDay[19]),
                new DataPoint(20,calPerDay[20])

        };
        return (dp);
    }

}
