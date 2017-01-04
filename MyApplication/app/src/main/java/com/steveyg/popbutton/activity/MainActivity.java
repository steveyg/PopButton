package com.steveyg.popbutton.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.steveyg.popbutton.R;
import com.steveyg.popbutton.model.PopModel;
import com.steveyg.popbutton.view.PopButton;

public class MainActivity extends Activity {

    private PopButton mPopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mPopButton = (PopButton) findViewById(R.id.popbutton);
        mPopButton.getPopmodel()
                .setRotateOfMainButton(45)
                .setDurationTime(350)
                .setBackground(0x55000000)
                .setNumOfButton(3)
                .setRadius(200)
                .setMenuDirection(PopModel.UP);

        int[] res = {R.drawable.icon1,R.drawable.icon2,R.drawable.icon3};
        mPopButton.getPopmodel().setButtonImageResource(res);

    }

}
