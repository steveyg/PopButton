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

    private RelativeLayout bg;
    private PopModel model;
    private ImageView mainButton;
    private RotateAnimation mainButtonRotateStart, mainButtonRotateEnd;
    private int DURATION_TIME = 0;
    private int MENU_ROTATE_TIME = 0;
    private int MENU_ROTATE_DELAY_TIME = 0;
    private ArrayList<ImageView> mButtons = new ArrayList<>();
    private ArrayList<AnimationSet> animSetStrat = new ArrayList<>();
    private ArrayList<AnimationSet> animSetEnd = new ArrayList<>();
    private RotateAnimation menuRotateStart,menuRotateEnd;
    private ArrayList<ImageView> displayButtons = new ArrayList<>();
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
        MENU_ROTATE_TIME = model.getRotateTime();
        MENU_ROTATE_DELAY_TIME = model.getRotateDelayTime();
    }

    private void initAnim() {
        //init main button anim
        final float startX = model.getMainButton().getX() + model.getMainButton().getWidth() / 2;
        final float startY = model.getMainButton().getY() + model.getMainButton().getHeight() / 2;
        mainButtonRotateStart = new RotateAnimation(0, model.getRotateOfMainButton(), Animation.ABSOLUTE, startX, Animation.ABSOLUTE, startY);
        mainButtonRotateStart.setFillAfter(true);
        mainButtonRotateStart.setDuration(DURATION_TIME);

        mainButtonRotateEnd = new RotateAnimation(model.getRotateOfMainButton(), 0, Animation.ABSOLUTE, startX, Animation.ABSOLUTE, startY);
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
//            degree = degree + model.getRotateOfMenuButton();
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
            tranStart.setDuration(DURATION_TIME);
            tranStart.setInterpolator(new OvershootInterpolator());
            start.addAnimation(tranStart);
            final int finali = i;
            final float finalEndX = endX;
            final float finalEndY = endY;
            start.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    ImageView button = new ImageView(PopActivity.this);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(model.getMainButton().getWidth(), model.getMainButton().getHeight());
                    button.setLayoutParams(params);
                    if (model.getButtonImageResource().length > finali) {
                            button.setImageResource(model.getButtonImageResource()[finali]);
                    }
                    button.setX(startX + finalEndX - mButtons.get(finali).getWidth()/2);
                    button.setY(startY +  finalEndY - mButtons.get(finali).getHeight()/2);
                    if (model.getButtonClickListener().size() > finali) {
                        button.setOnClickListener(model.getButtonClickListener().get(finali));
                    }
                    bg.addView(button);
                    displayButtons.add(button);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            menuRotateStart = new RotateAnimation(0,model.getRotateOfMenuButton(),Animation.ABSOLUTE, startX + endX, Animation.ABSOLUTE, startY + endY);
            start.addAnimation(menuRotateStart);
            menuRotateStart.setDuration(MENU_ROTATE_TIME);
            menuRotateStart.setFillAfter(true);
            menuRotateStart.setStartOffset(MENU_ROTATE_DELAY_TIME);
            animSetStrat.add(start);

            //end anim
            AnimationSet end = new AnimationSet(false);
            TranslateAnimation tranEnd = new TranslateAnimation(endX,0, endY,0);
            tranEnd.setStartOffset(MENU_ROTATE_DELAY_TIME);
            end.setFillAfter(true);
            tranEnd.setDuration(DURATION_TIME);
            end.addAnimation(tranEnd);

            menuRotateEnd = new RotateAnimation(0,-model.getRotateOfMenuButton(),Animation.ABSOLUTE, startX + endX, Animation.ABSOLUTE, startY + endY);
            menuRotateEnd.setFillAfter(true);
            menuRotateEnd.setDuration(MENU_ROTATE_TIME);
            end.addAnimation(menuRotateEnd);

            end.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    for(int i = 0;i < displayButtons.size();i++){
                        displayButtons.get(i).setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animSetEnd.add(end);
        }
    }

    private void initView() {
        bg = (RelativeLayout) findViewById(R.id.bg);
        bg.setBackgroundColor(model.getBackground());
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endAnim();
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
            button.setPivotX(model.getMainButton().getPivotX());
            button.setPivotY(model.getMainButton().getPivotY());
            bg.addView(button);
            mButtons.add(button);
        }

        mainButton = new ImageView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(model.getMainButton().getWidth(), model.getMainButton().getHeight());
        mainButton.setLayoutParams(params);
        mainButton.setBackground(model.getMainButton().getBackground());
        mainButton.setX(model.getMainButton().getX());
        mainButton.setY(model.getMainButton().getY() + model.getMainButtonOffsetY());
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
