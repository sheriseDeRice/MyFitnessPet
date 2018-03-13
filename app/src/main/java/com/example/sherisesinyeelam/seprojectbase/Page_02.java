package com.example.sherisesinyeelam.seprojectbase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by SheriseSinYeeLam on 21/1/2018.
 */

public class Page_02  extends Fragment {

    public Page_02(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageTwo = inflater.inflate(R.layout.page2, container, false);

        return PageTwo;
    }
}

