package com.zeroseven.atomscript.api;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.function.Function;

import javax.swing.*;

public class GUI {

	public void alert(String message){
		
		JOptionPane.showMessageDialog(null, message, "Alert", JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public boolean confirm(String message){
		
		int i = JOptionPane.showConfirmDialog(null, message, "Confirm", JOptionPane.YES_NO_OPTION);
		
		if(i == 0){
			
			return true;
			
		}else{
			
			return false;
			
		}
		
	}
	
	public String prompt(String message){
		
		return JOptionPane.showInputDialog(null, message, "Prompt", JOptionPane.QUESTION_MESSAGE);
		
	}
	
	public void alert(String message, String title){
		
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public boolean confirm(String message, String title){
		
		int i = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		
		if(i == 0){
			
			return true;
			
		}else{
			
			return false;
			
		}
		
	}
	
	public String prompt(String message, String title){
		
		return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
		
	}
	
	public JFrame newWindow(String title, int x, int y, JComponent[] components){
		
		JFrame frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(x, y);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		for(JComponent component: components){
			
			component.setAlignmentX(Component.CENTER_ALIGNMENT);
			component.setMaximumSize(component.getPreferredSize());
			frame.getContentPane().add(component);
			
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/atomscript.png")));
		frame.setIconImage(icon.getImage());
		
		return frame;
		
	}
	
	public JFrame newWindow(String title, int x, int y, JComponent[] components, String iconpath){
		
		JFrame frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(x, y);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		for(JComponent component: components){
			
			component.setAlignmentX(Component.CENTER_ALIGNMENT);
			component.setMaximumSize(component.getPreferredSize());
			frame.getContentPane().add(component);
			
		}
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon(iconpath);
		frame.setIconImage(icon.getImage());
		
		return frame;
		
	}
	
	public JButton newButton(String text, Callable<?> method){
		
			JButton btn = new JButton(text);
			btn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						method.call();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}});
		
		return btn;
			
	}
	
	public JButton newImageButton(String imagePath, Callable<?> method){
		
			ImageIcon img = new ImageIcon(imagePath);
		
			JButton btn = new JButton(img);
			btn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						method.call();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}});
		
		return btn;
			
	}
	
	public JButton newImageButton(String imagePath, int width, int height, Callable<?> method){
		
			ImageIcon img = new ImageIcon(imagePath);
			Image scaledImg = getScaledImage(img.getImage(), width, height);
		
			JButton btn = new JButton(new ImageIcon(scaledImg));
			btn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						method.call();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}});
		
		return btn;
			
	}
	
	public JButton newImageButton(String imagePath, String text, Callable<?> method){
		
			ImageIcon img = new ImageIcon(imagePath);
		
			JButton btn = new JButton(text);
			btn.setIcon(img);
			btn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						method.call();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}});
		
		return btn;
			
	}
	
	public JButton newImageButton(String imagePath, String text, int width, int height, Callable<?> method){
		
			ImageIcon img = new ImageIcon(imagePath);
			Image scaledImg = getScaledImage(img.getImage(), width, height);
		
			JButton btn = new JButton(text);
			btn.setIcon(new ImageIcon(scaledImg));
			btn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						method.call();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}});
		
		return btn;
			
	}
	
	public JTextArea newTextArea(int width, int height, Font font, boolean wrapText){
		
		JTextArea ta = new JTextArea();
		ta.setPreferredSize(new Dimension(width, height));
		ta.setFont(font);
		ta.setLineWrap(wrapText);
		
		return ta;
	
	}
	
	public JLabel newLabel(String text, Font font){
		
		JLabel label = new JLabel(text);
		
		if(font!=null)label.setFont(font);
		
		return label;
		
	}
	
	public JLabel newLabel(String text){
		
		JLabel label = new JLabel(text);
		
		return label;
		
	}
	
	public JLabel newImage(String path){
		
		ImageIcon img = new ImageIcon(path);
		
		JLabel label = new JLabel(img);
		
		return label;
		
	}
	
	public JLabel newImage(String path, int width, int height){
		
		ImageIcon img = new ImageIcon(path);
		Image scaledImg = getScaledImage(img.getImage(), width, height);
		
		JLabel label = new JLabel(new ImageIcon(scaledImg));
		
		return label;
		
	}
	
	public KeyListener newKeyListener(Function<KeyEvent, ?> pressed, Function<KeyEvent, ?> released, Function<KeyEvent, ?> typed){
		
		return new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				pressed.apply(e);
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				released.apply(e);
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				typed.apply(e);
				
			}
			
		};
		
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
		
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
}