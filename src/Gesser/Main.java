package Gesser;

import java.io.*;
import java.awt.GraphicsEnvironment;
import java.io.Console;
public class Main{
    static OsCheck.OSType ostype = OsCheck.getOperatingSystemType();
    public static void main (String [] args) throws IOException{
        Console console = System.console();
        if(console == null && !GraphicsEnvironment.isHeadless()){
            String filename = Main.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);

            switch (ostype) {
                case Windows: Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + filename + "\""}); break;
                case MacOS:
                    break;
                case Linux: Runtime.getRuntime().exec("xterm" + " java -jar \"" + filename); break;
                case Other: break;
            }
        }else{
            WordGesser.main(new String[0]);
            System.out.println("Program has ended, please type 'exit' to close the console");
        }
    }

    public static void Macos(String filename) throws IOException {
        //String[] args = new String[] { "java","-jar " + filename};
        //Process proc = new ProcessBuilder(args).start();
        //"/usr/bin/open -a Terminal", "java -jar " + filename + "\""
        //String command = "java -jar "+filename;  "/bin/bash", "-c",
        //Process proc = new ProcessBuilder("-a Terminal").start();
        //Process proc = Runtime.getRuntime().exec(command);
        //System.console();
        Console console = System.console();
        console.printf("java -jar ", filename);
        console.flush();
    }
}