package com.zeroseven.atomscript;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import com.zeroseven.atomscript.api.GUI;

public class Main {
	
	public static void main(String[] args){
		
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

    		@Override
    		public void uncaughtException(Thread t, Throwable e) {
    			new GUI().alert(e.getLocalizedMessage(), "Fatal Error");
    			e.printStackTrace();
    		}}

    	);
		
		AtomScript atomScript = new AtomScript();
		
		if(args.length == 0){
			
			atomScript.start();
			
		}if(args.length == 1){
				
			if(args[0].endsWith(".atom")||args[0].endsWith(".atom\""))atomScript.run(args[0]);
			else{
				
				try {
					
					ASIO io = new ASIO();
					File temp = File.createTempFile("temp", ".atom");
					io.writeFile(args[0], temp);
					atomScript.run(temp.getAbsolutePath());
					temp.delete();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}else if(args.length > 1){
			
			ArrayList<String> arguments = new ArrayList<String>();
			
			for(int i = 1; i < args.length; i++){
				
				arguments.add(args[i]);
				
			}
			
			String[] argss = new String[arguments.size()];
			
			if(args[0].endsWith(".atom")||args[0].endsWith(".atom\""))atomScript.run(args[0], arguments.toArray(argss));
			else{
				
				StringBuilder scriptbuilder = new StringBuilder();
				
				for(int i = 0; i < args.length; i++){
					
					scriptbuilder.append(args[i]);
					
				}
				
				String script = scriptbuilder.toString();
				
				try {
					ASIO io = new ASIO();
					File temp = File.createTempFile("temp", ".atom");
					io.writeFile(script, temp);
					atomScript.run(temp.getAbsolutePath());
					temp.delete();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}
		
	}

}
