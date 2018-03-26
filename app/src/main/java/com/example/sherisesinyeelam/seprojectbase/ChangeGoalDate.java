package com.example.sherisesinyeelam.seprojectbase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Created by SheriseSinYeeLam on 26/3/2018.
 */
public class ChangeGoalDate extends AppCompatActivity {

    String goal;

    EditText goalInput;

    Button dateSubmit;

    final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_goal_date);

        final SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        goalInput = (EditText) findViewById(R.id.goalInput);

        try {
            String currentValue = prefs.getString("EndingDate", "");

            goalInput.setText(currentValue);

        } catch (Exception e) {}

        dateSubmit = (Button) findViewById(R.id.dateSubmit);

        dateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    goal = goalInput.getText().toString();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("EndingDate", goal);
                    editor.apply();
                    Toast.makeText(ChangeGoalDate.this, "Changed", Toast.LENGTH_LONG).show();
                    //Log.d("checkAge", ""+prefs.getFloat("Height", 1.0f));

                } catch (Exception e) {

                    Toast.makeText(ChangeGoalDate.this, "Invalid value", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}