package com.example.sherisesinyeelam.seprojectbase;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Michal on 26.03.2018.
 */

public class LandingPage extends Fragment {
    public LandingPage(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View LandingPage = inflater.inflate(R.layout.landingpage, container, false);
        final SharedPreferences prefs = getContext().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);


        final Button confirmButton = (Button) LandingPage.findViewById(R.id.button6);

        if(!prefs.getString("FitnessPlan","").equals("")){
            confirmButton.setVisibility(View.INVISIBLE);
            replaceFragment(new Page_01());
        }

        Spinner genderSpinner = (Spinner) LandingPage.findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(LandingPage.getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.genders));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(myAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Gender","Male");
                    editor.apply();
                }
                else if (position == 2){
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Gender","Female");
                    editor.apply();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner fitnessSpinner = (Spinner) LandingPage.findViewById(R.id.spinner3);

        ArrayAdapter<String> fadapter = new ArrayAdapter<String>(LandingPage.getContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.fitness_plan));
        fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fitnessSpinner.setAdapter(fadapter);

        fitnessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            SharedPreferences.Editor editor = prefs.edit();

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    String plan = "Lose Weight";
                    editor.putString("FitnessPlan", "Lose Weight");
                    editor.apply();
                } else if (position == 2) {
                    String plan = "Gain Weight";
                    editor.putString("FitnessPlan", "Gain Weight");
                    editor.apply();
                } else if (position == 3) {
                    String plan = "Maintain Weight";
                    editor.putString("FitnessPlan", "Maintain Weight");
                    editor.apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final EditText editTextAge = (EditText) LandingPage.findViewById(R.id.editText3);
        final EditText editTextHeight = (EditText) LandingPage.findViewById(R.id.editText4);
        final EditText editTextWeight = (EditText) LandingPage.findViewById(R.id.editText5);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int age = Integer.valueOf(String.valueOf(editTextAge.getText()));
                    String height = String.valueOf(editTextHeight.getText());
                    String weight = String.valueOf(editTextWeight.getText());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("Age", age);
                    editor.putString("Height", height);
                    editor.putString("Weight", weight);
                    editor.apply();
                    confirmButton.setVisibility(View.INVISIBLE);
                    Log.d("Landing Page", prefs.getString("Height", ""));
                    replaceFragment(new Page_01());

                }catch (Exception e ){

                    Toast.makeText(getContext(),"Invalid value", Toast.LENGTH_LONG).show();
                }
            }
        });


        return LandingPage;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.landing_page, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
