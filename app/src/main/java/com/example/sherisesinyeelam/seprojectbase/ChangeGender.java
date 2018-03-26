package com.example.sherisesinyeelam.seprojectbase;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeGender extends AppCompatActivity {
    boolean ismale=false;
    boolean isfemale=false;
    final String PREFS_NAME = "MyPrefsFile";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_gender);
        Spinner genderSpinner = (Spinner) findViewById(R.id.spinner1);
        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(ChangeGender.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.genders));

        TextView tv = (TextView) findViewById(R.id.oldGenderValue);

        try {
            String currentValue = prefs.getString("Gender", "");

            if(currentValue.equals("Male")){
                tv.setText("Your current value: " + currentValue);

            }else if (currentValue.equals("Female")){
                tv.setText("Your current value: " + currentValue);

            }

        } catch (Exception e) {}

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(myAdapter);


        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){
                    ismale= true;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Gender","Male");
                    editor.apply();
                    Toast.makeText(ChangeGender.this,"Changed", Toast.LENGTH_LONG).show();

                }
                 else if (position == 2){

                    isfemale = true;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Gender","Female");
                    editor.apply();
                    Toast.makeText(ChangeGender.this,"Changed", Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });



        }
    }

