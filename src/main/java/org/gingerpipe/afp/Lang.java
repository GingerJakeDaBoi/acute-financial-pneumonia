package org.gingerpipe.afp;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

import static org.gingerpipe.afp.Interpreter.editFile;

public class Lang {


//Unused code for a shell, may get working later

//    static boolean isRunning = false;
//
//    public static void shell() {
//        isRunning = true;
//
//        System.out.print("afp> ");
//        Scanner TermIn = new Scanner(System.in);
//        String command = TermIn.nextLine();
//
//
//        switch (command) {
//            case "ls" -> ls();
//            case "pwd" -> pwd();
//            case "exit" -> System.exit(0);
//            case "exec" -> exec();
//            case "clear", "cls" -> clear();
//            case "print" -> print();
//            case "cat" -> cat();
//            case "newFile" -> newFile();
//            case "rm" -> rem();
//            case "mkdir" -> creaDir();
//            case "edit" -> edit();
//            default -> System.out.println("Command not recognized");
//        }
//    }

    public static void ls() {
        File fileRoot = new File(System.getProperty("user.dir"));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileRoot);
        DefaultTreeModel model = new DefaultTreeModel(root);

        File[] subItems = fileRoot.listFiles();
        for (File file : Objects.requireNonNull(subItems)) {
            if (file.isDirectory()) {
                DefaultMutableTreeNode newDir = new DefaultMutableTreeNode(file.getName() + "/");
                root.add(newDir);
                model.reload();
            } else {
                DefaultMutableTreeNode newFile = new DefaultMutableTreeNode(file.getName());
                root.add(newFile);
            }
        }
        System.out.println(" ");
        for (int i = 0; i < root.getChildCount(); i++) {
            System.out.println(root.getChildAt(i).toString());
        }
    }

    public static void pwd() {
        System.out.println(" ");
        System.out.println(System.getProperty("user.dir"));
    }

    public static void exec(String substring) {

        try {
            Process p = Runtime.getRuntime().exec(substring);
            p.waitFor();
            System.out.println(" ");
            Scanner execOut = new Scanner(p.getInputStream());
            while (execOut.hasNextLine()) {
                System.out.println(execOut.nextLine());
            }

            Scanner execErr = new Scanner(p.getErrorStream());
            while (execErr.hasNextLine()) {
                System.out.println(execErr.nextLine());
            }

            p.waitFor();
        } catch (FileNotFoundException e) {
            System.out.println(" ");
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println(" ");
            System.out.println("IOException");
        } catch (InterruptedException e) {
            System.out.println(" ");
            System.out.println("InterruptedException");
        }
    }

    public static void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println(" ");
        }
    }

    public static void print(String substring) {
        System.out.println(substring);
    }

    public static void cat(String substring) {
        File f = new File(System.getProperty("user.dir") + "/" + substring);
        try {
            System.out.println(" ");
            Scanner fileScanner = new Scanner(f);

            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found or is a directory");
        }
    }

    public static void newFile(String substring) {
        File f = new File(System.getProperty("user.dir") + "/" + substring);

        if (f.exists()) {
            System.out.println(" ");
            System.out.println("File already exists");
        } else {
            try {
                //noinspection ResultOfMethodCallIgnored
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(" ");
            System.out.println("File created");
        }
    }

    public static void rem(String substring) {
        File f = new File(System.getProperty("user.dir") + "/" + substring);
        if (f.exists()) {
            //noinspection ResultOfMethodCallIgnored
            f.delete();
        } else {
            System.out.println(" ");
            System.out.println("File or directory does not exist");
        }
    }

    public static void creaDir(String substring) {
        File f = new File(System.getProperty("user.dir") + "/" + substring);
        if (f.exists()) {
            System.out.println(" ");
            System.out.println("Directory already exists");
        } else {
            //noinspection ResultOfMethodCallIgnored
            f.mkdir();
            System.out.println(" ");
            System.out.println("Directory created");
        }
    }

    public static void setEdit(String substring) {
        Path editFile = Path.of(substring);
    }

    public static void write(String editContents) throws IOException {
        //write to file, error if file does not exist
            Files.writeString(editFile, editContents);
        }



    public static void pause(String substring) {
        try {
            Thread.sleep(Long.parseLong(substring));
        } catch (InterruptedException e) {
            System.out.println(" ");
            System.out.println("InterruptedException");
        }
    }

    public static void nothing() {

    }
}




