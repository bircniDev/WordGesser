package Gesser;

import java.io.*;
import java.awt.GraphicsEnvironment;
public class Main{
    static OsCheck.OSType ostype = OsCheck.getOperatingSystemType();
    public static void main (String [] args) throws IOException{
        Console console = System.console();
        if(console == null && !GraphicsEnvironment.isHeadless()){
            String filename = Main.class.getProtectionDomain().getCodeSource().getLocation().toString().substring(6);

            switch (ostype) {
                case Windows: Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + filename + "\""}); break;
                case MacOS: Runtime.getRuntime().exec("/bin/bash -c" + " java -jar \"" + filename); break;
                case Linux: Runtime.getRuntime().exec("xterm" + " java -jar \"" + filename); break;
                case Other: break;
            }
        }else{
            WordGesser.main(new String[0]);
            System.out.println("Program has ended, please type 'exit' to close the console");
        }
    }
}