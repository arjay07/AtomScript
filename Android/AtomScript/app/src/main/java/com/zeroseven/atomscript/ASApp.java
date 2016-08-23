package com.zeroseven.atomscript;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Leandro on 8/22/2016.
 */
public class ASApp {

    private Activity activity;
    private String title;
    private View[] views;

    public ASApp(String title, View[] views, Activity activity){

        this.activity = activity;
        this.title = title;
        this.views = views;

    }

    public void create(){

        UiActivity.setAppTitle(title);

    }

    public void addViews(){

        LinearLayout layout = UiActivity.getLayout();

        for(View view: views){

            layout.addView(view);

        }

    }

    public void start(){

        Intent intent = new Intent(activity.getBaseContext(), UiActivity.class);
        activity.startActivity(intent);

    }

}
