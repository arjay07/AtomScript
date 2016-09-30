package com.zeroseven.atomscript;

import com.zeroseven.atomscript.api.GUI;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Leandro on 8/15/2016.
 */
public class ASParser {

    private String code;
    public String SRC = "";
    public String SRC_DIR = "";

    public static boolean showErrorDialog = false;

    public ASParser(String scriptcode){

        code = scriptcode;

    }

    public ASParser(String scriptcode, String src){

        code = scriptcode;
        SRC = src;
        SRC_DIR = SRC.substring(0, SRC.length() - new File(SRC).getName().length());

    }

    public void parse() {

        removeComments();
        includeLibs();
        includeFiles();
        convertVariables();
        convertMethods();
        convertObjects();
        convertObjectProperties();
        convertNameSpaceSplitters();
        convertObjectPropertyNameCaller();
        convertObjectPropertyCaller();
        exponents();
        multiplication();
        whenStatement();
        threadStatement();
        removeComments();
        convertEscapes();

    }

    private void removeComments(){

        code = code.replaceAll("\\B\\#[^\\n]+", "");

    }

    private void convertVariables(){

        Pattern pattern = Pattern.compile("\\B@\\w+");
        Matcher matcher = pattern.matcher(code);

        if(matcher.find()){

//			System.out.println(matcher.start());
//			System.out.println(inString(code, matcher.start()));
//			System.out.println(code.substring(matcher.start(), matcher.end()));

            if(inString(code, matcher.start())){

                code = replaceAtIndex(code, matcher.start(), matcher.start()+1, "%e@");
                convertVariables();

            }else{

                code = replaceAtIndex(code, matcher.start(), matcher.start()+1, "var ");
                convertVariables();

            }

        }

    }

    private void convertMethods(){

        Pattern pattern = Pattern.compile("\\B\\$\\w+|\\B\\$\\(");
        Matcher matcher = pattern.matcher(code);

        if(matcher.find()){

            if(inString(code, matcher.start())){

                code = replaceAtIndex(code, matcher.start(), matcher.start()+1, "%e$");
                convertMethods();

            }else{

                code = replaceAtIndex(code, matcher.start(), matcher.start()+1, "function ");
                convertMethods();

            }

        }

    }

    private void convertObjects(){

        Pattern pattern = Pattern.compile("\\B\\*[^0-9;\\s ]+");
        Matcher matcher = pattern.matcher(code);

        if(matcher.find()){

            if(inString(code, matcher.start())){

                code = replaceAtIndex(code, matcher.start(), matcher.start()+1, "%e*");
                convertObjects();

            }else{
                code = replaceAtIndex(code, matcher.start(), matcher.start()+1, "new ");
                convertObjects();
            }

        }

    }

    private void convertObjectProperties(){

        code = code.replaceAll("this(\\s*)->(\\s*)", "this.");

    }

    private void convertNameSpaceSplitters(){

        code = code.replaceAll("::", ".");

    }

