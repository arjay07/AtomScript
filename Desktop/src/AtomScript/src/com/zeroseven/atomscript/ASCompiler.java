package com.zeroseven.atomscript;

import java.io.*;

public class ASCompiler {

	private String compiledScript;
	private String scriptFilePath;
	private ASEvaluator evaluator;
	
	public ASCompiler(ASEvaluator eval){
		
		evaluator = eval;
		
	}
	
	public void compile(String inFileName){
		
		File input = new File(inFileName);
		String inScript = new ASIO().readFile(input);
		
		scriptFilePath = input.getAbsolutePath().replace(input.getName(), "");
		
		compiledScript = inScript;
		
	}
	
	public void run(){
		
		evaluator.evaluate(compiledScript + "\nif(typeof(main) == \"function\")main();");
		
	}
	
	public void cr(String inFileName){
		
		compile(inFileName);
		run();
		
	}
	
	public void cr(String inFileName, ASEvaluator eval){
	
		compile(inFileName);
		eval.put("_homepath_", "\"" + scriptFilePath.replaceAll("\\\\", "\\\\\\\\") + "\"");
		eval.evaluate(compiledScript + "\nif(typeof(main) == \"function\")main();");
			
	}
	
	public void cr(String inFileName, String[] args, ASEvaluator eval){
		
		StringBuilder sb = new StringBuilder();
		String arguments = "";
		
		for(int i = 0; i < args.length; i++){
			
			String arg = args[i];
			
			sb.append("\"" + arg + "\"");
			if(i < args.length-1)sb.append(", ");
			
		}
		
		arguments = "[" + sb.toString() + "]";
		
		String mainFunc = "\nif(typeof(main) == \"function\")main(" + arguments + ");";
		
		compile(inFileName);
		eval.put("_homepath_", "\"" + scriptFilePath.replaceAll("\\\\", "\\\\\\\\") + "\"");
		eval.evaluate(compiledScript + mainFunc);
			
	}
	
	public String getCompiledScript(){
		
		return compiledScript;
		
	}
	
}
