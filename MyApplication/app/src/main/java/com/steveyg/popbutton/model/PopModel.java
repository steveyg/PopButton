package com.steveyg.popbutton.model;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by steveyg on 2017/1/3.
 */

public class PopModel implements Serializable {

    private static PopModel instance;
    private ImageView mainButton;

    /**
     * the time of Anim
     */
    private int durationTime = 0;
    /**
     * the offest of main button in Y axin
     * if zhe front activity has a system actionbar, maybe you need set this value
     */
    private float mainButtonOffsetY = 0;
    /**
     * the angle of mainButton when click the button,
     * it can hold still if this value is default or 0;
     */
    private int rotateOfMainButton = 0;

    private PopModel(ImageView mainButton){
        this.mainButton = mainButton;
    }

    public static PopModel getInstance(){
        return getInstance(null);
    }

    public static PopModel getInstance(ImageView mainButton) {
        if(instance == null){
            synchronized (PopModel.class){
                instance = new PopModel(mainButton);
            }
        }
        return instance;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public PopModel setDurationTime(int durationTime) {
        this.durationTime = durationTime;
        return this;
    }

    public ImageView getMainButton() {
        return mainButton;
    }

    public  float getMainButtonOffsetY() {
        return mainButtonOffsetY;
    }

    public PopModel setMainButtonOffsetY(float mainButtonOffsetY) {
        this.mainButtonOffsetY = mainButtonOffsetY;
        return this;
    }

    public int getRotateOfMainButton() {
        return rotateOfMainButton;
    }

    public PopModel setRotateOfMainButton(int rotateOfMainButton) {
        this.rotateOfMainButton = rotateOfMainButton;
        return this;
    }
}
