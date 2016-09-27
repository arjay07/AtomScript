package com.zeroseven.atomscript;

import java.io.File;
import java.util.Calendar;
import java.util.Scanner;

import javax.script.ScriptEngine;

public class AtomScript {
	
	public static final String ATOM = ".atom";
	public static final String ATOMW = ".atomw";
	public static final String ATOMX = ".atomx";
	public static final String ATX = ".atx";
	
	private Scanner scanner;
	private String userInput;
	public ASEvaluator evaluator;
	public ScriptEngine engine;
	public boolean DEBUG_MODE = false;
	public boolean ATOMSCRIPT_RUNNING = true;
	
	// Colors
	public String BLACK = "0";
	public String BLUE = "1";
	public String GREEN = "2";
	public String AQUA = "3";
	public String RED = "4";
	public String PURPLE = "5";
	public String YELLOW = "6";
	public String WHITE = "7";
	public String GRAY = "8";
	public String LIGHT_BLUE = "9";
	public String LIGHT_GREEN = "A";
	public String LIGHT_AQUA = "B";
	public String LIGHT_RED = "C";
	public String LIGHT_PURPLE = "D";
	public String LIGHT_YELLOW = "E";
	public String BRIGHT_WHITE = "F";
	
	private String WELCOME_MESSAGE = "";
	private static int YEAR;
	public static String ATOMSCRIPT_VERSION = "Lanthanum";
	public static String ATOMSCRIPT_VERSION_NUMBER = "1.0";
	
/*	0 = Black	8 = Gray
	1 = Blue	9 = Light Blue
	2 = Green	A = Light Green
	3 = Aqua	B = Light Aqua
	4 = Red		C = Light Red
	5 = Purple	D = Light Purple
	6 = Yellow	E = Light Yellow
	7 = White	F = Bright White */
	
	public AtomScript(){
		
		scanner = new Scanner(System.in);
		YEAR = Calendar.getInstance().get(Calendar.YEAR);
		
	}
	
	public ASEvaluator getEvaluator() {
		return evaluator;
	}
	
	private void init(ASEvaluator eval){
		
		ScriptEngine eng  = eval.getEngine();

		eng.put("AtomScript", this);
		eng.put("as", this);
		eng.put("AS", this);
		eval.put("_homepath_", ("\"" + System.getProperty("user.dir") + "\"").replace("\\", "/"));
		eval.put("_tutorial_", "_homepath_" + "+\"\\tutorial.atom\"".replace("\\", "/"));
		eval.put("_examples_", "_homepath_" + "+\"\\runexamples.atom\"".replace("\\", "/"));
		eval.put("_ide_", "_homepath_" + "+\"\\IDE.atom\"".replace("\\", "/"));
		eval.put("currentThread", "$(){ return java<lang>Thread<currentThread>(); }");
		
		WELCOME_MESSAGE = "AtomScript v" + ATOMSCRIPT_VERSION_NUMBER + " (" + ATOMSCRIPT_VERSION + ")";
		
	}
	
	public void start(){
		
		evaluator = new ASEvaluator();
		init(evaluator);
		
		print(WELCOME_MESSAGE);
		print("(c)ZeroSeven Interactive " + YEAR);
		print("Welcome to AtomScript");
		setTitle("AtomScript");
		while(ATOMSCRIPT_RUNNING){
			
			userInput = scanner.nextLine();
			evaluator.evaluate(userInput);
			
		}
		
	}
	
	private void print(String message){
		
		System.out.println(message);
		
	}
	
	public void run(String script){
		
		evaluator = new ASEvaluator(script);
		init(evaluator);
		
		if(script.endsWith(ATOM)||script.endsWith(ATOMW)){
			
			ASCompiler compiler = new ASCompiler(new ASEvaluator(script));
			compiler.cr(script, evaluator);
			
		}else if(script.endsWith(ATX)||script.endsWith(ATOMX)){
			
			ASPackage aspackage = new ASPackage(script);
			ASExecutableHandler executableHandler = new ASExecutableHandler(evaluator);
			executableHandler.execute(aspackage);
			
		}
		
	}
	
	public void run(String script, String[] args){
		
		evaluator = new ASEvaluator(script);
		init(evaluator);
		
		if(script.endsWith(ATOM)||script.endsWith(ATOMW)){
			
			ASCompiler compiler = new ASCompiler(new ASEvaluator(script));
			compiler.cr(script, evaluator);
			
		}else if(script.endsWith(ATX)||script.endsWith(ATOMX)){
			
			ASPackage aspackage = new ASPackage(script);
			ASExecutableHandler executableHandler = new ASExecutableHandler(evaluator);
			executableHandler.execute(aspackage);
			
		}
		
	}
	
	public void start(String script){
		
		new ASIO().execute("start \"\" \"" + script + "\"");
		
	}
	
	public void start(String script, String... args){
		
		new ASIO().execute("start \"\" \"" + script + "\"");
		
	}
	
	public void setTitle(String title){
		
		ASIO io = new ASIO();
		io.execute("title " + title);
		
	}
	
	public void setColor(String background, String text){
		
		ASIO io = new ASIO();
		
		io.execute("color " + background + text);
		
	}
	
	public boolean getRunning(){
		
		return ATOMSCRIPT_RUNNING;
		
	}
	
	public void exit(){
		
		ATOMSCRIPT_RUNNING = false;
		
	}
	
	public void setShowErrorDialog(boolean showErrorDialog){
		
		getEvaluator().showErrorDialog = showErrorDialog;
		ASParser.showErrorDialog = getEvaluator().showErrorDialog;
		
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
