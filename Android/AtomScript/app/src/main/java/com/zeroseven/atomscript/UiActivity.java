package com.zeroseven.atomscript;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mozilla.javascript.Context;

import java.io.IOException;

/**
 * Created by Leandro on 8/15/2016.
 */
public class UiActivity extends AppCompatActivity{

    private static AtomScript atomScript;
    private static ASEvaluator evaluator;
    private static Context context;

    private static String title;
    private static LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic);

        layout = (LinearLayout) findViewById(R.id.basic_layout);
        layout.setOrientation(LinearLayout.VERTICAL);

        atomScript = ConsoleActivity.getAtomScript();
        if(atomScript != null){
            initAtomScript();
            createApp();
        }

        setTitle(title);

    }

    public void initAtomScript(){

        evaluator = atomScript.getEvaluator();
        context = Context.enter();
        context.setOptimizationLevel(-1);
        evaluator.setContext(context);

        atomScript.setActivity(this);
        atomScript.setEvaluator(evaluator);
        atomScript.init();

    }

    public void createApp(){

        ASApp app = atomScript.getApp();
        app.create();
        app.addViews();

    }

    public static LinearLayout getLayout() {
        return layout;
    }

    public static void setAppTitle(String s){

        title = s;

    }

    public static String getAppTitle(){

        return title;

    }

}
