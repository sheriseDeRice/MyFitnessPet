package com.example.sherisesinyeelam.seprojectbase;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeHeight extends AppCompatActivity {
    String height ;

    EditText heightInput;

    Button submitButton;

    final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_height);

        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        heightInput = (EditText) findViewById(R.id.heightInput);

        try {
            String currentValue = prefs.getString("Height", "");

            heightInput.setText("" + Float.valueOf(currentValue));
        } catch (Exception e) {}

        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    height = String.valueOf(heightInput.getText().toString());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Height", height);
                    editor.apply();
                    Toast.makeText(ChangeHeight.this, "Changed", Toast.LENGTH_LONG).show();
                    //Log.d("checkAge", ""+prefs.getFloat("Height", 1.0f));

                } catch (Exception e ){

                    Toast.makeText(ChangeHeight.this,"Invalid value", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}

