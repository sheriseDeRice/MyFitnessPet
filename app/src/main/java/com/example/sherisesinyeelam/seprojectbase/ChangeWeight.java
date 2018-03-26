package com.example.sherisesinyeelam.seprojectbase;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeWeight extends AppCompatActivity {

    float weight ;

    EditText weightInput;

    Button weightButton;

    final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_weight);

        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        weightInput = (EditText) findViewById(R.id.weightInput);

        try {
            String currentValue = prefs.getString("Weight", "");

            weightInput.setText(""+Float.valueOf(currentValue));
        } catch (Exception e) {}


        weightButton = (Button) findViewById(R.id.weightButton);

        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    weight = Float.valueOf(weightInput.getText().toString());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putFloat("Weight",weight);
                    editor.apply();
                    Toast.makeText(ChangeWeight.this,"Changed", Toast.LENGTH_LONG).show();
                    //Log.d("checkAge", ""+prefs.getFloat("Weight", 1.0f));

                } catch (Exception e ){

                    Toast.makeText(ChangeWeight.this,"Invalid value", Toast.LENGTH_LONG).show();
                }
            }
        });

        

    }
}
