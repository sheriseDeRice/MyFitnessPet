package com.example.sherisesinyeelam.seprojectbase;

import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ChangeFitnessPlan extends AppCompatActivity {

    boolean is_lose_weight=false;
    boolean is_gain_weight=false;
    boolean is_maintain_weight=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_fitness_plan);

        Spinner fitnessSpinner = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> fadapter = new ArrayAdapter<String>(ChangeFitnessPlan.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.fitness_plan));
        fadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fitnessSpinner.setAdapter(fadapter);

        fitnessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1)
                {
                    is_lose_weight=true;
                }
                else if(position==2){
                    is_gain_weight=true;
                }
                else if (position==3)
                {
                    is_maintain_weight=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
