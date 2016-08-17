package com.zeroseven.atomscript;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zeroseven.atomscript.api.GUI;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ConsoleActivity extends AppCompatActivity {

    Console console;
    Console.View consoleView;
    AtomScript atomScript = null;

    Intent intent;
    String action;
    Uri uri;
    File script;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        intent = getIntent();
        action = intent.getAction();
        uri = intent.getData();

        if(Intent.ACTION_VIEW.equals(action)){

            URL url;
            String path = "";

            try {
                url = new URL(uri.getScheme(), uri.getHost(), uri.getPath());
                path = url.toString().replace("file:", "");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            script = new File(path);
            //Toast.makeText(this, script.getAbsoluteFile().toString(), Toast.LENGTH_LONG).show();

        }

        LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);

        atomScript = new AtomScript(this);

        console = atomScript.getConsole();
        consoleView = console.getView();

        Intent intent = new Intent(this, UiActivity.class);
        startActivity(intent);

        console.startThread(new Runnable() {
            @Override
            public void run() {

                Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        showError(e);
                        printError(e.toString());
                        e.printStackTrace();
                    }}

                );

                Looper.prepare();
                if(script == null)atomScript.start();
                else atomScript.run(script.getAbsoluteFile().toString());

            }
        });

        if (layout != null) {
            layout.addView(consoleView.get());
        }

    }

    private void showError(final Throwable e){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

    private void printError(final String e){

        consoleView.getConsoleDisplay().append(textColor( e.toString(), "#ff3333"));
        consoleView.getConsoleDisplay().append("\n");
        consoleView.scrollToBottom();

    }

    private Spanned textColor(String source, String color){

        String openTag = "<font color='%c'>".replace("%c", color) ;

        return Html.fromHtml(openTag + source.replace("<", "&lt;").replace(">", "&gt;") + "</font>");

    }

}
