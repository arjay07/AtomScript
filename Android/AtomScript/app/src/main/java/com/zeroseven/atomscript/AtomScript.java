package com.zeroseven.atomscript;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;

import java.io.File;

/**
 * Created by Leandro on 8/14/2016.
 */
public class AtomScript {

    public static final String ATOM = ".atom";
    public static final String ATOMW = ".atomw";
    public static final String ATOMX = ".atomx";
    public static final String ATX = ".atx";

    private Activity activity;
    private ASEvaluator evaluator;

    private Console console;
    private Console.View consoleView;
    private String userInput;
    private boolean showErrorDialog = false;

    public static boolean ATOMSCRIPT_RUNNING = false;

    private static String WELCOME_MESSAGE = "";
    private final static String ATOMSCRIPT_VERSION = "Lanthanum";
    private final static String ATOMSCRIPT_VERSION_NUMBER = "1.0";

    private ASApp app;

    public AtomScript(){

        console = new Console(activity);
        consoleView = console.getView();

    }

    public AtomScript(Activity activity){

        setActivity(activity);
        console = new Console(getActivity());
        consoleView = console.getView();

    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        CurrentActivity.setActivity(getActivity());
    }

    public ASEvaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(ASEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void init(){

        getEvaluator().put("AtomScript", this);
        getEvaluator().put("as", this);
        getEvaluator().put("AS", this);

        getEvaluator().put("Java", "java");
        getEvaluator().put("JavaAWT", "java.awt");
        getEvaluator().put("JavaLang", "java.lang");
        getEvaluator().put("JavaIO", "java.io");

        getEvaluator().put("print", "function(message){ as.print(message); }");

        WELCOME_MESSAGE = "AtomScript v" + ATOMSCRIPT_VERSION_NUMBER + " (" + ATOMSCRIPT_VERSION + ")";

    }

    public void start(){

        setEvaluator(new ASEvaluator());
        init();

        if(!ATOMSCRIPT_RUNNING){

            print(WELCOME_MESSAGE);
            print("(c)ZeroSeven Interactive 2016");
            print("Welcome to AtomScript");
            setTitle("AtomScript");
            ATOMSCRIPT_RUNNING = true;
        }
        while(ATOMSCRIPT_RUNNING){

            userInput = console.in();
            try{

                evaluator.evaluate(userInput);

            }catch (RuntimeException e){

                printError(e.getLocalizedMessage());
                if(showErrorDialog)showError(e);
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

        setEvaluator(new ASEvaluator(script));
        init();

        ASCompiler compiler = new ASCompiler(getEvaluator());
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

    public void setShowErrorDialog(boolean showErrorDialog) {
        this.showErrorDialog = showErrorDialog;
        ASParser.showErrorDialog = showErrorDialog;
    }

    public void setApp(ASApp app) {
        this.app = app;
    }

    public ASApp getApp() {
        return app;
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

    public void print(final String message){

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

    public static boolean isAtomScriptFile(String name){

        if(name.endsWith(AtomScript.ATOM)||name.endsWith(AtomScript.ATOM + "\"")||
                name.endsWith(AtomScript.ATOMW)||name.endsWith(AtomScript.ATOMW + "\"")||
                name.endsWith(AtomScript.ATX)||name.endsWith(AtomScript.ATX + "\"")||
                name.endsWith(AtomScript.ATOMX)||name.endsWith(AtomScript.ATOMX + "\"")) return true;

        return false;

    }

    public static boolean isAtomScriptFile(File file){

        String name = file.getName();

        if(name.endsWith(AtomScript.ATOM)||name.endsWith(AtomScript.ATOM + "\"")||
                name.endsWith(AtomScript.ATOMW)||name.endsWith(AtomScript.ATOMW + "\"")||
                name.endsWith(AtomScript.ATX)||name.endsWith(AtomScript.ATX + "\"")||
                name.endsWith(AtomScript.ATOMX)||name.endsWith(AtomScript.ATOMX + "\"")) return true;

        return false;

    }

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

}
