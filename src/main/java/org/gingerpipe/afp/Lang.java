package org.gingerpipe.afp;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Lang {

    static boolean isRunning = false;

    public static void shell() {
        isRunning = true;

        System.out.print("afp> ");
        Scanner TermIn = new Scanner(System.in);
        String command = TermIn.nextLine();


        switch (command) {
            case "ls" -> ls();
            case "pwd" -> pwd();
            case "exit" -> System.exit(0);
            case "exec" -> exec();
            case "clear", "cls" -> clear();
            case "print" -> print(Interpreter.stringOutput);
            case "cat" -> cat();
            case "newFile" -> newFile();
            case "rm" -> rem();
            case "mkdir" -> creaDir();
            case "edit" -> edit();
            default -> System.out.println("Command not recognized");
        }
    }

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

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public static void exec() {
        Scanner exec = new Scanner(System.in);
        System.out.print("Enter the program you want to execute: ");
        String program = exec.nextLine();

        try {
            Process p = Runtime.getRuntime().exec(program);
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
            p.destroy();
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

        if (exec.equals("")) {
            System.out.println(" ");
            System.out.println("No program specified");
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

    public static void cat() {
        Scanner catInput = new Scanner(System.in);

        System.out.print("Enter the file you want to read: ");
        String file = catInput.nextLine();
        File f = new File(System.getProperty("user.dir") + "/" + file);
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

    public static void newFile() {
        Scanner newFileInput = new Scanner(System.in);
        System.out.print("Enter the file you want to create: ");
        String file = newFileInput.nextLine();
        File f = new File(System.getProperty("user.dir") + "/" + file);

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

    public static void rem() {
        Scanner remInput = new Scanner(System.in);
        System.out.print("Enter the file or directory you want to delete: ");
        String file = remInput.nextLine();
        File f = new File(System.getProperty("user.dir") + "/" + file);
        if (f.exists()) {
            //noinspection ResultOfMethodCallIgnored
            f.delete();
        } else {
            System.out.println(" ");
            System.out.println("File or directory does not exist");
        }
    }

    public static void creaDir() {
        Scanner creaDirInput = new Scanner(System.in);
        System.out.print("Enter the directory you want to create: ");
        String dir = creaDirInput.nextLine();
        File f = new File(System.getProperty("user.dir") + "/" + dir);
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

    public static void edit() {
        Scanner editInput = new Scanner(System.in);
        System.out.print("Enter the file you want to edit: ");
        String file = editInput.nextLine();
        File f = new File(System.getProperty("user.dir") + "/" + file);
        if (f.exists()) {
            System.out.println(" ");
            System.out.print("Enter the text you want to write: ");
            String text = editInput.nextLine();
            try {
                FileWriter fw = new FileWriter(f);
                fw.write(text);
                fw.close();
            } catch (IOException e) {
                System.out.println(" ");
                System.out.println("IOException");
            }
        } else {
            System.out.println(" ");
            System.out.println("File does not exist");
        }
    }
    
    public static void pause(int waitOutput) {
        try {
            Thread.sleep(Interpreter.waitOutput);
        } catch (InterruptedException e) {
            System.out.println(" ");
            System.out.println("InterruptedException");
        }
    }
}




