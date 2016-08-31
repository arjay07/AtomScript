package com.zeroseven.atomscript;

import android.app.Activity;

/**
 * Created by Leandro on 8/21/2016.
 */
public class CurrentActivity {

    private static Activity activity;

    public static void setActivity(Activity activity) {
        CurrentActivity.activity = activity;
    }

    public static Activity getActivity() {
        return activity;
    }

}
