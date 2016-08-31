package com.zeroseven.atomscript;

import android.app.Activity;
import java.io.*;

/**
 * Created by Leandro on 8/14/2016.
 */
public class ASIO {

    Console console;
    Console.View consoleView;

    public ASIO(Console console){

        this.console = console;
        this.consoleView = console.getView();

    }

    public ASIO(){}

    public void clr(){

        CurrentActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                consoleView.getConsoleDisplay().setText("");
            }
        });

    }

    public String in(){

        return console.in();

    }

    public String out(final String message){

        CurrentActivity.getActivity().runOnUiThread(new Runnable(){

            @Override
            public void run() {
                console.out(message);
            }
        });

        return message;

    }

    public String[] out(final String... message){

        CurrentActivity.getActivity().runOnUiThread(new Runnable(){

            @Override
            public void run() {
                console.out(message);
                consoleView.scrollToBottom();
            }
        });

        return message;

    }

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

}
