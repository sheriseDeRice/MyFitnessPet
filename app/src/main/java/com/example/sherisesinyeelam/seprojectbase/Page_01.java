package com.example.sherisesinyeelam.seprojectbase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SheriseSinYeeLam on 21/1/2018.
 */


public class Page_01 extends Fragment{
    // extents AppCompatActivity
    DatabaseHandler db;
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

        //txt= pageOne.findViewById(R.id.test_txt);
        ImageButton imageButton = (ImageButton)pageOne.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(listenerImageButton);
        final ListView listView = (ListView)pageOne.findViewById(R.id.listView);

        db = new DatabaseHandler(getContext());

        try {
            db.create();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Entry> dbentries = db.readFromDB("SELECT DISTINCT * FROM CaloriesIntake LIMIT 16");
        ArrayAdapter adapter = new ListAdapter(this.getContext(), R.layout.dblist,dbentries){

        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                Entry entry = (Entry) item;
                AddEntry addEntry = new AddEntry();
                addEntry.setName(entry.getName());
                addEntry.setCalories(entry.getCalories());
                replaceFragment(addEntry);
                fab_sport.startAnimation(FabClose);
                fab_meal.startAnimation(FabClose);
                fab_plus.startAnimation(FabRanticlockwise);
                fab_meal.setClickable(false);
                fab_sport.setClickable(false);
                isOpen = false;

            }
        });

        aca = new AppCompatActivity();
        fab_plus = (FloatingActionButton) pageOne.findViewById(R.id.fab_plus);
        fab_meal = (FloatingActionButton) pageOne.findViewById(R.id.fab_meal);
        fab_sport = (FloatingActionButton) pageOne.findViewById(R.id.fab_sport);
        FabOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        FabClose =  AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        FabRClockwise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_clockwise);
        FabRanticlockwise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_anticlockwise);
        fab_meal.setOnClickListener(fabMealListener);
        fab_sport.setOnClickListener(fabSportListener);

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

        EditText editText = (EditText) pageOne.findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // EditText tv = (EditText) editable;
                ArrayList<Entry> resultsTV = searchFor(editable.toString());
                Log.d("EditText", editable.toString());
                ArrayAdapter adapter = new ListAdapter(getContext(), R.layout.dblist,resultsTV);
                listView.setAdapter(adapter);
//                if(resultsTV.size() == 0){
//
//
//                }
            }
        });


        return pageOne;
    }

    private View.OnClickListener fabMealListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AddEntry addEntry = new AddEntry();
            addEntry.setActivity(false);
            replaceFragment(addEntry);
            fab_sport.startAnimation(FabClose);
            fab_meal.startAnimation(FabClose);
            fab_plus.startAnimation(FabRanticlockwise);
            fab_meal.setClickable(false);
            fab_sport.setClickable(false);
            isOpen = false;
        }
    };

    private View.OnClickListener fabSportListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AddEntry addEntry = new AddEntry();
            addEntry.setActivity(true);
            replaceFragment(addEntry);
            fab_sport.startAnimation(FabClose);
            fab_meal.startAnimation(FabClose);
            fab_plus.startAnimation(FabRanticlockwise);
            fab_meal.setClickable(false);
            fab_sport.setClickable(false);
            isOpen = false;
        }
    };


    private View.OnClickListener listenerImageButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    };

    public ArrayList<String> getDatabase(String filter){
        // ArrayList<String> results = this.db.readFromDB(filter);
        return null;
    }

    private ArrayList<Entry> searchFor(String word){

        ArrayList<Entry> dbentries = new DatabaseHandler(getContext()).readFromDB("SELECT DISTINCT * FROM CaloriesIntake WHERE Name LIKE '%"+word+"%' ");
        Log.d("SearchFor", String.valueOf(dbentries.size()));
        return dbentries;


    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.page1_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
