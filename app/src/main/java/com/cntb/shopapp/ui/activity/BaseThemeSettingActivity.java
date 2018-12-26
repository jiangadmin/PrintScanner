package com.cntb.shopapp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cntb.shopapp.R;
import com.jude.utils.JUtils;

public class BaseThemeSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTheme();
    }

    private void setCustomTheme() {
        int theme = JUtils.getSharedPreference().getInt("THEME", 0);
        switch (theme) {
            case 1:
                setTheme(R.style.AppTheme1);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                break;
            default:
                setTheme(R.style.AppThemeDefault);
                break;
        }
    }
}
