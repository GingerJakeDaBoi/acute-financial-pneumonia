package org.gingerpipe.afp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Interpreter {

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
                Shell.print();
            } else if (line.startsWith("ls")) {
                Shell.ls();
            } else if (line.startsWith("pwd")) {
                Shell.pwd();
            } else if (line.startsWith("exit")) {
                System.exit(0);
            } else if (line.startsWith("exec")) {
                Shell.exec();
            } else if (line.startsWith("clear") || line.startsWith("cls")) {
                Shell.clear();
            } else if (line.startsWith("cat")) {
                Shell.cat();
            } else if (line.startsWith("newFile")) {
                Shell.newFile();
            } else if (line.startsWith("rm")) {
                Shell.rem();
            } else if (line.startsWith("mkdir")) {
                Shell.creaDir();
            } else if (line.startsWith("edit")) {
                Shell.edit();
            } else {
                System.out.println("Command not recognized");
            }

        }
        scanner.close();

        //noinspection InfiniteLoopStatement
        while(true) {
            while(!Shell.isRunning) {
                Shell.run();
            }
            Shell.run();
        }
    }
}