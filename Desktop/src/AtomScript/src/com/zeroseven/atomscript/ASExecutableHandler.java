package com.zeroseven.atomscript;

import java.io.File;

import javax.script.ScriptEngine;

public class ASExecutableHandler {
	
	ASEvaluator evaluator;
	
	public ASExecutableHandler(ASEvaluator evaluator) {
		// TODO Auto-generated constructor stub
		this.evaluator = evaluator;
	}
	
	public void execute(ASPackage aspackage){
		
		aspackage.getManifest();
		aspackage.init();
		aspackage.extract();
		
		ScriptEngine engine = evaluator.getEngine();
		engine.put("Package", aspackage);
		
		String script = aspackage.getMainScript().getAbsolutePath();
		
		System.out.println("Hello");
		ASCompiler compiler = new ASCompiler(evaluator);
		compiler.cr(script);
		
	}
	
	public void delete(ASPackage aspackage){
		
		if(aspackage.exists()) aspackage.delete();
		
	}

}
