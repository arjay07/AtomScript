package com.zeroseven.atomscript;

import java.io.File;

import javax.script.*;
import javax.swing.JOptionPane;

public class ASEvaluator {

	private ScriptEngineManager engineManager;
	private ScriptEngine engine;
	public String SRC = "";
	public boolean showErrorDialog = false;
	
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
		
		ASParser parser = new ASParser(code, SRC);
		parser.parse();
		String parsedCode = parser.getParsedCode();
		
		try {
			return engine.eval(parsedCode);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			
			System.out.println(e.getLocalizedMessage());
			if(showErrorDialog)JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), (new File(SRC).exists()?"AtomScript Error: " + new File(SRC).getName():"AtomScript Error"), JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
		
	}
	
	public void put(String key, String value){
		
		evaluate("@" + key + " = " + value);
		
	}
	
	public ScriptEngine getEngine(){
		return engine;
		
	}
	
}
