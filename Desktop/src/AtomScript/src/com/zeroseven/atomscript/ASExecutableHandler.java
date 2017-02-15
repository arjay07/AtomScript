package com.zeroseven.atomscript;

import javax.script.ScriptEngine;

import net.lingala.zip4j.progress.ProgressMonitor;

public class ASExecutableHandler {
	
	ASEvaluator evaluator;
	
	public ASExecutableHandler(ASEvaluator evaluator) {
		// TODO Auto-generated constructor stub
		this.evaluator = evaluator;
	}
	
	public void execute(ASPackage aspackage){
		
		aspackage.getManifest();
		aspackage.extract();
		
		ScriptEngine engine = evaluator.getEngine();
		engine.put("Package", aspackage);
		
		ProgressMonitor packageProgress = aspackage.getZippedPackage().getProgressMonitor();
		
		while(packageProgress.getState() == ProgressMonitor.STATE_BUSY){
			
			System.out.println("\b\b\b\b" + packageProgress.getPercentDone() + "%");
			
		}
		
		if(packageProgress.getState() == ProgressMonitor.STATE_READY){
			
			aspackage.init();
			
			String script = aspackage.getMainScript().getAbsolutePath();
			ASCompiler compiler = new ASCompiler(evaluator);
			compiler.cr(script);
			
		}
		
	}
	
	public void delete(ASPackage aspackage){
		
		if(aspackage.exists()) aspackage.delete();
		
	}

}
