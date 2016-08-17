package com.zeroseven.atomscript;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class ASIO {

	private Scanner scanner;
	
	public ASIO(){}
	
	public String readFile(File file){
		
		String s = "";
		StringBuilder sb = new StringBuilder();
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((s = br.readLine()) != null){
				
				sb.append(s);
				sb.append("\n");
				
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
		
	}

	public String readFile(String fileName){
	
		File file = new File(fileName);
		
		String s = "";
		StringBuilder sb = new StringBuilder();
		
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((s = br.readLine()) != null){
				
				sb.append(s);
				sb.append("\n");
				
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
		
	}
	
	public void writeFile(String content, File file){
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void writeFile(String content, String fileName){
		
		try {
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public File newFile(String path){
		
		return new File(path);
		
	}
	
	public File newFile(String path, String name){
		
		return new File(path, name);
		
	}
	
	public File openFile(Component parent){
		
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(parent);
		return fc.getSelectedFile();
		
	}
	
	public boolean saveFile(Component parent, String content){
		
		JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(parent);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		File file = fc.getSelectedFile();
		
		writeFile(content, file);
		
		return file.exists();
		
	}
	
	public File[] getFolders(String dir){
		
		File[] dirs = new File(dir).listFiles(new FileFilter(){

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.isDirectory();
			}});
		
		return dirs;
		
	}
	
	public File[] getFiles(String dir){
		
		File[] files = new File(dir).listFiles(new FileFilter(){

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.isFile();
			}});
		
		return files;
		
	}
	
	public String in(){
		
		scanner = new Scanner(System.in);
		String nline = scanner.nextLine();
		return nline;
		
	}
	
	public String out(String output){
		
		System.out.println(output);
		
		return output;
		
	}
	
	public String type(String s){
		
		try {
			
			for (int i = 0; i < s.length(); i++) {
		        char c = s.charAt(i);
		        ASKeyboard keyboard = new ASKeyboard();
		        keyboard.type(c);
		    }
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s;
		
	}
	
	public String type(String s, boolean enter){
		
		try {
			
			if(enter)s = s + "\n";
			
			for (int i = 0; i < s.length(); i++) {
		        char c = s.charAt(i);
		        ASKeyboard keyboard = new ASKeyboard();
		        keyboard.type(c);
		        
		    }
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s;
		
	}
	
	public String type(String s, boolean enter, int delay){
		
		try {
			
			if(enter)s = s+"\n";
			
			for (int i = 0; i < s.length(); i++) {
		        char c = s.charAt(i);
		        ASKeyboard keyboard = new ASKeyboard();
		        Robot robot = keyboard.getRobot();
		        keyboard.type(c);
		        
		        robot.delay(delay);
		        
		    }
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s;
		
	}
	
	public String type(String s, int delay){
		
		try {
			
			for (int i = 0; i < s.length(); i++) {
		        char c = s.charAt(i);
		        ASKeyboard keyboard = new ASKeyboard();
		        Robot robot = keyboard.getRobot();
		        keyboard.type(c);
		        
		        robot.delay(delay);
		    }
			
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s;
		
	}
	
	public void clr(){
		
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void execute(String... commands){
		
		try {
			
			StringBuilder cmdbuilder = new StringBuilder();
			
			for(String command : commands){
				
				cmdbuilder.append(command);
				cmdbuilder.append(" & ");
				
			}
			
			String cmd = cmdbuilder.toString();
			
			new ProcessBuilder("cmd", "/c", cmd).inheritIO().start().waitFor();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
