package com.zeroseven.atomscript;

import javax.script.*;
import javax.swing.JOptionPane;

public class ASEvaluator {

	private ScriptEngineManager engineManager;
	private ScriptEngine engine;
	public String SRC = "";
	
	public ASEvaluator(){
		
		engineManager = new ScriptEngineManager();
		engine = engineManager.getEngineByName("JavaScript");
		
	}
	
	public ASEvaluator(String source){
		
		engineManager = new ScriptEngineManager();
		engine = engineManager.getEngineByName("JavaScript");
		SRC = source;
		
	}
	
	public void evaluate(String code){
		
		ASParser parser = new ASParser(code, SRC);
		parser.parse();
		String parsedCode = parser.getParsedCode();
		
		try {
			engine.eval(parsedCode);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "AtomScript Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void put(String key, String value){
		
		evaluate("@" + key + " = " + value);
		
	}
	
	public ScriptEngine getEngine(){
		return engine;
		
	}
	
}
