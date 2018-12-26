package com.cntb.shopapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.cntb.shopapp.R;

public class FlagmentTwo extends Fragment {
    private ImageButton btnOutStore;
    private ImageButton btnInStore;
    private ImageButton btnBackStore;

    private ImageView imageView;

    private View tab2view;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tab2view = inflater.inflate(R.layout.flagmenttwo, container, false);
        InitComponent();
        return tab2view;
    }

    private void InitComponent() {
        btnOutStore = tab2view.findViewById(R.id.btnOutStore);
        btnInStore = tab2view.findViewById(R.id.btnInStore);
        btnBackStore = tab2view.findViewById(R.id.btnBackStore);

        imageView = tab2view.findViewById(R.id.Serverimgshow);

        btnOutStore.setImageResource(R.drawable.ptoutstore);
        btnInStore.setImageResource(R.drawable.ptinstore);
        btnBackStore.setImageResource(R.drawable.ptbackstore);
        imageView.setImageResource(R.drawable.infostime);

        btnOutStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), OstoreActivity.class);
                startActivity(intent);
            }
        });

        btnInStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "功能目前未开放", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "功能目前未开放", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
