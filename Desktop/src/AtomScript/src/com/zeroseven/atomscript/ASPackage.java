package com.zeroseven.atomscript;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ASPackage {
	
	private File PACKAGE;
	private File DESTINATION;
	private File MAIN_DIRECTORY;
	private File SCRIPT_DIRECTORY;
	private File IMAGE_DIRECTORY;
	private File SOUND_DIRECTORY;
	private File ASSETS_DIRECTORY;
	private File MAIN_SCRIPT;
	private File MANIFEST;
	
	private ZipFile ZIPPED_PACKAGE;
	
	private String NAME;
	@SuppressWarnings("unused")
	private String VERSION = "Version 1";
	@SuppressWarnings("unused")
	private String VERSION_NUMBER = "1.0";
	@SuppressWarnings("unused")
	private String AUTHOR = "Unknown";
	
	private String SCRIPTS_NAME = "scripts";
	private String IMAGES_NAME = "images";
	private String SOUNDS_NAME = "sounds";
	private String ASSETS_NAME = "assets";
	private String MAIN_SCRIPT_NAME = "main.atom";
	
	public ASPackage(String path) {
		// TODO Auto-generated constructor stub
		PACKAGE = new File(path);
		try {
			ZIPPED_PACKAGE = new ZipFile(PACKAGE);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DESTINATION = new File(System.getProperty("java.io.tmpdir") + File.separator + "AtomScript" + File.separator + "packages");
		if(!DESTINATION.exists())DESTINATION.mkdirs();
	}
	
	public ASPackage(File pkg) {
		// TODO Auto-generated constructor stub
		PACKAGE = pkg;
		try {
			ZIPPED_PACKAGE = new ZipFile(PACKAGE);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DESTINATION = new File(System.getProperty("java.io.tmpdir") + File.separator + "AtomScript" + File.separator + "packages");
		if(!DESTINATION.exists())DESTINATION.mkdirs();
	}
	
	public static void compile(String pkgroot){
		
		try {
			File parent = new File(pkgroot).getParentFile();
			String name = new File(pkgroot).getName() + ".atomx";
			ZipFile zipFile = new ZipFile(new File(parent, name));
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setIncludeRootFolder(false);
			zipFile.addFolder(pkgroot, parameters);
			
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void compile(File pkgroot){
		
		try {
			File parent = pkgroot.getParentFile();
			String name = pkgroot.getName() + ".atomx";
			ZipFile zipFile = new ZipFile(new File(parent, name));
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setIncludeRootFolder(false);
			zipFile.addFolder(pkgroot, parameters);
			
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void createWorkspace(String root, String name){
		
		try {
			
			File rootDir = new File(root);
			File mainDir = new File(root, name);
			File scriptDir = new File(mainDir, "scripts");
			File imagesDir = new File(mainDir, "images");
			File soundsDir = new File(mainDir, "sounds");
			File assetsDir = new File(mainDir, "assets");
			if(!rootDir.exists())rootDir.mkdirs();
			if(!mainDir.exists())mainDir.mkdirs();
			if(!scriptDir.exists())scriptDir.mkdirs();
			if(!imagesDir.exists())imagesDir.mkdirs();
			if(!soundsDir.exists())soundsDir.mkdirs();
			if(!assetsDir.exists())assetsDir.mkdirs();
			
			File mainScript = new File(scriptDir, "main.atom");
			mainScript.createNewFile();
			
			// Create Manifest File
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringElementContentWhitespace(true);
			
			try {
				
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.newDocument();
				
				Element rootElement = doc.createElement("AtomScript");
				rootElement.setAttribute("version", AtomScript.ATOMSCRIPT_VERSION);
				rootElement.setAttribute("version-number", AtomScript.ATOMSCRIPT_VERSION_NUMBER);
				rootElement.setAttribute("author", "ZeroSeven Interactive");
				doc.appendChild(rootElement);
				
				Element appElement = doc.createElement("Application");
				appElement.setAttribute("name", name);
				appElement.setAttribute("version", "Version 1.0");
				appElement.setAttribute("version-number", "1.0");
				rootElement.appendChild(appElement);
				
				Element mainScriptElement = doc.createElement("main");
				mainScriptElement.appendChild(doc.createTextNode("main.atom"));
				appElement.appendChild(mainScriptElement);
				
				Element dirsElement = doc.createElement("Directories");
				appElement.appendChild(dirsElement);
				
				Element scriptDirElement = doc.createElement("scripts");
				scriptDirElement.appendChild(doc.createTextNode("scripts"));
				Element imagesDirElement = doc.createElement("images");
				imagesDirElement.appendChild(doc.createTextNode("images"));
				Element soundsDirElement = doc.createElement("sounds");
				soundsDirElement.appendChild(doc.createTextNode("sounds"));
				Element assetsDirElement = doc.createElement("assets");
				assetsDirElement.appendChild(doc.createTextNode("assets"));
				dirsElement.appendChild(scriptDirElement);
				dirsElement.appendChild(soundsDirElement);
				dirsElement.appendChild(imagesDirElement);
				dirsElement.appendChild(assetsDirElement);
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(mainDir, "Manifest.xml"));
				
				transformer.transform(source, result);
				
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void init(){
		
		MAIN_DIRECTORY = new File(DESTINATION, NAME);
		SCRIPT_DIRECTORY = new File(MAIN_DIRECTORY, SCRIPTS_NAME);
		IMAGE_DIRECTORY = new File(MAIN_DIRECTORY, IMAGES_NAME);
		SOUND_DIRECTORY = new File(MAIN_DIRECTORY, SOUNDS_NAME);
		ASSETS_DIRECTORY = new File(MAIN_DIRECTORY, ASSETS_NAME);
		
		if(!MAIN_DIRECTORY.exists())MAIN_DIRECTORY.mkdirs();
		if(!SCRIPT_DIRECTORY.exists())SCRIPT_DIRECTORY.mkdirs();
		if(!IMAGE_DIRECTORY.exists())IMAGE_DIRECTORY.mkdirs();
		if(!SOUND_DIRECTORY.exists())SOUND_DIRECTORY.mkdirs();
		if(!ASSETS_DIRECTORY.exists())ASSETS_DIRECTORY.mkdirs();
		
		// Creating main.atom/w/x script
		for(File file : SCRIPT_DIRECTORY.listFiles()){
			
			String name = file.getName();
			
			if(name.startsWith(MAIN_SCRIPT_NAME)){
				
				MAIN_SCRIPT = new File(SCRIPT_DIRECTORY, file.getName());
				break;
				
			}
			
		}
		
		MANIFEST.delete();
			
	}
	
	public void extract(){
		
		try {
		
			ZIPPED_PACKAGE.extractAll(MAIN_DIRECTORY.getAbsolutePath());
			
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getManifest(){
		
		try {
			
			ZIPPED_PACKAGE.extractFile("Manifest.xml", DESTINATION.getAbsolutePath());
			MANIFEST = new File(DESTINATION.getAbsolutePath(), "Manifest.xml");
			
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Getting Manifest Data
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringElementContentWhitespace(true);
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new ErrorHandler(){

				@Override
				public void warning(SAXParseException exception) throws SAXException {
					// TODO Auto-generated method stub
					exception.printStackTrace();
				}

				@Override
				public void error(SAXParseException exception) throws SAXException {
					// TODO Auto-generated method stub
					exception.printStackTrace();
				}

				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					// TODO Auto-generated method stub
					exception.printStackTrace();
				}});
			Document doc = builder.parse(MANIFEST);
			Element atomScriptElement = (Element) doc.getElementsByTagName("AtomScript").item(0);
			Element appElement = (Element) atomScriptElement.getElementsByTagName("Application").item(0);
			Element dirsElement = (Element) appElement.getElementsByTagName("Directories").item(0);
			Element scriptElement = (Element) dirsElement.getElementsByTagName("scripts").item(0);
			Element imagesElement = (Element) dirsElement.getElementsByTagName("images").item(0);
			Element soundsElement = (Element) dirsElement.getElementsByTagName("sounds").item(0);
			Element assetsElement = (Element) dirsElement.getElementsByTagName("assets").item(0);
			Element mainScriptElement = (Element) appElement.getElementsByTagName("main").item(0);
			NAME = appElement.getAttribute("name");
			VERSION = appElement.getAttribute("version");
			VERSION_NUMBER = appElement.getAttribute("version-number");
			MAIN_SCRIPT_NAME = mainScriptElement.getTextContent();
			SCRIPTS_NAME = scriptElement.getTextContent();
			IMAGES_NAME = imagesElement.getTextContent();
			SOUNDS_NAME = soundsElement.getTextContent();
			ASSETS_NAME = assetsElement.getTextContent();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean exists(){
		
		return MAIN_DIRECTORY.exists();
		
	}
	
	public void delete(){
		
		deleteDir(MAIN_DIRECTORY);
		
	}
	
	private void deleteDir(File directory){
		
		for(File file : directory.listFiles()){
			
			if(file.isFile()) file.delete();
			else if(file.isDirectory()) deleteDir(file);
			
		}
		
		directory.delete();
		
	}
	
	public File getMainScript(){
		
		return MAIN_SCRIPT;
		
	}
	
	public File getScriptDirectory(){
		
		return SCRIPT_DIRECTORY;
		
	}
	
	public File getImageDirectory(){
		
		return IMAGE_DIRECTORY;
		
	}
	
	public File getSoundDirectory(){
		
		return SOUND_DIRECTORY;
		
	}
	
	public File getAssetsDirectory(){
		
		return ASSETS_DIRECTORY;
		
	}
	
	public File getScript(String name){
		
		return new File(getScriptDirectory(), name);
		
	}
	
	public File getImage(String name){
		
		return new File(getImageDirectory(), name);
		
	}
	
	public File getSound(String name){
		
		return new File(getSoundDirectory(), name);
		
	}
	
	public File getAsset(String name){
		
		return new File(getAssetsDirectory(), name);
		
	}

}
