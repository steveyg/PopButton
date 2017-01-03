package com.steveyg.popbutton.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.steveyg.popbutton.activity.PopActivity;
import com.steveyg.popbutton.model.PopModel;

/**
 * Created by steveyg on 2017/1/3.
 */

public class PopButton extends ImageView {

    private PopModel popmodel;

    public PopButton(Context context) {
        super(context);
        init();
    }

    public PopButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PopButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public PopButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        popmodel = PopModel.getInstance(this);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PopActivity.class);
                getContext().startActivity(i);
            }
        });
    }

    public PopModel getPopmodel() {
        return popmodel;
    }
}
