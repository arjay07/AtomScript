package com.zeroseven.atomscript.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zeroseven.atomscript.Console;
import com.zeroseven.atomscript.R;
import com.zeroseven.atomscript.UiActivity;

import java.util.concurrent.Callable;

/**
 * Created by Leandro on 8/15/2016.
 */
public class GUI {

    Console console;
    Console.View consoleView;
    Activity activity;

    public GUI(Activity activity, Console console){

        this.activity = activity;
        this.console = console;
        this.consoleView = console.getView();

    }

    public void alert(final String message){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Alert");
                builder.setMessage(message);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        console.resume();
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        console.pause();

    }

    public void alert(final String message, final String title){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        console.resume();
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        console.pause();

    }

    public boolean confirm(final String message){

        final boolean[] output = {false};

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Confirm");
                builder.setMessage(message);
                builder.setIcon(R.drawable.question);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        output[0] = true;
                        console.resume();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        console.resume();
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        console.pause();
        return output[0];

    }

    public boolean confirm(final String message, final String title){

        final boolean[] output = {false};

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setIcon(R.drawable.question);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        output[0] = true;
                        console.resume();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        console.resume();
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        console.pause();
        return output[0];

    }

    public String prompt(final String message){

        final String[] output = {null};

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final EditText input = new EditText(activity);
                input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                input.setMaxLines(1);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Prompt");
                builder.setMessage(message);
                builder.setIcon(R.drawable.question);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        output[0] = input.getText().toString();
                        console.resume();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        console.resume();
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        console.pause();
        return output[0];

    }

    public String prompt(final String message, final String title){

        final String[] output = {null};

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final EditText input = new EditText(activity);
                input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                input.setMaxLines(1);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.setIcon(R.drawable.question);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        output[0] = input.getText().toString();
                        console.resume();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        console.resume();
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        console.pause();
        return output[0];

    }

    public Button newButton(String text, final Callable<?> method){

        Button btn = new Button(activity);
        btn.setText(text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    method.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return btn;

    }

    public TextView newLabel(String text){

        TextView label = new TextView(activity);
        label.setText(text);

        return label;

    }

    public TextView newLabel(String text, Typeface font){

        TextView label = new TextView(activity);
        label.setText(text);
        label.setTypeface(font);

        return label;

    }

    public EditText newTextField(){

        EditText textField = new EditText(activity);

        return textField;

    }

    public EditText newTextField(String hint){

        EditText textField = new EditText(activity);
        textField.setHint(hint);

        return textField;

    }

    public Intent newApp(final String title, final View[] views){

        Intent intent = new Intent(activity.getBaseContext(), UiActivity.class);
        intent.putExtra("Title", title);
        activity.startActivity(intent);

        // test

        return intent;

    }

    public Dialog newDialog(final String title, final View[] views){

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);

        for(View view : views){

            layout.addView(view);

        }

        final Dialog  dialog = new Dialog(activity, android.R.style.Theme_Material_Light_DarkActionBar);
        dialog.setTitle(title);
        dialog.setContentView(layout);
        dialog.setCancelable(true);

        return dialog;

    }

    public void show(final Dialog dialog){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });

    }

    public void dismiss(final Dialog dialog){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });

    }

}
