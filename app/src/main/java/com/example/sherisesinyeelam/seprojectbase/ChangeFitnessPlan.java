package com.example.sherisesinyeelam.seprojectbase;

import android.content.SharedPreferences;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeFitnessPlan extends AppCompatActivity {

    boolean is_lose_weight=false;
    boolean is_gain_weight=false;
    boolean is_maintain_weight=false;
    final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_fitness_plan);

        Spinner fitnessSpinner = (Spinner) findViewById(R.id.spinner2);
        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        ArrayAdapter<String> fadapter = new ArrayAdapter<String>(ChangeFitnessPlan.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.fitness_plan));

        TextView tv = (TextView) findViewById(R.id.oldFitValue);

        try {
            String currentValue = prefs.getString("FitnessPlan", "");

            if(currentValue.equals("Lose Weight")){
                tv.setText("Your current value: " + currentValue);

            }else if (currentValue.equals("Gain Weight")){
                tv.setText("Your current value: " + currentValue);

            }else if(currentValue.equals("Maintain Weight")){
                tv.setText("Your current value: " + currentValue);
            }

        } catch (Exception e) {}


        fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fitnessSpinner.setAdapter(fadapter);

        fitnessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1)
                {
                    is_lose_weight=true;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("FitnessPlan","Lose Weight");
                    editor.apply();
                    Toast.makeText(ChangeFitnessPlan.this,"Changed", Toast.LENGTH_LONG).show();
                }
                else if(position==2){
                    is_gain_weight=true;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("FitnessPlan","Gain Weight");
                    editor.apply();
                    Toast.makeText(ChangeFitnessPlan.this,"Changed", Toast.LENGTH_LONG).show();
                }
                else if (position==3)
                {
                    is_maintain_weight=true;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("FitnessPlan","Maintain Weight");
                    editor.apply();
                    Toast.makeText(ChangeFitnessPlan.this,"Changed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
