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
                case Windows ->
                        Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar \"" + filename + "\""});
                case MacOS, Other -> {
                    new WordGesser();
                    WordGesser.main(null);
                }
                case Linux -> Runtime.getRuntime().exec("xterm" + " java -jar \"" + filename);
            }
        }else{
            WordGesser.main(new String[0]);
            System.out.println("Program has ended, please type 'exit' to close the console");
        }
    }
}