package com.steveyg.popbutton.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.steveyg.popbutton.R;
import com.steveyg.popbutton.model.PopModel;
import com.steveyg.popbutton.view.PopButton;

import java.util.ArrayList;
import java.util.List;

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
                .setRotateOfMenuButton(360)
                .setDurationTime(400)
                .setRotateTime(170)
                .setRotateDelayTime(125)
                .setBackground(0x55000000)
                .setNumOfButton(3)
                .setRadius(250)
                .setMenuDirection(PopModel.UP);

        int[] res = {R.drawable.icon1,R.drawable.icon2,R.drawable.icon3};
        mPopButton.getPopmodel().setButtonImageResource(res);

        ArrayList<View.OnClickListener> clickListenerList = new ArrayList<>();
        for(int i = 0; i < 3 ; i++){
            final int fi = i + 1;
            clickListenerList.add(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"click button" + fi ,Toast.LENGTH_SHORT).show();
                }
            });
        }
        mPopButton.getPopmodel().setButtonClickListener(clickListenerList);

    }

}
