package com.zeroseven.atomscript;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Leandro on 8/15/2016.
 */
public class UiActivity extends Activity {

    private Intent intent;
    private String title;

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);
        intent = getIntent();
        title = intent.getStringExtra("Title");
        setTitle(title);

    }

    public LinearLayout getLayout(){

        return layout;

    }

}
