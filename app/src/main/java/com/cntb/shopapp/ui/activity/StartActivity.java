package com.cntb.shopapp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cntb.shopapp.R;
import com.cntb.shopapp.common.PreferenceUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartActivity extends Activity {
    /**
     * 定义三个切换动画
     */
    private Animation mFadeIn;
    private Animation mFadeOut;
    private Animation mFadeInScale;

    @BindView(R.id.application_bg)
    ImageView applicationBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        DoAction();
    }

    private void DoAction() {
        boolean isFirst = PreferenceUtils.readBoolean(this, "First", "isFirst", true);
        if (isFirst) {
            PreferenceUtils.write(this, "First", "isFirst", false);
            Welcome();
        } else {
            ComeingApp();
        }
    }

    /**
     * 进入欢迎界面
     */
    private void Welcome() {
        Intent intent = new Intent();
        intent.setClass(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 直接进入app
     */
    private void ComeingApp() {
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        RandomApplicationBg();
        initAnim();
        setAnimListener();
    }

    /**
     * 建立监听事件
     */
    private void setAnimListener() {
        mFadeIn.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                applicationBg.startAnimation(mFadeInScale);
            }
        });
        mFadeInScale.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                applicationBg.startAnimation(mFadeOut);
            }
        });
        mFadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                Intent intent = new Intent();
                intent.setClass(StartActivity.this, MainActivity.class);    //跳转到MainActivity
                startActivity(intent);
                finish();
            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {

            }
        });
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        mFadeIn = AnimationUtils.loadAnimation(this, R.anim.application_fade_in);
        mFadeIn.setDuration(500);
        mFadeInScale = AnimationUtils.loadAnimation(this, R.anim.application_fade_in_scale);
        mFadeInScale.setDuration(2000);
        mFadeOut = AnimationUtils.loadAnimation(this, R.anim.application_fade_out);
        mFadeOut.setDuration(500);
        applicationBg.setAnimation(mFadeIn);
    }

    /**
     * 随机选择背景图片
     */
    private void RandomApplicationBg() {
        /**
         * 实现产生随机数，随机从本地获取图片或者从网络获取，从网络获取时随机取几张图片中的一张
         */
        int index = new Random().nextInt(2);
        if (index == 1) {
            applicationBg.setImageResource(R.mipmap.entrance1);
        } else {
            applicationBg.setImageResource(R.mipmap.entrance2);
        }
        /**
         if (!CommonFun.networkStatusOK(this))
         {
         int index = new Random().nextInt(2);
         if (index == 1) {
         applicationBg.setImageResource(R.mipmap.entrance1);
         } else {
         applicationBg.setImageResource(R.mipmap.entrance2);
         }
         }else
         {
         int index = new Random().nextInt(5);
         applicationBg.setImageURI(Uri.parse(Config.startpicUrls[index]));
         }
         */

    }

}
