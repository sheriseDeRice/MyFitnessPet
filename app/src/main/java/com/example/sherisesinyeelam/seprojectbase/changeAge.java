package com.example.sherisesinyeelam.seprojectbase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class changeAge extends AppCompatActivity {


    int age;

    EditText ageInput;

    Button ageSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_age);

        ageInput = (EditText) findViewById(R.id.ageInput);
        ageSubmit = (Button) findViewById(R.id.ageSubmit);

        ageSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = Integer.valueOf(ageInput.getText().toString());
            }
        });

    }
}
