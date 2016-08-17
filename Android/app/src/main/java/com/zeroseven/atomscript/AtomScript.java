package com.zeroseven.atomscript;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;

import com.zeroseven.atomscript.api.GUI;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Leandro on 8/14/2016.
 */
public class AtomScript {

    private Console console;
    private Console.View consoleView;
    private String userInput;
    private Activity activity;
    public ASEvaluator evaluator;
    public boolean DEBUG_MODE = false;
    public boolean ATOMSCRIPT_RUNNING = false;

    public ASIO io;
    public GUI gui;

    private String WELCOME_MESSAGE = "";
    private String ATOMSCRIPT_VERSION = "Lanthanum";
    private String ATOMSCRIPT_VERSION_NUMBER = "1.0";

    public AtomScript(Activity activity){

        this.activity = activity;
        console = new Console(activity);
        consoleView = console.getView();

    }

    public void init(ASEvaluator eval){

        io = new ASIO(activity, console);
        gui = new GUI(activity, console);

        eval.put("AtomScript", this);
        eval.put("as", this);
        eval.put("AS", this);

        eval.put("gui", gui);
        eval.put("GUI", gui);

        eval.put("io", io);
        eval.put("IO", io);

        eval.put("Java", "java");
        eval.put("JavaAWT", "java.awt");
        eval.put("JavaLang", "java.lang");
        eval.put("JavaIO", "java.io");

        eval.put("print", "function(message){ io.out(message); }");

        WELCOME_MESSAGE = "AtomScript v" + ATOMSCRIPT_VERSION_NUMBER + " (" + ATOMSCRIPT_VERSION + ")";

    }

    public void start(){

        ATOMSCRIPT_RUNNING = true;

        evaluator = new ASEvaluator();
        init(evaluator);

        print(WELCOME_MESSAGE);
        print("(c)ZeroSeven Interactive 2016");
        print("Welcome to AtomScript");
        setTitle("AtomScript");
        while(ATOMSCRIPT_RUNNING){

            userInput = console.in();
            try{

                evaluator.evaluate(userInput);

            }catch (RuntimeException e){

                printError(e.getLocalizedMessage());
                showError(e);
                e.printStackTrace();

            }

        }

    }

    public void start(String script){

        Uri data = Uri.fromFile(new File(script));

        Intent intent = new Intent(activity.getBaseContext(), ConsoleActivity.class);
        intent.setData(data);
        intent.setAction(Intent.ACTION_VIEW);
        activity.startActivity(intent);

    }

    public void run(String script){

        evaluator = new ASEvaluator(script);
        init(evaluator);

        ASCompiler compiler = new ASCompiler(evaluator);
        compiler.cr(script);

    }

    public void setTitle(final String title){

        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                activity.setTitle(title);
            }
        });

    }

    public void exit(){

        ATOMSCRIPT_RUNNING = false;
        activity.finish();

    }

    public Console getConsole(){

        return console;

    }

    private void showError(final Exception e){

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Error");
                builder.setMessage("com.zeroseven.atomscript.Error:\n" + e.getLocalizedMessage());
                builder.setIcon(R.drawable.error);
                builder.setNeutralButton("Close", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void printError(final String e){

        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {

                consoleView.getConsoleDisplay().append(textColor( e.toString(), "#ff3333"));
                consoleView.getConsoleDisplay().append("\n");
                consoleView.scrollToBottom();

            }
        });

    }

    private void print(final String message){

        activity.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                console.out(message);
                consoleView.scrollToBottom();
            }
        });

    }

    private Spanned textColor(String source, String color){

        String openTag = "<font color='%c'>".replace("%c", color) ;

        return Html.fromHtml(openTag + source.replace("<", "&lt;").replace(">", "&gt;") + "</font>");

    }
}
