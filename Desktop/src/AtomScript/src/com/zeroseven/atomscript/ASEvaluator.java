package com.zeroseven.atomscript;

import java.io.File;
import java.util.regex.Matcher;

import javax.script.*;
import javax.swing.JOptionPane;

public class ASEvaluator {

	private ScriptEngineManager engineManager;
	private ScriptEngine engine;
	public String SRC = "";
	public boolean showErrorDialog = false;
	private ASParser parser;
	
	public ASEvaluator(){
		
		engineManager = new ScriptEngineManager();
		engine = engineManager.getEngineByName("JavaScript");
		engine.put(ScriptEngine.FILENAME, "<AtomScript>");
		
	}
	
	public ASEvaluator(String source){
		
		engineManager = new ScriptEngineManager();
		engine = engineManager.getEngineByName("JavaScript");
		engine.put(ScriptEngine.FILENAME, "<" + (new File(SRC).exists()?new File(SRC).getName():"AtomScript") + ">");
		SRC = source;
		
	}
	
	public Object evaluate(String code){
		
		parser = new ASParser(code, SRC);
		
		try {
			return engine.eval(parser.parse());
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			
			System.out.println(e.getLocalizedMessage()
					.replaceAll(Matcher.quoteReplacement(ASParser.VAR), "var")
					.replaceAll(Matcher.quoteReplacement(ASParser.FUNCTION), "function")
					.replaceAll(Matcher.quoteReplacement(ASParser.NEW), "new"));
			if(showErrorDialog)JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), (new File(SRC).exists()?"AtomScript Error: " + new File(SRC).getName():"AtomScript Error"), JOptionPane.ERROR_MESSAGE);
		
		}
		
		return null;
		
	}
	
	public void put(String key, String value){
		
		evaluate("@" + key + " = " + value);
		
	}
	
	public void add(String key, String value){
		
		evaluate(key + " = " + value);
		
	}
	
	public ScriptEngine getEngine(){
		return engine;
		
	}
	
	public ASParser getParser() {
		return parser;
	}
	
}
