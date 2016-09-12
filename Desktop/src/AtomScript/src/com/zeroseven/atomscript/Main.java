package com.zeroseven.atomscript;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.zeroseven.atomscript.api.GUI;

public class Main {
	
	private static AtomScript atomScript;
	
	public static void main(String[] args){
		
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

    		@Override
    		public void uncaughtException(Thread t, Throwable e) {
    			new GUI().alert(e.toString(), "Uncaught Exception");
    			e.printStackTrace();
    		}}

    	);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		atomScript = new AtomScript();
		
		if(args.length == 0){
			
			atomScript.start();
			
		}if(args.length == 1){
				
			if(AtomScript.isAtomScriptFile(args[0]))atomScript.run(args[0]);
			else{
				
				try {
					
					ASIO io = new ASIO();
					File temp = File.createTempFile("temp", AtomScript.ATOM);
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
			
			if(AtomScript.isAtomScriptFile(args[0]))atomScript.run(args[0], arguments.toArray(argss));
			else if(args[0].equals("-atomx")){
				
				switch(args[1]){
				
				case "compile":
					
					String input = args[2];
					ASPackage.compile(input);
					
					break;
					
				case "create":
					
					String root = args[2];
					String name = args[3];
					
					ASPackage.createWorkspace(root, name);
					
					break;
				
				}
				
			}else{
				
				StringBuilder scriptbuilder = new StringBuilder();
				
				for(int i = 0; i < args.length; i++){
					
					scriptbuilder.append(args[i]);
					
				}
				
				String script = scriptbuilder.toString();
				
				try {
					ASIO io = new ASIO();
					File temp = File.createTempFile("temp", AtomScript.ATOM);
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

	public static AtomScript getAtomScript() {
		return atomScript;
	}
	
}
