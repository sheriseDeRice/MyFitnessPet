package com.example.sherisesinyeelam.seprojectbase;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class changeAge extends AppCompatActivity {

    int age;

    EditText ageInput;

    Button ageSubmit;

    final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_age);

        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        ageInput = (EditText) findViewById(R.id.ageInput);

        try {
            int currentValue = prefs.getInt("Age", 0);

            ageInput.setText(""+ currentValue);

        } catch (Exception e) {}

        ageSubmit = (Button) findViewById(R.id.ageSubmit);

        ageSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    age = Integer.valueOf(ageInput.getText().toString());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("Age", age);
                    editor.apply();
                    Toast.makeText(changeAge.this, "Changed", Toast.LENGTH_LONG).show();
                    //Log.d("checkAge", ""+prefs.getInt("Age", 0));

                } catch (Exception e ){

                    Toast.makeText(changeAge.this,"Invalid value", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
