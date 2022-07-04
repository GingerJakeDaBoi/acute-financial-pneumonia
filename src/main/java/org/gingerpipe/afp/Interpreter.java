package org.gingerpipe.afp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;


public class Interpreter {

    public static String editContents;
    static boolean isRunning = false;
    public static Path editFile;

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
                Lang.setEdit(line.substring(7));
            } else if (line.startsWith("wait")) {
                Lang.pause(line.substring(5));
            }else if (line.startsWith("write")) {
                editContents = line.substring(6);
                Lang.write(editContents);
            }
            else if (line.startsWith("//") || line.startsWith("#")) {
                Lang.nothing(); //TODO: errors!
            }
        }
        scanner.close();
    }

}
