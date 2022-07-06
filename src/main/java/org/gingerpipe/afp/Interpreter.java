package org.gingerpipe.afp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


public class Interpreter {

    public static String editContents;
    public static HashMap<String, Boolean> boolVars = new HashMap<>();
    public static HashMap<String, String> stringVars = new HashMap<>();
    public static HashMap<String, Integer> intVars = new HashMap<>();
    public static HashMap<String, Float> floatVars = new HashMap<>();

    static boolean isRunning = false;
    public static String editFile;

    public static void run() throws IOException {
        isRunning = true;


        System.out.println("Usage: acute financial pneumonia Interpreter <filename>");
        File file = new File("test.afp");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            //TODO: Implement interpreter working with edit
            if (line.startsWith("print")) {
                Lang.print(line.substring(6));
            } else if (line.startsWith("ls")) {
                Lang.ls();
            } else if (line.startsWith("pwd")) {
                Lang.pwd();
            } else if (line.startsWith("exit")) {
                System.exit(0);
            } else if (line.startsWith("exec ")) {
                Lang.exec(line.substring(5));
            } else if (line.startsWith("clear") || line.startsWith("cls")) {
                Lang.clear();
            } else if (line.startsWith("cat")) {
                Lang.cat(line.substring(4));
            } else if (line.startsWith("newFile")) {
                Lang.newFile(line.substring(8));
            } else if (line.startsWith("rm")) {
                Lang.rem(line.substring(3));
            } else if (line.startsWith("creaDir")) {
                Lang.creaDir(line.substring(7));
            } else if (line.startsWith("setEdit")) {
                Lang.setEdit(line.substring(8));
            } else if (line.startsWith("wait")) {
                Lang.pause(line.substring(5));
            } else if (line.startsWith("write")) {
                editContents = line.substring(6);
                Lang.write(editContents);
            } else if (line.startsWith("//") || line.startsWith("#")) {
                Lang.nothing(); //TODO: errors!
            } else if (line.startsWith("initBool")) {
                Lang.boolVar(line.substring(9));
            } else if (line.startsWith("initInt")) {
                Lang.intVar(line.substring(8));
            } else if (line.startsWith("initFloat")) {
                Lang.floatVar(line.substring(10));
            } else if (line.startsWith("initString")) {
                Lang.stringVar(line.substring(11));
            } else if (line.startsWith("setBool")) {
                Lang.setBool(line.substring(8));
            } else if (line.startsWith("setInt")) {
                Lang.setInt(line.substring(7));
            } else if (line.startsWith("setFloat")) {
                Lang.setFloat(line.substring(9));
            } else if (line.startsWith("setString")) {
                Lang.setString(line.substring(10));
            } else if (line.startsWith("editBool")) {
                Lang.editBool(line.substring(9));
            } else if (line.startsWith("editInt")) {
                Lang.editInt(line.substring(8));
            } else if (line.startsWith("editFloat")) {
                Lang.editFloat(line.substring(10));
            } else if (line.startsWith("editString")) {
                Lang.editString(line.substring(11));
            }
        }


    }

}

