package com.steveyg.popbutton.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.steveyg.popbutton.R;
import com.steveyg.popbutton.model.PopModel;

import java.util.ArrayList;

/**
 * Created by steveyg on 2017/1/3.
 */

public class PopActivity extends Activity {

    private RelativeLayout bg;
    private PopModel model;
    private ImageView mainButton;
    private RotateAnimation mainButtonRotateStart, mainButtonRotateEnd;
    private int DURATION_TIME = 0;
    private ArrayList<ImageView> mButtons = new ArrayList<>();
    private ArrayList<AnimationSet> animSetStrat = new ArrayList<>();
    private ArrayList<AnimationSet> animSetEnd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop);

        initData();
        initAnim();
        initView();
        drawView();
        startAnim();
    }

    private void initData() {
        model = PopModel.getInstance();
        DURATION_TIME = model.getDurationTime();
    }

    private void initAnim() {
        float startX = model.getMainButton().getX() + model.getMainButton().getWidth() / 2;
        float startY = model.getMainButton().getY() + model.getMainButton().getHeight() / 2;
        mainButtonRotateStart = new RotateAnimation(0, 0 + model.getRotateOfMainButton(), Animation.ABSOLUTE, startX, Animation.ABSOLUTE, startY);
        mainButtonRotateStart.setFillAfter(true);
        mainButtonRotateStart.setDuration(DURATION_TIME);

        mainButtonRotateEnd = new RotateAnimation(0 + model.getRotateOfMainButton(), 0, Animation.ABSOLUTE, startX, Animation.ABSOLUTE, startY);
        mainButtonRotateEnd.setFillAfter(true);
        mainButtonRotateEnd.setDuration(DURATION_TIME);

        float startDegree = 0;
        float endDegree = 180;
        float radius = model.getRadius();
        for (int i = 0; i < model.getNumOfButton(); i++) {
            AnimationSet start = new AnimationSet(true);
            float degree = (endDegree - startDegree) / (model.getNumOfButton() + 1) * (i + 1) + startDegree;
            float endX = 0;
            float endY = 0;
            if(degree < 90){
                degree = 180 - degree;
                endX = (float) (radius * Math.cos(degree));
                endY = (float) (radius * Math.sin(degree));
                endX = Math.abs(endX);
                endY = -Math.abs(endY);
            }else if(degree < 180){
                endX = (float) (radius * Math.cos(degree));
                endY = (float) (radius * Math.sin(degree));
                endX = -Math.abs(endX);
                endY = -Math.abs(endY);
            }else if(degree < 270){
                endX = (float) (radius * Math.sin(degree));
                endY = (float) (radius * Math.cos(degree));
                endX = -Math.abs(endX);
                endY = Math.abs(endY);
            }else if(degree < 360){
                endX = (float) (radius * Math.cos(degree));
                endY = (float) (radius * Math.sin(degree));
                endX = Math.abs(endX);
                endY = Math.abs(endY);
            }
            System.out.println(degree + "  " +  Math.sin(degree) + "   " + Math.cos(degree));
            System.out.println(degree + "  " + startX + "   " + startY + "  "+ endX + "   "+ endY);
//            TranslateAnimation tranStart = new TranslateAnimation(Animation.ABSOLUTE, startX, Animation.ABSOLUTE, endX, Animation.ABSOLUTE, startY, Animation.ABSOLUTE, endY);
            TranslateAnimation tranStart = new TranslateAnimation(0,endX,0,endY);
            start.setFillAfter(true);
            start.setDuration(DURATION_TIME);
            start.addAnimation(tranStart);
            animSetStrat.add(start);
        }
    }

    private void initView() {
        bg = (RelativeLayout) findViewById(R.id.bg);
        bg.setBackgroundColor(model.getBackground());
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endAnim();
//                finish();
            }
        });


    }

    private void drawView() {
        for (int i = 0; i < model.getNumOfButton(); i++) {
            ImageView button = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(model.getMainButton().getWidth(), model.getMainButton().getHeight());
            button.setLayoutParams(params);
            if (model.getButtonImageResource().length > i) {
                button.setImageResource(model.getButtonImageResource()[i]);
            }
            button.setX(model.getMainButton().getX());
            button.setY(model.getMainButton().getY() + model.getMainButtonOffsetY());
            bg.addView(button);
            mButtons.add(button);
        }

        mainButton = new ImageView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(model.getMainButton().getWidth(), model.getMainButton().getHeight());
        mainButton.setLayoutParams(params);
        mainButton.setBackground(model.getMainButton().getBackground());
        mainButton.setX(model.getMainButton().getX());
        mainButton.setY(model.getMainButton().getY() + model.getMainButtonOffsetY());
//        mainButton.setVisibility(View.GONE);
        bg.addView(mainButton);
    }

    private void startAnim() {
        mainButton.startAnimation(mainButtonRotateStart);

        for (int i = 0; i < model.getNumOfButton(); i++) {
            mButtons.get(i).startAnimation(animSetStrat.get(i));
        }
    }

    private void endAnim() {
        mainButtonRotateEnd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mainButton.startAnimation(mainButtonRotateEnd);
    }

}
