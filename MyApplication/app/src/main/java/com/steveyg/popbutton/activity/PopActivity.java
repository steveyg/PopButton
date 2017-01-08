package com.steveyg.popbutton.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
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

    private AbsoluteLayout bg;
    private PopModel model;
    private ImageView mainButton;
    private RotateAnimation mainButtonRotateStart, mainButtonRotateEnd;
    private int DURATION_TIME = 0;
    private ArrayList<ImageView> mButtons = new ArrayList<>();
    private ArrayList<AnimationSet> animSetStrat = new ArrayList<>();
    private ArrayList<AnimationSet> animSetEnd = new ArrayList<>();
    private RotateAnimation menuRotateStart,menuRotateEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop);

        initData();
        initView();
        drawView();
        initAnim();
        startAnim();
    }

    private void initData() {
        model = PopModel.getInstance();
        DURATION_TIME = model.getDurationTime();
    }

    private void initAnim() {
        //init main button anim
        float startX = model.getMainButton().getX() + model.getMainButton().getWidth() / 2;
        float startY = model.getMainButton().getY() + model.getMainButton().getHeight() / 2;
        mainButtonRotateStart = new RotateAnimation(0, 0 + model.getRotateOfMainButton(), Animation.ABSOLUTE, startX, Animation.ABSOLUTE, startY);
        mainButtonRotateStart.setFillAfter(true);
        mainButtonRotateStart.setDuration(DURATION_TIME);

        mainButtonRotateEnd = new RotateAnimation(0 + model.getRotateOfMainButton(), 0, Animation.ABSOLUTE, startX, Animation.ABSOLUTE, startY);
        mainButtonRotateEnd.setFillAfter(true);
        mainButtonRotateEnd.setDuration(DURATION_TIME);

        //init tran anim
        float startDegree = 0;
        float endDegree = 180;
        int flag = 1;
        switch (model.getMenuDirection()) {
            case PopModel.UP:
                startDegree = 0;
                endDegree = 180;
                flag = 1;
                break;
            case PopModel.DOWN:
                startDegree = 180;
                endDegree = 360;
                flag = 1;
                break;
            case PopModel.LEFT:
                startDegree = 90;
                endDegree = 270;
                flag = 1;
                break;
            case PopModel.RIGHT:
                startDegree = 270;
                endDegree = 450;
                flag = 1;
                break;
            case PopModel.LEFT_UP:
                startDegree = 100;
                endDegree = 170;
                flag = -1;
                break;
            case PopModel.LEFT_DOWN:
                startDegree = 190;
                endDegree = 260;
                flag = -1;
                break;
            case PopModel.RIGHT_UP:
                startDegree = 10;
                endDegree = 80;
                flag = -1;
                break;
            case PopModel.RIGHT_DOWN:
                startDegree = 280;
                endDegree = 350;
                flag = -1;
                break;
        }

        float radius = model.getRadius();
        for (int i = 0; i < model.getNumOfButton(); i++) {
            //start anim
            AnimationSet start = new AnimationSet(false);
            float degree = (endDegree - startDegree) / (model.getNumOfButton() + flag) * (i + Math.max(0,flag)) + startDegree;
            float radian = (float) ((Math.PI / 180) * degree);
            float endX = 0;
            float endY = 0;

            endX = (float) (radius * Math.cos(radian));
            endY = (float) (radius * Math.sin(radian));
            degree = degree % 360;
            if (degree < 90) {
                endX = Math.abs(endX);
                endY = -Math.abs(endY);
            } else if (degree < 180) {
                endX = -Math.abs(endX);
                endY = -Math.abs(endY);
            } else if (degree < 270) {
                endX = -Math.abs(endX);
                endY = Math.abs(endY);
            } else if (degree < 360) {
                endX = Math.abs(endX);
                endY = Math.abs(endY);
            }
            TranslateAnimation tranStart = new TranslateAnimation(0, endX, 0, endY);
            start.setFillAfter(true);
            start.setDuration(DURATION_TIME);
            tranStart.setInterpolator(new OvershootInterpolator());
            start.addAnimation(tranStart);

            //            menuRotateStart = new RotateAnimation(model.getRotateOfMenuButton(),0);
            menuRotateStart = new RotateAnimation(model.getRotateOfMenuButton(),0,Animation.ABSOLUTE,500,Animation.ABSOLUTE,500);
            start.addAnimation(menuRotateStart);
            menuRotateStart.setDuration(200);
            menuRotateStart.setFillAfter(true);
            menuRotateStart.setStartOffset(DURATION_TIME);
            animSetStrat.add(start);

            //end anim
            AnimationSet end = new AnimationSet(false);

            TranslateAnimation tranEnd = new TranslateAnimation(endX,0, endY,0);
            end.setFillAfter(true);
            end.setDuration(DURATION_TIME);
            end.addAnimation(tranEnd);
            animSetEnd.add(end);



            menuRotateEnd = new RotateAnimation(0,model.getRotateOfMenuButton(),Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//            end.addAnimation(menuRotateEnd);
        }
    }

    private void initView() {
        bg = (AbsoluteLayout) findViewById(R.id.bg);
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
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(model.getMainButton().getWidth(), model.getMainButton().getHeight());
            AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(model.getMainButton().getWidth(), model.getMainButton().getHeight(),(int)model.getMainButton().getX(),(int)model.getMainButton().getY());
            button.setLayoutParams(params);
            if (model.getButtonImageResource().length > i) {
                button.setImageResource(model.getButtonImageResource()[i]);
            }
//            button.setX(model.getMainButton().getX());
//            button.setY(model.getMainButton().getY() + model.getMainButtonOffsetY());
//            button.setPivotX(model.getMainButton().getPivotX());
//            button.setPivotY(model.getMainButton().getPivotY());
            int [] location = new int[2];
            button.getLocationOnScreen(location);
            System.out.println(location[0] + "  " + location[1]);
            bg.addView(button);
            mButtons.add(button);
        }

        mainButton = new ImageView(this);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(model.getMainButton().getWidth(), model.getMainButton().getHeight());
        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(model.getMainButton().getWidth(), model.getMainButton().getHeight(),(int)model.getMainButton().getX(),(int)model.getMainButton().getY());
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
        for (int i = 0; i < model.getNumOfButton(); i++) {
            mButtons.get(i).startAnimation(animSetEnd.get(i));
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
