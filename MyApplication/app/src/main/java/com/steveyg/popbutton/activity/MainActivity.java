package com.steveyg.popbutton.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.steveyg.popbutton.R;
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
                .setDurationTime(500)
                .setBackground(0x00ffffff)
                .setNumOfButton(3);
        
    }

}
