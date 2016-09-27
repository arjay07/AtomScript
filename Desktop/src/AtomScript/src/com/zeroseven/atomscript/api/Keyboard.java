package com.zeroseven.atomscript.api;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Function;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class Keyboard implements KeyListener{

	public KeyEvent event;
	public KeyStroke stroke;
	public String keyString;
	public char keyChar;
	public String modifiersText;
	public int modifiers;
	public String state = "up";
	public int keyCode;
	public boolean down = false; 
	public boolean up = false;
	
	public Function<KeyEvent, ?> keyTyped = null;
	public Function<KeyEvent, ?> keyDown = null;
	public Function<KeyEvent, ?> keyUp = null;
	
	public static final String STATE_UP = "up";
	public static final String STATE_DOWN = "down";
	
	public Keyboard(JComponent parent){
		
		parent.addKeyListener(this);
		
	}
	
	public Keyboard(JFrame parent) {
		
		parent.addKeyListener(this);
		
	}
	
	public Keyboard(Component parent){
		
		parent.addKeyListener(this);
		
	}
	
	public Keyboard(Frame parent) {
		
		parent.addKeyListener(this);
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		event = e;
		stroke = KeyStroke.getKeyStroke(e.getKeyCode(), e.getModifiers());
		keyCode = e.getKeyCode();
		keyString = Character.toString(e.getKeyChar());
		keyChar = e.getKeyChar();
		modifiers = e.getModifiers();
		modifiersText = KeyEvent.getKeyModifiersText(modifiers);
		
		if(keyTyped!=null) keyTyped.apply(e);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		event = e;
		stroke = KeyStroke.getKeyStroke(e.getKeyCode(), e.getModifiers());
		keyCode = e.getKeyCode();
		keyString = Character.toString(e.getKeyChar());
		keyChar = e.getKeyChar();
		modifiers = e.getModifiers();
		modifiersText = KeyEvent.getKeyModifiersText(modifiers);
		down = true;
		up = false;
		state = "down";
		
		if(keyDown!=null) keyDown.apply(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		event = e;
		stroke = KeyStroke.getKeyStroke(e.getKeyCode(), e.getModifiers());
		keyCode = e.getKeyCode();
		keyString = Character.toString(e.getKeyChar());
		keyChar = e.getKeyChar();
		modifiers = e.getModifiers();
		modifiersText = KeyEvent.getKeyModifiersText(modifiers);
		down = false;
		up = true;
		state = "up";
		
		if(keyUp!=null) keyUp.apply(e);
		
	}
	
	public KeyStroke getKeyStroke(){
		return stroke;
	}
	
	public KeyEvent getEvent() {
		return event;
	}
	
	public char getKeyChar() {
		return keyChar;
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	
	public String getKeyString() {
		return keyString;
	}
	
	public int getModifiers() {
		return modifiers;
	}
	
	public String getModifiersText() {
		return modifiersText;
	}
	
	public boolean getDown(){
		return down;
	}
	
	public boolean getUp(){
		return up;
	}
	
	public void setKeyTyped(Function<KeyEvent, ?> keyTyped) {
		this.keyTyped = keyTyped;
	}
	
	public void setKeyDown(Function<KeyEvent, ?> keyDown) {
		this.keyDown = keyDown;
	}
	
	public void setKeyUp(Function<KeyEvent, ?> keyUp) {
		this.keyUp = keyUp;
	}

}
