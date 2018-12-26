package com.cntb.shopapp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cntb.shopapp.R;

public class Flagmentone extends Fragment {

    private View tab1view;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tab1view=inflater.inflate(R.layout.flagmentone, container, false);
        return tab1view;
    }
}
