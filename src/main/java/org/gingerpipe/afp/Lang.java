package org.gingerpipe.afp;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

import static org.gingerpipe.afp.Interpreter.*;

public class Lang {
    public static String currentBoolVar = "";
    public static String currentIntVar = "";
    public static String currentFloatVar = "";
    public static String currentStringVar = "";

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
        Interpreter.editFile = String.valueOf(Path.of(substring));

    }

    public static void write(String editContents) throws IOException {
        //write to file, error if file does not exist
        File f = new File(Interpreter.editFile);
        if (f.exists()) {
            Files.writeString(Path.of(editFile), editContents);
        } else {
            throw new IOException("File does not exist");
        }
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

    public static void boolVar(String substring) {
        boolVars.put(substring, false);
    }

    public static void intVar(String substring) {
        intVars.put(substring, 0);
    }

    public static void floatVar(String substring) {
        floatVars.put(substring, 0.0F);
    }

    public static void stringVar(String substring) {
        stringVars.put(substring, "");
    }

    public static void setBool(String substring) {
        if (boolVars.containsKey(substring)) {
            currentBoolVar = substring;
        } else {
            throw new RuntimeException("Variable does not exist");
        }
    }

    public static void setInt(String substring) {
        if (intVars.containsKey(substring)) {
            currentIntVar = substring;
        } else {
            throw new RuntimeException("Variable does not exist");
        }
    }

    public static void setFloat(String substring) {
        if (floatVars.containsKey(substring)) {
            currentFloatVar = substring;
        } else {
            throw new RuntimeException("Variable does not exist");
        }
    }

    public static void setString(String substring) {
        if (stringVars.containsKey(substring)) {
            currentStringVar = substring;
        } else {
            throw new RuntimeException("Variable does not exist");
        }
    }

    public static void editBool(String substring) {
        if (currentBoolVar != null) {
            boolVars.put(currentBoolVar, Boolean.parseBoolean(substring));
        } else {
            throw new RuntimeException("No variable selected");
        }
    }

    public static void editInt(String substring) {
        if (currentIntVar != null) {
            intVars.put(currentIntVar, Integer.parseInt(substring));
        } else {
            throw new RuntimeException("No variable selected");
        }
    }

    public static void editFloat(String substring) {
        if (currentFloatVar != null) {
            floatVars.put(currentFloatVar, Float.parseFloat(substring));
        } else {
            throw new RuntimeException("No variable selected");
        }
    }

    public static void editString(String substring) {
        if (currentStringVar != null) {
            stringVars.put(currentStringVar, substring);
        } else {
            throw new RuntimeException("No variable selected");
        }
    }
}




