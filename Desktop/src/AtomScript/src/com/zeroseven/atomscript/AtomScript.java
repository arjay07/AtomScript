package com.zeroseven.atomscript;

import java.util.Scanner;
import javax.script.ScriptEngine;

public class AtomScript {
	
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
	private String ATOMSCRIPT_VERSION = "Lanthanum";
	private String ATOMSCRIPT_VERSION_NUMBER = "1.0";
	
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
		
	}
	
	public ASEvaluator getEvaluator() {
		return evaluator;
	}
	
	private void init(ASEvaluator eval){
		
		ScriptEngine eng  = eval.getEngine();

		eng.put("AtomScript", this);
		eng.put("as", this);
		eng.put("AS", this);
		eval.put("JavaxSwing", "javax.swing");
		eval.put("JavaAWT", "java.awt");
		eval.put("JavaLang", "java.lang");
		eval.put("Java", "java");
		eval.put("Sound", "com.zeroseven.atomscript.api.Sound");
		eval.put("_homepath_", ("\"" + System.getProperty("user.dir") + "\"").replace("\\", "/"));
		eval.put("_tutorial_", "_homepath_" + "+\"\\tutorial.atom\"".replace("\\", "/"));
		eval.put("_examples_", "_homepath_" + "+\"\\runexamples.atom\"".replace("\\", "/"));
		eval.put("_ide_", "_homepath_" + "+\"\\IDE.atom\"".replace("\\", "/"));
		eval.put("JavaIO", "java.io");
		WELCOME_MESSAGE = "AtomScript v" + ATOMSCRIPT_VERSION_NUMBER + " (" + ATOMSCRIPT_VERSION + ")";
		
		
	}
	
	public void start(){
		
		evaluator = new ASEvaluator();
		init(evaluator);
		
		print(WELCOME_MESSAGE);
		print("(c)ZeroSeven Interactive 2016");
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
		
		ASCompiler compiler = new ASCompiler(new ASEvaluator(script));
		compiler.cr(script, evaluator);
		
	}
	
	public void run(String script, String[] args){
		
		evaluator = new ASEvaluator(script);
		init(evaluator);
		
		ASCompiler compiler = new ASCompiler(new ASEvaluator(script));
		compiler.cr(script, args, evaluator);
		
	}
	
	public void start(String script){
		
		
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
		
	}

}
