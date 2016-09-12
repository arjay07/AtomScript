package com.zeroseven.atomscript.api;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.FocusManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class GUI {

	public void alert(String message){
		
		Window activeWindow = FocusManager.getCurrentManager().getActiveWindow();
		JOptionPane.showMessageDialog(activeWindow, message, "Alert", JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public boolean confirm(String message){
		
		Window activeWindow = FocusManager.getCurrentManager().getActiveWindow();
		int i = JOptionPane.showConfirmDialog(activeWindow, message, "Confirm", JOptionPane.YES_NO_OPTION);
		
		if(i == 0){
			
			return true;
			
		}else{
			
			return false;
			
		}
		
	}
	
	public String prompt(String message){
		
		Window activeWindow = FocusManager.getCurrentManager().getActiveWindow();
		return JOptionPane.showInputDialog(activeWindow, message, "Prompt", JOptionPane.QUESTION_MESSAGE);
		
	}
	
	public void alert(String message, String title){
		
		Window activeWindow = FocusManager.getCurrentManager().getActiveWindow();
		JOptionPane.showMessageDialog(activeWindow, message, title, JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public boolean confirm(String message, String title){
		
		Window activeWindow = FocusManager.getCurrentManager().getActiveWindow();
		int i = JOptionPane.showConfirmDialog(activeWindow, message, title, JOptionPane.YES_NO_OPTION);
		
		if(i == 0){
			
			return true;
			
		}else{
			
			return false;
			
		}
		
	}
	
	public String prompt(String message, String title){
		
		Window activeWindow = FocusManager.getCurrentManager().getActiveWindow();
		return JOptionPane.showInputDialog(activeWindow, message, title, JOptionPane.QUESTION_MESSAGE);
		
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
		frame.setLocationByPlatform(true);
		
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
		frame.setLocationByPlatform(true);
		
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
		if(font!=null)ta.setFont(font);
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
	
	public JMenuBar newMenuBar(JMenu... menus){
		
		JMenuBar menuBar = new JMenuBar();
		
		for(JMenu menu : menus){
			
			menuBar.add(menu);
			
		}
		
		return menuBar;
		
	}
	
	public JMenu newMenu(String name, JMenuItem... menuItems){
		
		JMenu menu = new JMenu(name);
		
		for(JMenuItem menuItem : menuItems){
			
			menu.add(menuItem);
			
		}
		
		return menu;
		
	}
	
	public JMenu newMenu(String name, JMenu... menus){
		
		JMenu menu = new JMenu(name);
		
		for(JMenu m : menus){
			
			menu.add(m);
			
		}
		
		return menu;
		
	}
	
	public JPopupMenu newPopupMenu(JMenuItem... menuItems){
		
		JPopupMenu menu = new JPopupMenu();
		
		for(JMenuItem menuItem : menuItems){
			
			menu.add(menuItem);
			
		}
		
		return menu;
		
	}
	
	public JPopupMenu newPopupMenu(JMenu... menus){
		
		JPopupMenu menu = new JPopupMenu();
		
		for(JMenu m : menus){
			
			menu.add(m);
			
		}
		
		return menu;
		
	}
	
	public JMenuItem newMenuItem(String name, Function<ActionEvent, ?> method){
		
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				method.apply(e);
				
			}
		});
		
		return menuItem;
		
	}
	
	@SuppressWarnings("serial")
	public JMenuItem newMenuItem(String name, Function<ActionEvent, ?> method, int mnemonickey){
		
		JMenuItem menuItem = new JMenuItem(name);
		Action action = new AbstractAction(name) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				method.apply(e);
				
			}
		};
		
		action.putValue(Action.MNEMONIC_KEY, mnemonickey);
		menuItem.setAction(action);
		
		return menuItem;
		
	}
	
	@SuppressWarnings("serial")
	public JMenuItem newMenuItem(String name, Function<ActionEvent, ?> method, KeyStroke keyStroke){
		
		JMenuItem menuItem = new JMenuItem(name);
		Action action = new AbstractAction(name) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				method.apply(e);
				
			}
		};
		
		action.putValue(Action.ACCELERATOR_KEY, keyStroke);
		menuItem.setAction(action);
		
		return menuItem;
		
	}
	
	@SuppressWarnings("serial")
	public JMenuItem newMenuItem(String name, Function<ActionEvent, ?> method, String keyStroke){
		
		JMenuItem menuItem = new JMenuItem(name);
		Action action = new AbstractAction(name) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				method.apply(e);
				
			}
		};
		
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
		menuItem.setAction(action);
		
		return menuItem;
		
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