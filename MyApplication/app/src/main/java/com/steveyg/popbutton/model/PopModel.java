package com.steveyg.popbutton.model;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by steveyg on 2017/1/3.
 */

public class PopModel implements Serializable {

    private static PopModel instance;
    private ImageView mainButton;

    /**
     * direction of menu button
     */
    private int menuDirection = UP;

    public final static int UP = 1;
    public final static int DOWN = 2;
    public final static int LEFT = 3;
    public final static int RIGHT = 4;
    public final static int LEFT_UP = 5;
    public final static int LEFT_DOWN = 6;
    public final static int RIGHT_UP = 7;
    public final static int RIGHT_DOWN = 8;

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

    private int rotateOfMenuButton = 0;

    private int background = 0xffffffff;

    private int numOfButton = 0;
    private int[] buttonImageResource;
    private int[] buttonBackground;
    private ArrayList<View.OnClickListener> buttonClickListener = new ArrayList<>();

    /**
     * the distance of button move
     */
    private float radius = 0;

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

    public int getBackground() {
        return background;
    }

    public PopModel setBackground(int background) {
        this.background = background;
        return this;
    }

    public int getMenuDirection() {
        return menuDirection;
    }

    public PopModel setMenuDirection(int menuDirection) {
        this.menuDirection = menuDirection;
        return this;
    }

    public int[] getButtonImageResource() {
        return buttonImageResource;
    }

    public void setButtonImageResource(int[] buttonImageResource) {
        this.buttonImageResource = buttonImageResource;
    }

    public int getNumOfButton() {
        return numOfButton;
    }

    public PopModel setNumOfButton(int numOfButton) {
        this.numOfButton = numOfButton;
        return this;
    }

    public int[] getButtonBackground() {
        return buttonBackground;
    }

    public void setButtonBackground(int[] buttonBackground) {
        this.buttonBackground = buttonBackground;
    }

    public ArrayList<View.OnClickListener> getButtonClickListener() {
        return buttonClickListener;
    }

    public void setButtonClickListener(ArrayList<View.OnClickListener> buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    public float getRadius() {
        return radius;
    }

    public PopModel setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public int getRotateOfMenuButton() {
        return rotateOfMenuButton;
    }

    public PopModel setRotateOfMenuButton(int rotateOfMenuButton) {
        this.rotateOfMenuButton = rotateOfMenuButton;
        return this;
    }
}
