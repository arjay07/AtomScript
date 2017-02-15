package com.zeroseven.atomscript;

import java.io.File;
import java.util.Random;
import java.util.regex.*;

import javax.script.ScriptEngine;

import com.zeroseven.atomscript.api.GUI;

public class ASParser {
	
	public static boolean showErrorDialog = false;
	
	private String code;
	public String SRC = "";
	public String SRC_DIR = "";
	
	public static String VAR;
	public static String FUNCTION;
	public static String NEW;
	
	public ASParser(String scriptcode){
		
		code = scriptcode;
		
	}
	
	public ASParser(String scriptcode, String src){
		
		code = scriptcode;
		SRC = src;
		SRC_DIR = SRC.substring(0, SRC.length() - new File(SRC).getName().length());
		
	}
	
	public String parse() {
		
		removeComments();
		includeLibs();
		includeFiles();
		avoidKeywords();
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
		
		return code;
		
	}
	
	private void removeComments(){
		
		code = code.replaceAll("\\B\\#(.+)", "");
		
	}
	
	public static void generateKeywordReserves(){
		
		String chars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz123456789_$";
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		
		for(int i = 0; i < 7; i++){
			
			sb.append(chars.charAt(r.nextInt(chars.length())));
			
		}
		
		String random = sb.toString();
		
		VAR = "var_" + random;
		FUNCTION = "function_" + random;
		NEW = "new_" + random;
		
	}
	
