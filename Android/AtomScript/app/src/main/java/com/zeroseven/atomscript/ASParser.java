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
        convertObjectPropertyCaller();
        convertObjectPropertyNameCaller();
        removeComments();
        convertEscapes();

    }

    private void removeComments(){

        code = code.replaceAll("\\B\\#[^\\n]+", "");

    }

    private void convertVariables(){

        Pattern pattern = Pattern.compile("\\B@\\w+");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            code = code.replace(matcher.group().substring(0, 1), "var ");

        }

    }

    private void convertMethods(){

        Pattern pattern = Pattern.compile("\\$\\w+|\\$\\(");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            code = code.replace(matcher.group().substring(0, 1), "function ");

        }

    }

    private void convertObjects(){

        Pattern pattern = Pattern.compile("\\*[^0-9;\\s ]+");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            code = code.replace(matcher.group().substring(0, 1), "new ");

        }

    }

    private void convertObjectProperties(){

        code = code.replaceAll("this ->|this->|this-> |this -> ", "this.");

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

    private void convertEscapes(){

        code = code.replaceAll("%evar ", "@").replaceAll("%efunction ", "$").replaceAll("%e#", "#").replaceAll("%enew ", "*");

    }

    private void includeFiles(){

        Pattern pattern = Pattern.compile("include[^;]+");
        Matcher matcher = pattern.matcher(code);

        while(matcher.find()){

            String match = matcher.group();

            String includer = match.split(" ")[1];
            String file = SRC_DIR + "/" + includer.substring(1, includer.length()-1);

            if(file.endsWith(".atom")){

                String read = new ASIO().readFile(new File(file));
                code = code.replace(match, read);

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

                }else{

                    String[] pkgs = lib.split(Pattern.quote("."));
                    String name = pkgs[pkgs.length - 1];

                    code = code.replace(match, "");
                    evaluator.put(name, lib);

                }

            }

        }

    }

    public String getParsedCode(){

        return code;

    }

}