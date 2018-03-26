package com.example.sherisesinyeelam.seprojectbase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeWeight extends AppCompatActivity {

    float weight ;

    EditText weightInput;

    Button weightButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_weight);

        weightInput = (EditText) findViewById(R.id.weightInput);
        weightButton = (Button) findViewById(R.id.weightButton);

        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = Float.valueOf(weightInput.getText().toString());
            }
        });

        

    }
}
