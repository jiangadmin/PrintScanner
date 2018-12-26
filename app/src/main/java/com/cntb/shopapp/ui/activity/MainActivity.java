package com.cntb.shopapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cntb.shopapp.R;
import com.cntb.shopapp.common.Config;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private LinearLayout mTabFirstPage;
    private LinearLayout mTabWorkstation;
    private LinearLayout mTabMine;
    //底部的按钮
    private ImageButton mFirstpage ;
    private ImageButton mWorkstation;
    private ImageButton mMine;

    //FragmentLayout要加载的四个Fragment
    private Fragment tabone;
    private Fragment tabtwo;
    private Fragment tabthree;

    private TextView mFirsttitle;
    private TextView Worktitle;
    private TextView Minetitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSelect(0);
    }

    /*
     * 初始化点击
     */
    private void initEvent() {
        // TODO Auto-generated method stub
        mFirstpage.setOnClickListener(this);
        mWorkstation.setOnClickListener(this);
        mMine.setOnClickListener(this);
    }

    //初始化
    private void initView() {

        mFirstpage = findViewById(R.id.id_tab_fistpage_img);

        mTabFirstPage = findViewById(R.id.id_tab_toppage);
        mTabWorkstation = findViewById(R.id.id_tab_workdesk);
        mTabMine = findViewById(R.id.id_tab_mine);

        mWorkstation = findViewById(R.id.id_tab_workst_img);
        mMine = findViewById(R.id.id_tab_mine_img);

        mFirsttitle = findViewById(R.id.firsttitle);
        Worktitle = findViewById(R.id.worktitle);
        Minetitle = findViewById(R.id.mimetitle);
    }

    /**
     * 将tab 的text 初始化
     */
    public void reset() {
        mFirsttitle.setText(Config.tabs[0]);
        Worktitle.setText(Config.tabs[1]);
        Minetitle.setText(Config.tabs[2]);
        mFirstpage.setImageResource(R.drawable.main_normal);
        mWorkstation.setImageResource(R.drawable.workstation_normal);
        mMine.setImageResource(R.drawable.mine_normal);
    }

    //设置Fragment内容区域
    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trs = fm.beginTransaction();
        //隐藏Fragment
        hideFragment(trs);

        // 把text 切换为选中
        switch (i) {
            case 0:
                if (tabone == null) {
                    tabone = new Flagmentone();
                    trs.add(R.id.id_content, tabone);
                } else {
                    trs.show(tabone);
                }
                mFirstpage.setImageResource(R.drawable.main_press);
                break;
            case 1:
                if (tabtwo == null) {
                    tabtwo = new FlagmentTwo();
                    trs.add(R.id.id_content, tabtwo);
                } else {
                    trs.show(tabtwo);
                }
                mWorkstation.setImageResource(R.drawable.workstation_press);
                break;

            case 2:
                if (tabthree == null) {
                    tabthree = new FlagmentThree();
                    trs.add(R.id.id_content, tabthree);
                } else {
                    trs.show(tabthree);
                }
                mMine.setImageResource(R.drawable.mine_press);
                break;

            default:
                break;
        }
        trs.commit();
    }

    /*
     * 隐藏所有的Fragment
     */
    private void hideFragment(FragmentTransaction trs) {

        if (tabone != null) {
            trs.hide(tabone);
        }
        if (tabtwo != null) {
            trs.hide(tabtwo);
        }
        if (tabthree != null) {
            trs.hide(tabthree);
        }

    }

    @Override
    public void onClick(View v) {
        reset();
        switch (v.getId()) {
            case R.id.id_tab_fistpage_img:
                setSelect(0);
                break;
            case R.id.id_tab_workst_img:
                setSelect(1);

                break;
            case R.id.id_tab_mine_img:
                setSelect(2);
                break;

            default:
                break;
        }
    }


}
