package com.cntb.shopapp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cntb.shopapp.R;

public class FlagmentThree extends Fragment {
    private View tab3view;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tab3view=inflater.inflate(R.layout.flagmentthree, container, false);
        return tab3view;
    }
}
