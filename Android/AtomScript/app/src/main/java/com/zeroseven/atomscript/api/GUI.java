package com.zeroseven.atomscript.api;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zeroseven.atomscript.ASApp;
import com.zeroseven.atomscript.Console;
import com.zeroseven.atomscript.ConsoleActivity;
import com.zeroseven.atomscript.CurrentActivity;
import com.zeroseven.atomscript.R;

import java.util.concurrent.Callable;

/**
 * Created by Leandro on 8/15/2016.
 */
public class GUI {

    Console console;
    Console.View consoleView;

    private Drawable question, error;

    public GUI(Console console){

        this.console = console;
        this.consoleView = console.getView();

    }

    public void alert(final String message){

        CurrentActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentActivity.getActivity());
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

        CurrentActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentActivity.getActivity());
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

        CurrentActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentActivity.getActivity());
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

        CurrentActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentActivity.getActivity());
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

        CurrentActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final EditText input = new EditText(CurrentActivity.getActivity());
                input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                input.setMaxLines(1);

                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentActivity.getActivity());
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

    public String prompt(final String message, final String title){

        final String[] output = {null};

        CurrentActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final EditText input = new EditText(CurrentActivity.getActivity());
                input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                input.setMaxLines(1);

                AlertDialog.Builder builder = new AlertDialog.Builder(CurrentActivity.getActivity());
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

        Button btn = new Button(CurrentActivity.getActivity());
        btn.setTag("button");
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

        TextView label = new TextView(CurrentActivity.getActivity());
        label.setTag("label");
        label.setText(text);

        return label;

    }

    public TextView newLabel(String text, Typeface font){

        TextView label = new TextView(CurrentActivity.getActivity());
        label.setTag("label");
        label.setText(text);
        label.setTypeface(font);

        return label;

    }

    public EditText newTextField(){

        EditText textField = new EditText(CurrentActivity.getActivity());
        textField.setTag("textfield");

        return textField;

    }

    public EditText newTextField(String hint){

        EditText textField = new EditText(CurrentActivity.getActivity());
        textField.setTag("textfield");
        textField.setHint(hint);

        return textField;

    }

    public ASApp newApp(final String title, final View[] views){

        ConsoleActivity.getAtomScript().setApp(new ASApp(title, views, CurrentActivity.getActivity()));

        return ConsoleActivity.getAtomScript().getApp();

    }

    public Dialog newDialog(final String title, final View[] views){

        LinearLayout layout = new LinearLayout(CurrentActivity.getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        for(View view : views){

            layout.addView(view);

        }

        final Dialog  dialog = new Dialog(CurrentActivity.getActivity(), android.R.style.Theme_Material_Light_DarkActionBar);
        dialog.setTitle(title);
        dialog.setContentView(layout);
        dialog.setCancelable(true);

        return dialog;

    }

    public void show(final Dialog dialog){

        CurrentActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });

    }

    public void dismiss(final Dialog dialog){

        CurrentActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });

    }

}