    private void convertObjectPropertyCaller(){

        Pattern pattern = Pattern.compile("\\b\\<(.*?)\\>");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            String match = matcher.group();
            String propertyName = match.substring(1, match.length() - 1);

            code = code.replace(match, "." + propertyName);

        }

    }

    private void convertObjectPropertyNameCaller(){

        Pattern pattern = Pattern.compile("\\b\\<(.+?)\\> \\w+|\\<(.+?)\\>\\w+");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            String match = matcher.group();
            String propertyName = match.substring(1, match.indexOf(">"));
            String other = match.split(">")[1];

            code = code.replace(match, "." + propertyName + "." + other);

        }

    }

    private void multiplication(){

        Pattern pattern = Pattern.compile("\\d+\\((.+?)\\)|\\)\\((.+?)\\)");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            String match = matcher.group();
            String multiplier = match.substring(0, match.indexOf("("));
            String expression = match.substring(match.indexOf("(")+1, match.length()-1);

            code = code.replace(match, multiplier + "*(" + expression + ")");

            multiplication();

        }

    }

    private void exponents(){

        Pattern pattern = Pattern.compile("\\d+\\^\\d+|\\((.*?)\\)\\^\\d+");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            String match = matcher.group();
            String exponent = match.split("\\^")[1];
            String expression = match.split("\\^")[0];

            code = code.replace(match, "Math.pow(parseFloat(eval("+expression+")), parseFloat(eval("+exponent+")))");

            exponents();

        }

    }

    private void whenStatement(){

        Pattern pattern = Pattern.compile("when(\\s*)\\((.+?)\\)(\\s*)\\{");
        Matcher matcher = pattern.matcher(code);

        if(matcher.find()){

            try {

                int openBrackets = 0;
                int closeBrackets = 0;

                int i = matcher.start();
                try {
                    while(true){

                        String c = "" + code.charAt(i);
                        if(c.equals("{")){
                            openBrackets = openBrackets+1;
                        }

                        if(c.equals("}")){
                            closeBrackets = closeBrackets+1;
                        }
                        //System.out.println(openBrackets);
                        //System.out.println(closeBrackets);

                        i = i + 1;
                        if((openBrackets == closeBrackets) && (openBrackets|closeBrackets) > 0){
                            break;
                        }

                    }
                } catch (StringIndexOutOfBoundsException e) {
                    // TODO Auto-generated catch block
                    System.out.println("When statement brackets were not closed.");
                }

                int start = matcher.start();
                int end = i;

                String match = code.substring(
                        start,
                        end);
                String declaration = match.substring(0, match.indexOf("{"));
                String head = declaration.substring(declaration.indexOf("(")+1, declaration.lastIndexOf(")"));
                String bool = head.split(";")[0];
                String pauseThread = head.split(";").length>1?head.split(";")[1]:"false";
                String body = match.substring(match.indexOf("{")+1, match.lastIndexOf("}"));

                if(!Boolean.parseBoolean(pauseThread)){

                    code = replaceAtIndex(code, start, end, "new java.lang.Thread(new java.lang.Runnable({ run: function(){ while(true){ if(%bool%){ %body%; break; } } java.lang.Thread.currentThread().interrupt(); } })).start();".replace("%bool%", bool).replace("%body%", body));
                    //code = replaceAtIndex(code, start, end, "12345");
                    whenStatement();

                }else if(Boolean.parseBoolean(pauseThread)){

                    code = replaceAtIndex(code, start, end, "while(true){ if(%bool%){ %body%; break; } }".replace("%bool%", bool).replace("%body%", body));
                    whenStatement();

                }

            } catch (StringIndexOutOfBoundsException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                if(showErrorDialog) new GUI(ConsoleActivity.getConsole()).showErrorDialog(e);
            }

        }

    }

    private void threadStatement(){

        Pattern pattern = Pattern.compile("thread(\\s*)((\\((.+?)\\))*)(\\s*)\\{");
        Matcher matcher = pattern.matcher(code);

        if(matcher.find()){

            try {

                int openBrackets = 0;
                int closeBrackets = 0;

                int i = matcher.start();
                try {
                    while(true){

                        String c = "" + code.charAt(i);
                        if(c.equals("{")){
                            openBrackets = openBrackets+1;
                        }

                        if(c.equals("}")){
                            closeBrackets = closeBrackets+1;
                        }
                        //System.out.println(openBrackets);
                        //System.out.println(closeBrackets);

                        i = i + 1;
                        if((openBrackets == closeBrackets) && (openBrackets|closeBrackets) > 0){
                            break;
                        }

                    }
                } catch (StringIndexOutOfBoundsException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Thread statement brackets were not closed.");
                }

                int start = matcher.start();
                int end = i;

                String match = code.substring(
                        start,
                        end);
                String declaration = match.substring(0, match.indexOf("{"));
                String threadName = "null";
                if(matcher.group().contains("(")&&matcher.group().contains(")")){
                    threadName = declaration.substring(declaration.indexOf("(")+1, declaration.lastIndexOf(")"));
                }
                String body = match.substring(match.indexOf("{")+1, match.lastIndexOf("}"));

                if(threadName.equals("null")){

                    code = replaceAtIndex(code, start, end, "new java.lang.Thread(new java.lang.Runnable({ run: function(){ %body% java.lang.Thread.currentThread().interrupt(); } })).start();".replace("%body%", body).replace("this", "java.lang.Thread.currentThread()"));
                    //code = replaceAtIndex(code, start, end, "12345");
                    threadStatement();

                }else{

                    code = replaceAtIndex(code, start, end, "var %threadname% = new java.lang.Thread(new java.lang.Runnable({ run: function(){ %body% java.lang.Thread.currentThread().interrupt(); } }));".replace("%threadname%", threadName).replace("%body%", body).replace("this", "java.lang.Thread.currentThread()"));
                    threadStatement();

                }

            } catch (StringIndexOutOfBoundsException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                if(showErrorDialog) new GUI(ConsoleActivity.getConsole()).showErrorDialog(e);
            }

        }

    }

    private void convertEscapes(){

        code = code
                .replaceAll("%e@", "@")
                .replaceAll("%e\\$", Matcher.quoteReplacement("$"))
                .replaceAll("%e\\*", "*")
                .replaceAll("%e#", "#");

    }

    private void includeFiles(){

        Pattern pattern = Pattern.compile("include[^;]+");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            String match = matcher.group();

            String includer = match.split(" ")[1];
            String file = SRC_DIR + "/" + includer.substring(1, includer.length()-1);

            if((includer.startsWith("\"") && includer.endsWith("\"")) || (includer.startsWith("'") && includer.endsWith("'"))){

                if(file.endsWith(AtomScript.ATOM)||file.endsWith(AtomScript.ATOMW)){

                    File included = new File(file);
                    String read = "";
                    if(included.exists()){

                        read = new ASIO().readFile(included);

                    }else{

                        if(showErrorDialog)new GUI(ConsoleActivity.getConsole()).showErrorDialog("Module \"" + included.getName() + "\" does not exist...", "Module does not exist...");
                        new ASIO().out("Module \"" + included.getName() + "\" does not exist...");

                    }

                    code = code.replace(match, read);
                    includeFiles();

                }

            }

        }

    }

    private void includeLibs(){

        Pattern pattern = Pattern.compile("include[^;]+");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            String match = matcher.group();

            String includer = match.split(" ")[1];
            String lib = includer.substring(1, includer.length()-1);

            if(includer.startsWith("<") && includer.endsWith(">")){

                AtomScript atomScript = ConsoleActivity.getAtomScript();
                ASEvaluator evaluator = atomScript.getEvaluator();

                ASIO io = new ASIO(atomScript.getConsole());
                GUI gui = new GUI(atomScript.getConsole());


                if(lib.equals("io") || lib.equals("IO")){

                    code = code.replace(match, "");
                    evaluator.put("io", io);
                    evaluator.put("IO", io);

                }else if(lib.equals("gui") || lib.equals("GUI")){

                    code = code.replace(match, "");
                    evaluator.put("gui", gui);
                    evaluator.put("GUI", gui);

                }else if(lib.matches("([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*")){

                    String[] pkgs = lib.split(Pattern.quote("."));
                    String name = pkgs[pkgs.length - 1];

                    try{

                        Class.forName(lib);
                        code = code.replace(match, "var %name% = Java.type(\"%pkg%\");".replace("%name%", name).replace("%pkg%", lib));

                    }catch(ClassNotFoundException e){

                        if(showErrorDialog) new GUI(ConsoleActivity.getConsole()).showErrorDialog("The library \"" + lib + "\" does not exist...", "Library does not exist...");
                        new ASIO().out("The library \"" + lib + "\" does not exist...");

                    }

                }

                includeLibs();

            }

        }

    }

    public String getParsedCode(){

        return code;

    }

    private String replaceAtIndex(String string, int start, int end, String replacement){

        StringBuilder builder = null;
        try {
            builder = new StringBuilder(string);
            builder.delete(start, end);
            builder.insert(start, replacement);
        } catch (StringIndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return builder.toString();

    }

    private boolean inString(String str, int start){

        int quotes = 0;

        try {

            for(int i = 0; i < start; i++){

                String c = Character.toString(str.charAt(i));
                String pc = "";
                if(i>0)pc = Character.toString(str.charAt(i-1));
                if(c.equals("\"") && !pc.equals("\\")){

                    quotes = quotes+1;

                }

            }

        } catch (StringIndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if((quotes%2)==0)
            return false;
        else
            return true;

    }

}
