package com.example.sherisesinyeelam.seprojectbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by SheriseSinYeeLam on 21/1/2018.
 */

public class Page_03  extends Fragment {
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    public Page_03(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageThree = inflater.inflate(R.layout.page3, container, false);

        button = PageThree.findViewById(R.id.button);
        button2 = PageThree.findViewById(R.id.button2);
        button3 = PageThree.findViewById(R.id.button3);
        button4= PageThree.findViewById(R.id.button4);
        button5=PageThree.findViewById(R.id.button5);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getActivity(), ChangeWeight.class);
                startActivity(inte);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeHeight.class);
                startActivity(intent);
            }

        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), ChangeGender.class);
                startActivity(intent2);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), changeAge.class);
                startActivity(intent3);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(getActivity(),ChangeFitnessPlan.class);
                startActivity(intent4);
            }
        });
        return PageThree;
    }


}

