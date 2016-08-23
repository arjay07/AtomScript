package com.zeroseven.atomscript;

import org.mozilla.javascript.*;

/**
 * Created by Leandro on 8/14/2016.
 */
public class ASEvaluator {

    private Context context;
    private Scriptable scope;
    private String SRC = "";

    public ASEvaluator(){

        context = Context.enter();
        context.setOptimizationLevel(-1);
        scope = context.initStandardObjects();

    }

    public ASEvaluator(String source){

        context = Context.enter();
        context.setOptimizationLevel(-1);
        scope = context.initStandardObjects();
        SRC = source;

    }

    public void setSource(String source){

        SRC = source;

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void evaluate(String code){

        ASParser parser = new ASParser(code, SRC);
        parser.parse();
        String parsedCode = parser.getParsedCode();

        context.evaluateString(scope, parsedCode, "<AtomScript>", 1, null);

    }

    public void put(String key, String value){

        evaluate("var " + key + " = " + value);

    }

    public void put(String key, Object value){

        scope.put(key, scope, value);

    }

    public String getSource() {
        return SRC;
    }
}
