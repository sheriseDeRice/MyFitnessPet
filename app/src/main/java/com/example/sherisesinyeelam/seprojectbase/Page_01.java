package com.example.sherisesinyeelam.seprojectbase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by SheriseSinYeeLam on 21/1/2018.
 */


public class Page_01 extends Fragment{
    // extents AppCompatActivity

    Button btn1;
    Button btn2;

    TextView txt;

    AppCompatActivity aca;

    FloatingActionButton fab_plus, fab_meal, fab_sport;
    Animation FabOpen, FabClose, FabRClockwise, FabRanticlockwise;
    boolean isOpen = false;

    public Page_01(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View pageOne = inflater.inflate(R.layout.page1, container, false);
        super.onCreate(savedInstanceState);

        txt= pageOne.findViewById(R.id.test_txt);

        aca = new AppCompatActivity();
        fab_plus = (FloatingActionButton) pageOne.findViewById(R.id.fab_plus);
        fab_meal = (FloatingActionButton) pageOne.findViewById(R.id.fab_meal);
        fab_sport = (FloatingActionButton) pageOne.findViewById(R.id.fab_sport);
        FabOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        FabClose =  AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        FabRClockwise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_clockwise);
        FabRanticlockwise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_anticlockwise);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isOpen){
                    fab_sport.startAnimation(FabClose);
                    fab_meal.startAnimation(FabClose);
                    fab_plus.startAnimation(FabRanticlockwise);
                    fab_meal.setClickable(false);
                    fab_sport.setClickable(false);
                    isOpen = false;
                }
                else{
                    fab_sport.startAnimation(FabOpen);
                    fab_meal.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabRClockwise);
                    fab_meal.setClickable(true);
                    fab_sport.setClickable(true);
                    isOpen = true;
                }

            }
        });

        btn1 = pageOne.findViewById(R.id.add_entry);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt.setText("Here shows all entry.");

            }
        });

        btn2 = pageOne.findViewById(R.id.show_progress);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt.setText("Here shows the progress graph.");
            }
        });


        return pageOne;
    }


}
