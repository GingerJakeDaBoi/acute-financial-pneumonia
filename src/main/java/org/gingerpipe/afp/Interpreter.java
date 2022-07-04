package org.gingerpipe.afp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Interpreter {

    static int waitOutput = 0;
    static boolean isRunning = false;

    public static void run() {
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
                Lang.cat();
            } else if (line.startsWith("newFile")) {
                Lang.newFile();
            } else if (line.startsWith("rm")) {
                Lang.rem();
            } else if (line.startsWith("mkdir")) {
                Lang.creaDir();
            } else if (line.startsWith("edit")) {
                Lang.edit();
            } else if (line.startsWith("wait")) {
                waitOutput = Integer.parseInt(line.substring(5));
                Lang.pause();
            } else {
                Lang.nothing(); //TODO: errors!
            }
        }
        scanner.close();
    }

}