	private void convertVariables(){
		
		Pattern pattern = Pattern.compile("\\B@[A-Za-z_][A-Za-z0-9_$]*");
		Matcher matcher = pattern.matcher(code);
		
		if(matcher.find()){
			
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
		
		Pattern pattern = Pattern.compile("\\B\\$[A-Za-z_][A-Za-z0-9_$]*|\\B\\$\\(");
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
		
		Pattern pattern = Pattern.compile("\\B\\*[A-Za-z_][A-Za-z0-9_$]*");
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
		
		Pattern pattern = Pattern.compile("([A-Za-z_][A-Za-z0-9_$]*?)\\<([A-Za-z_][A-Za-z0-9_$]*?)\\>");
		Matcher matcher = pattern.matcher(code);
		
		while(matcher.find()){
			
			String match = matcher.group();
			String object = matcher.group(1);
			String propertyName = matcher.group(2);
			
			if(!inString(code, matcher.start())){
				
				code = code.replace(match, object + "." + propertyName);
				convertObjectPropertyCaller();
				
			}
			
		}
		
	}
	
	private void convertObjectPropertyNameCaller(){
		
		Pattern pattern = Pattern.compile("([A-Za-z_][A-Za-z0-9_$]*?)\\<([A-Za-z_][A-Za-z0-9_$]*?)\\>([A-Za-z_][A-Za-z0-9_$]*?)");
		Matcher matcher = pattern.matcher(code);
		
		while(matcher.find()){
			
			String match = matcher.group();
			String object = matcher.group(1);
			String propertyName = matcher.group(2);
			String other = matcher.group(3);
			
			if(!inString(code, matcher.start())){
				
				code = code.replace(match, object + "." + propertyName + "." + other);
				convertObjectPropertyNameCaller();
				
			}
			
		}
		
	}
	
	private void multiplication(){
		
		Pattern pattern = Pattern.compile("(\\b\\d+\\b|\\(.+?\\))\\((.+?)\\)");
		Matcher matcher = pattern.matcher(code);
		
		if(matcher.find()){
			
			if(!inString(code, matcher.start())){
				
				String match = matcher.group();
				String multiplier = matcher.group(1);
				String expression = matcher.group(2);
				
				code = code.replace(match, multiplier + "*(" + expression + ")");
					
				multiplication();
				
			}
			
		}
		
	}
	
	private void exponents(){
		
		Pattern pattern = Pattern.compile("(\\b\\d+\\b|\\(.+?\\)|[A-Za-z_][A-Za-z0-9_$]*)\\^\\^(\\b\\d+|\\(.+?\\)|([A-Za-z_][A-Za-z0-9_$]*))");
		Matcher matcher = pattern.matcher(code);
		
		while(matcher.find()){
			
			String match = matcher.group();
			String expression = matcher.group(1);
			String exponent = matcher.group(2);
			
			if(!inString(code, matcher.start())){
				
				code = code.replace(match, "Math.pow(parseFloat("+expression+"), parseFloat("+exponent+"))");
				
				exponents();
				
			}
			
		}
		
	}
	
	private void avoidKeywords(){
		
		Pattern pattern = Pattern.compile("\\b(var|function|new)\\b");
		Matcher matcher = pattern.matcher(code);
		
		if(matcher.find()){
			
			String match = matcher.group();
			
			if(!inString(code, matcher.start()))
				switch(match){
				
				case "var":
					code = replaceAtIndex(code, matcher.start(), matcher.end(), VAR);
					avoidKeywords();
					break;
					
				case "function":
					code = replaceAtIndex(code, matcher.start(), matcher.end(), FUNCTION);
					avoidKeywords();
					break;
					
				case "new":
					code = replaceAtIndex(code, matcher.start(), matcher.end(), NEW);
					avoidKeywords();
					break;
				
				}
		}
		
	}
	
	private void whenStatement(){
		
		Pattern pattern = Pattern.compile("\\bwhen(\\s*)\\((.+?)\\)(\\s*)\\{");
		Matcher matcher = pattern.matcher(code);
		
		if(matcher.find()){
			
			if(!inString(code, matcher.start())){
				
				try {
					
					int openBrackets = 0;
					int closeBrackets = 0;
					
					int i = matcher.start();
					try {
						while(true){
							
							String c = "" + code.charAt(i);
							if(!inString(code, i)){
								
								if(c.equals("{")){
									openBrackets = openBrackets+1;
								}
								
								if(c.equals("}")){
									closeBrackets = closeBrackets+1;
								}
								
							}
							
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
					
					if(!inString(code, start) && !Boolean.parseBoolean(pauseThread)){
						
						code = replaceAtIndex(code, start, end, "new java.lang.Thread(new java.lang.Runnable({ run: function(){ while(true){ if(%bool%){ %body%; break; } } java.lang.Thread.currentThread().interrupt(); } })).start();".replace("%bool%", bool).replace("%body%", body));
						
					}else if(!inString(code, start) && Boolean.parseBoolean(pauseThread)){
						
						code = replaceAtIndex(code, start, end, "while(true){ if(%bool%){ %body%; break; } }".replace("%bool%", bool).replace("%body%", body));
						
					}
					
				} catch (StringIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					if(showErrorDialog) new GUI().showErrorDialog(e);
				}
				
			}
			
			whenStatement();
			
		}
		
	}
	
	private void threadStatement(){
		
		Pattern pattern = Pattern.compile("\\bthread(\\s*)((\\((.+?)\\))*)(\\s*)\\{");
		Matcher matcher = pattern.matcher(code);
		
		if(matcher.find()){
			
			if(!inString(code, matcher.start())){
				
				try {
					
					int openBrackets = 0;
					int closeBrackets = 0;
					
					int i = matcher.start();
					try {
						while(true){
							
							String c = "" + code.charAt(i);
							
							if(!inString(code, i)){
								
								if(c.equals("{")){
									openBrackets = openBrackets+1;
								}
								
								if(c.equals("}")){
									closeBrackets = closeBrackets+1;
								}
								
							}
							i = i+1;
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
						
						code = replaceAtIndex(code, start, end, "new java.lang.Thread(new java.lang.Runnable({ run: function(){ %body%; java.lang.Thread.currentThread().interrupt(); } })).start();".replace("%body%", body));
						
					}else if(threadName.equals("null")){
						
						code = replaceAtIndex(code, start, end, "var %threadname% = new java.lang.Thread(new java.lang.Runnable({ run: function(){ %body%; java.lang.Thread.currentThread().interrupt(); } }));".replace("%threadname%", threadName).replace("%body%", body));
						
					}
					
				} catch (StringIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					if(showErrorDialog) new GUI().showErrorDialog(e);
				}
				
			}
			
			threadStatement();
			
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
		
		Pattern pattern = Pattern.compile("\\b(include)\\b\\s\"((.+).atom)\"");
		Matcher matcher = pattern.matcher(code);
		
		while(matcher.find()){
			
			String match = matcher.group();
			String file = SRC_DIR + "/" + matcher.group(2);
			
			AtomScript atomScript = Main.getAtomScript();
			ASEvaluator evaluator = atomScript.getEvaluator();
			
			if(file.endsWith(AtomScript.ATOM)||file.endsWith(AtomScript.ATOMW)){
				 
				File included = new File(file);
				String read = "";
				if(included.exists()){
					
					read = new ASIO().readFile(included);
					code = code.replace(match, "");
					evaluator.evaluate(read);
					includeFiles();
					
				}else{
					
					code = code.replace(match, "");
					if(showErrorDialog)new GUI().showErrorDialog("Module \"" + included.getName() + "\" does not exist...", "Module does not exist...");
					new ASIO().out("Module \"" + included.getName() + "\" does not exist...");
					
				}
				
			}
				
		}
			
	}

	private void includeLibs(){
		
		Pattern pattern = Pattern.compile("\\b(include)\\b\\s\\<(.+)\\>");
		Matcher matcher = pattern.matcher(code);
		
		while(matcher.find()){
			
			String match = matcher.group();
			String lib = matcher.group(2);
				 
			AtomScript atomScript = Main.getAtomScript();
			ASEvaluator evaluator = atomScript.getEvaluator();
			ScriptEngine engine = evaluator.getEngine();
			
			ASIO io = new ASIO();
			GUI gui = new GUI();
			
			if(lib.equals("io") || lib.equals("IO")){
				
				code = code.replace(match, "");
				engine.put("io", io);
				engine.put("IO", io);
				includeLibs();
				
			}else if(lib.equals("gui") || lib.equals("GUI")){
				
				code = code.replace(match, "");
				engine.put("gui", gui);
				engine.put("GUI", gui);
				includeLibs();
				
			}else if(lib.equals("Sound")){
				
				code = code.replace(match, "");
				evaluator.put("Sound", "com.zeroseven.atomscript.api.Sound");
				includeLibs();
				
			}else if(lib.equals("Pointer")){
				
				code = code.replace(match, "");
				evaluator.put("Pointer", "com.zeroseven.atomscript.api.Pointer");
				includeLibs();
				
			}else if(lib.equals("Keyboard")){
				
				code = code.replace(match, "");
				evaluator.put("Keyboard", "com.zeroseven.atomscript.api.Keyboard");
				includeLibs();
				
			}else if(lib.matches("([A-Za-z_$][A-Za-z0-9_$]*\\.)*[A-Za-z_$][A-Za-z0-9_$.*]*")){
				
				String[] pkgs = lib.split(Pattern.quote("."));
				String name = pkgs[pkgs.length - 1];
				
				try{
					
					Class.forName(lib);
					code = code.replace(match, "@%name% = Java.type(\"%pkg%\");".replace("%name%", name).replace("%pkg%", lib));
					
				}catch(ClassNotFoundException e){
					
					if(Package.getPackage(lib)!=null && lib.endsWith("..")||lib.endsWith(".*")){
						
						if(!code.contains("load(\"nashorn:mozilla_compat.js\")")){
							
							code = "load(\"nashorn:mozilla_compat.js\");" + code;
							
						}
						
						lib = lib.substring(0, lib.lastIndexOf("."));
						code = code.replace(match, "importPackage(%lib%)".replace("%lib%", lib));
						includeLibs();
						
					}else{
						
						code = code.replace(match, "");
						if(showErrorDialog)new GUI().showErrorDialog("The library \"" + lib + "\" does not exist...", "Library does not exist...");
						new ASIO().out("The library \"" + lib + "\" does not exist...");
						
					}
					
				}
				
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
