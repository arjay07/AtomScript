package com.zeroseven.atomscript;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Leandro on 8/13/2016.
 */
public class Console {

    private Activity activity;
    private Console.View consoleView;
    private Thread consoleThread;
    private String in;
    private static CountDownLatch latch;

    public Console(Activity activity){

        this.activity = activity;
        consoleView = new View(this, activity);
        latch = new CountDownLatch(1);
        in = "";

    }

    public void startThread(Runnable runnable){

        consoleThread = new Thread(runnable);
        consoleThread.start();

    }

    public Console.View getView(){

        return consoleView;

    }

    public String in(){

        pause();
        return in;

    }

    public String out(String... strings){

        TextView consoleDisplay = consoleView.getConsoleDisplay();

        StringBuilder stringBuilder = new StringBuilder();

        for(String string : strings){

            stringBuilder.append(string + "\n");

        }

        consoleDisplay.append(stringBuilder.toString());
        consoleView.scrollToBottom();

        return stringBuilder.toString();

    }

    public void pause(){

        try {
            latch = new CountDownLatch(1);
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void resume(){

        latch.countDown();
    }

    public Thread getThread() {
        return consoleThread;
    }

    public static class View{

        private android.view.View layout;
        private TextView consoleDisplay;
        private EditText consoleInput;
        private ScrollView consoleScroll;

        public View(final Console console, Activity activity){

            layout = activity.getLayoutInflater().inflate(R.layout.console, null);

            consoleDisplay = (TextView) layout.findViewById(R.id.consoleDisplay);
            consoleInput = (EditText) layout.findViewById(R.id.consoleInput);
            consoleScroll = (ScrollView) layout.findViewById(R.id.consoleScroll);

            consoleDisplay.setTypeface(Typeface.MONOSPACE);
            consoleInput.setTypeface(Typeface.MONOSPACE);

            consoleDisplay.setMovementMethod(new ScrollingMovementMethod());

            consoleInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                    if(actionId == EditorInfo.IME_ACTION_DONE){

                        consoleDisplay.append(textColor(consoleInput.getText().toString(), "#4db8ff"));
                        consoleDisplay.append("\n");
                        consoleScroll.smoothScrollTo(0, consoleDisplay.getBottom());
                        console.in = consoleInput.getText().toString();
                        consoleInput.setText("");
                        console.resume();

                        return true;

                    }

                    return false;
                }
            });

        }

        public android.view.View get(){

            return layout;

        }

        public TextView getConsoleDisplay(){

            return consoleDisplay;

        }

        public EditText getConsoleInput(){

            return consoleInput;

        }

        public void scrollToBottom(){

            consoleScroll.smoothScrollTo(0, consoleDisplay.getBottom());

        }

        private Spanned textColor(String source, String color){

            String openTag = "<font color='%c'>".replace("%c", color) ;

            return Html.fromHtml(openTag + source.replace("<", "&lt;").replace(">", "&gt;") + "</font>");

        }

    }

}
