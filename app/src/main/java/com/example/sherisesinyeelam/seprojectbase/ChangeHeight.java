package com.example.sherisesinyeelam.seprojectbase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeHeight extends AppCompatActivity {
    float height ;

    EditText heightInput;

    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_height);

        heightInput = (EditText) findViewById(R.id.heightInput);

        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height = Float.valueOf(heightInput.getText().toString());
            }
        });


    }

}

