package com.zeroseven.atomscript.api;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	private File SOUND_FILE;
	private long SOUND_LENGTH;
	private float SOUND_VOLUME;
	private Clip CLIP;
	
	public Sound(String path){
		
		SOUND_FILE = new File(path);
		init();
		
	}
	public Sound(File file){
		
		SOUND_FILE = file;
		init();
		
	}
	public Sound(String parent, String name){
		
		SOUND_FILE = new File(parent, name);
		
	}
	
	public Sound(File parent, String name){
		
		SOUND_FILE = new File(parent, name);
		init();
		
	}
	
	private void init(){
		
		try {
			
			CLIP = AudioSystem.getClip();
			CLIP.open(AudioSystem.getAudioInputStream(SOUND_FILE));
			SOUND_LENGTH = CLIP.getMicrosecondLength();
			SOUND_VOLUME = CLIP.getLevel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(LineUnavailableException e) {
			
			e.printStackTrace();
			new GUI().showErrorDialog("AtomScript only supports 16-bit .wav files...", "Sound Error");
			
		} catch(UnsupportedAudioFileException e){
			
			e.printStackTrace();
			new GUI().showErrorDialog("AtomScript only supports .wav files...", "Sound Error");
			
		}
		
	}
	
	public void play(){
		
		CLIP.drain();
		CLIP.start();
		
	}
	
	public void pause(){
		
		CLIP.stop();
		
	}
	
	public void resume(){
		
		CLIP.start();
		
	}
	
	public void stop(){
		
		CLIP.stop();
		CLIP.close();
		
	}
	
	public long getLength(){
		
		return SOUND_LENGTH;
		
	}
	
	public float getVolume(){
		
		return SOUND_VOLUME;
		
	}
	
	public Clip getClip(){
		
		return CLIP;
		
	}
	
	public void changeBitrate(File source,File output){
		
	}

}
