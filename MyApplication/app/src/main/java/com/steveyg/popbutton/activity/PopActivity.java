package com.steveyg.popbutton.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.steveyg.popbutton.R;
import com.steveyg.popbutton.model.PopModel;

/**
 * Created by steveyg on 2017/1/3.
 */

public class PopActivity extends Activity {

    private RelativeLayout bg;
    private PopModel model;
    private ImageView mainButton;
    private RotateAnimation mainButtonRotateStart, mainButtonRotateEnd;
    private int DURATION_TIME = 0;

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
        mainButtonRotateStart = new RotateAnimation(0, 0 + model.getRotateOfMainButton(), Animation.ABSOLUTE, model.getMainButton().getX() + model.getMainButton().getWidth() / 2, Animation.ABSOLUTE, model.getMainButton().getY() + model.getMainButton().getHeight() / 2);
        mainButtonRotateStart.setFillAfter(true);
        mainButtonRotateStart.setDuration(DURATION_TIME);

        mainButtonRotateEnd = new RotateAnimation(0 + model.getRotateOfMainButton(), 0,Animation.ABSOLUTE, model.getMainButton().getX() + model.getMainButton().getWidth() / 2, Animation.ABSOLUTE, model.getMainButton().getY() + model.getMainButton().getHeight() / 2);
        mainButtonRotateEnd.setFillAfter(true);
        mainButtonRotateEnd.setDuration(DURATION_TIME);

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
        mainButton = new ImageView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(model.getMainButton().getWidth(),model.getMainButton().getHeight());
        mainButton.setLayoutParams(params);
        mainButton.setBackground(model.getMainButton().getBackground());
        mainButton.setX(model.getMainButton().getX());
        mainButton.setY(model.getMainButton().getY() + model.getMainButtonOffsetY());

        bg.addView(mainButton);
    }

    private void startAnim() {
        mainButton.startAnimation(mainButtonRotateStart);
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
