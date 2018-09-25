package com.example.sherisesinyeelam.seprojectbase;

import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Michal on 25.03.2018.
 */

public class AddEntry extends Fragment {

    private DatabaseHandler db;
    private boolean isActivity;
    private boolean isEditing;
    private String name;
    private int calories;
    private String date;

    public AddEntry(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View AddEntry = inflater.inflate(R.layout.add_entry, container, false);

        db = new DatabaseHandler(getContext());


        final EditText editName = (EditText) AddEntry.findViewById(R.id.editEntryName);
        final EditText editCalories = (EditText) AddEntry.findViewById(R.id.editEntryCal);
        final EditText editDate = (EditText) AddEntry.findViewById(R.id.editEntryDate);

        if(name != null){
            editName.setText(name, TextView.BufferType.EDITABLE);
            editCalories.setText(String.valueOf(calories), TextView.BufferType.EDITABLE);
            editDate.setText(date, TextView.BufferType.EDITABLE);
        }

        final Button confirmButton = (Button) AddEntry.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = String.valueOf(editName.getText());
                calories = Integer.parseInt(String.valueOf(editCalories.getText()));
                date = String.valueOf(editDate.getText());
                if(isActivity) calories = -calories;
                boolean result = db.writeToDB(name,calories,date);
                Log.d("Write", String.valueOf(result));
                confirmButton.setVisibility(View.INVISIBLE);
                replaceFragment(new Page_01());
            }
        });

        return AddEntry;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.addEntry_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setActivity(Boolean activity) {
        isActivity = activity;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
