package com.zeroseven.atomscript.api;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

public class Pointer implements MouseInputListener{

	public int x = 0;
	public int y = 0;
	public int movementCheckerInterval = 500;
	public boolean down = false;
	public boolean up = true;
	public boolean hover = false;
	public boolean exit = false;
	public boolean dragging = false;
	public boolean moving = false;
	public MouseEvent event = null;
	
	private Function<MouseEvent, ?> onClick = null;
	private Function<MouseEvent, ?> onDown = null;
	private Function<MouseEvent, ?> onUp = null;
	private Function<MouseEvent, ?> onHover = null;
	private Function<MouseEvent, ?> onExit = null;
	private Function<MouseEvent, ?> onMove = null;
	private Function<MouseEvent, ?> onDrag = null;
	
	private Timer movementTimer;
	
	public Pointer(JComponent parent){
		
		parent.addMouseListener(this);
		parent.addMouseMotionListener(this);
		
	}
	
	public Pointer(JFrame parent){
		
		parent.addMouseListener(this);
		parent.addMouseMotionListener(this);
		
	}
	
	public Pointer(Component parent){
		
		parent.addMouseListener(this);
		parent.addMouseMotionListener(this);
		
	}
	
	public Pointer(Frame parent){
		
		parent.addMouseListener(this);
		parent.addMouseMotionListener(this);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		event = e;
		if(onClick!=null) onClick.apply(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		event = e;
		down = true;
		up = false;
		
		if(onDown!=null) onDown.apply(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		event = e;
		up = true;
		down = false;
		dragging = false;
		
		if(onUp!=null) onUp.apply(e);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		event = e;
		exit = false;
		hover = true;
		
		if(onHover!=null) onHover.apply(e);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		event = e;
		hover = false;
		exit = true;
		
		if(onExit!=null) onExit.apply(e);
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		event = e;
		x = e.getX();
		y = e.getY();
		dragging = true;
		
		if(onDrag!=null) onDrag.apply(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(movementTimer!=null){
			
			movementTimer.cancel();
			movementTimer = null;
			
		}
		event = e;
		x = e.getX();
		y = e.getY();
		moving = true;
		movementTimer = new Timer();
		movementTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				moving = false;
			}
			
		}, movementCheckerInterval);
		
		if(onMove!=null) onMove.apply(e);
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean getDown(){
		
		return down;
		
	}
	
	public boolean getUp(){
		
		return up;
		
	}
	
	public boolean getHover(){
		
		return hover;
		
	}
	
	public boolean getExit(){
		
		return exit;
		
	}
	
	public boolean getDragging(){
		
		return dragging;
		
	}
	
	public boolean getMoving(){
		
		return moving;
		
	}
	
	public int getMovementCheckerInterval() {
		return movementCheckerInterval;
	}
	
	public MouseEvent getEvent() {
		return event;
	}
	
	public void setMovementCheckerInterval(int movementCheckerInterval) {
		this.movementCheckerInterval = movementCheckerInterval;
	}
	
	public void setOnClick(Function<MouseEvent, ?> onClick) {
		this.onClick = onClick;
	}
	
	public void setOnDown(Function<MouseEvent, ?> onDown) {
		this.onDown = onDown;
	}
	
	public void setOnUp(Function<MouseEvent, ?> onUp) {
		this.onUp = onUp;
	}
	
	public void setOnHover(Function<MouseEvent, ?> onHover) {
		this.onHover = onHover;
	}
	
	public void setOnExit(Function<MouseEvent, ?> onExit) {
		this.onExit = onExit;
	}
	
	public void setOnDrag(Function<MouseEvent, ?> onDrag) {
		this.onDrag = onDrag;
	}
	
	public void setOnMove(Function<MouseEvent, ?> onMove) {
		this.onMove = onMove;
	}

}
