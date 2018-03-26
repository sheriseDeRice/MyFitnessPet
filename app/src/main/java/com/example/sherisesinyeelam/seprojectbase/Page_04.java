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

    DatabaseHandler dbh;

    ArrayList<Entry> list;

    int [] calPerDay;

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

        return pageFour;
    }

    public void getDailyCalories(){

        dbh = new DatabaseHandler(getContext());
        list = dbh.readFromDB("SELECT DISTINCT * FROM CaloriesIntake");

        for(int i = 0; i < list.size(); i++){

            //if(dayDiffCalculator(list.get(i).getDate(), "18/03/2018") <= 7){
            //    perDay+= list.get(i).getCalories();
            //}
            if(dayDiffCalculator("18/03/2018",list.get(i).getDate()) <= 30){

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
                new DataPoint(20,calPerDay[20]),
                new DataPoint(21,calPerDay[21]),
                new DataPoint(22,calPerDay[22]),
                new DataPoint(23,calPerDay[23]),
                new DataPoint(24,calPerDay[24]),
                new DataPoint(25,calPerDay[25]),
                new DataPoint(26,calPerDay[26]),
                new DataPoint(27,calPerDay[27]),
                new DataPoint(28,calPerDay[28]),
                new DataPoint(29,calPerDay[29])
        };
        return (dp);
    }

}
