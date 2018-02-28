package com.example.sherisesinyeelam.seprojectbase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SheriseSinYeeLam on 21/1/2018.
 */

public class Page_03  extends Fragment {

    public Page_03(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageThree = inflater.inflate(R.layout.page3, container, false);

        return PageThree;
    }
}

