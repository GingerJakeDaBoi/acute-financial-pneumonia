package org.gingerpipe.afp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;


public class Interpreter {

    public static String editContents;
    public static HashMap<String, Boolean> boolVars = new HashMap<>();
    public static HashMap<String, String> stringVars = new HashMap<>();
    public static HashMap<String, Integer> intVars = new HashMap<>();
    public static HashMap<String, Float> floatVars = new HashMap<>();
    static boolean isRunning = false;
    static boolean initVars = false;
    public static Path editFile;

    public static void run() throws IOException {
        isRunning = true;

        //System.out.println("Usage: acute financial pneumonia Interpreter <filename>");
        File file = new File("test.afp");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith("##")) {
                continue;
            }
            if (line.startsWith("Code")) {
                String inputToken = line.substring(line.indexOf("-(") + 2); //TODO Rename this stupid thing
                String methodParams = line.contains("{") && line.contains("}") ? line.substring(line.indexOf("{") + 1, line.indexOf("}")) : null;
                if (line.substring(4).startsWith(".send")) {
                    Lang.print(inputToken);
                } else if (line.substring(4).startsWith(".holdFor")) {
                    double time = Double.parseDouble(inputToken);
                    if(time % 1 > 0) {
                        String[] split = String.valueOf(time * 1000).split("\\.");
                        Lang.pause(Long.parseLong(split[0]), Integer.parseInt(split[1]) * 1000000);
                    } else {
                        Lang.pause((long) time * 1000, 0);
                    }
                } else if (line.substring(4).startsWith(".terminate")) {
                    Lang.print(methodParams);
                    if(methodParams != null) {
                        System.exit(Integer.parseInt(methodParams));
                    } else {
                        //TODO Add better error
                        Lang.print("bad number");
                    }
                } else if (line.substring(4).startsWith(".prepare")) {
                    if (line.indexOf("{") == line.length() - 1) {
                        line = scanner.nextLine();
                        initVars = true;
                        while (initVars) {
                            String varName;
                            String varVal;
                            boolean whiteSpace = line.contains("=") && line.substring(line.indexOf("=")).contains(" ");
                            if (line.contains("bool")) {
                                varName = line.substring(line.indexOf("bool") + 5, line.indexOf("="));
                                if (varName.charAt(varName.length() - 1) == ' ') {
                                    varName = varName.substring(0, varName.length() - 1);
                                }
                                if (whiteSpace) {
                                    int i = 1;
                                    while (line.substring(line.indexOf("=") + i).startsWith(" ")) {
                                        i++;
                                    }
                                    varVal = line.substring(line.indexOf("=") + i);
                                } else {
                                    varVal = line.substring(line.indexOf("=") + 1);
                                }
                                if (!(boolVars.containsKey(varName) || stringVars.containsKey(varName) || intVars.containsKey(varName) || floatVars.containsKey(varName))) {
                                    boolVars.put(varName, Boolean.parseBoolean(varVal));
                                } else {
                                    //TODO Implement errors
                                    Lang.print("repeated variable name");
                                }
                            } else if (line.contains("text")) {
                                varName = line.substring(line.indexOf("text") + 5, line.indexOf("="));
                                if (varName.charAt(varName.length() - 1) == ' ') {
                                    varName = varName.substring(0, varName.length() - 1);
                                }
                                if (whiteSpace) {
                                    int i = 1;
                                    while (line.substring(line.indexOf("=") + i).startsWith(" ")) {
                                        i++;
                                    }
                                    varVal = line.substring(line.indexOf("=") + i);
                                } else {
                                    varVal = line.substring(line.indexOf("=") + 1);
                                }
                                if (!(boolVars.containsKey(varName) || stringVars.containsKey(varName) || intVars.containsKey(varName) || floatVars.containsKey(varName))) {
                                    stringVars.put(varName, varVal);
                                } else {
                                    //TODO Implement errors
                                    Lang.print("repeated variable name");
                                }
                            } else if (line.contains("int")) {
                                varName = line.substring(line.indexOf("int") + 4, line.indexOf("="));
                                if (varName.charAt(varName.length() - 1) == ' ') {
                                    varName = varName.substring(0, varName.length() - 1);
                                }
                                if (whiteSpace) {
                                    int i = 1;
                                    while (line.substring(line.indexOf("=") + i).startsWith(" ")) {
                                        i++;
                                    }
                                    varVal = line.substring(line.indexOf("=") + i);
                                } else {
                                    varVal = line.substring(line.indexOf("=") + 1);
                                }
                                if (!(boolVars.containsKey(varName) || stringVars.containsKey(varName) || intVars.containsKey(varName) || floatVars.containsKey(varName))) {
                                    intVars.put(varName, Integer.parseInt(varVal));
                                } else {
                                    //TODO Implement errors
                                    Lang.print("repeated variable name");
                                }
                            } else if (line.contains("floating")) {
                                varName = line.substring(line.indexOf("floating") + 9, line.indexOf("="));
                                if (varName.charAt(varName.length() - 1) == ' ') {
                                    varName = varName.substring(0, varName.length() - 1);
                                }
                                if (whiteSpace) {
                                    int i = 1;
                                    while (line.substring(line.indexOf("=") + i).startsWith(" ")) {
                                        i++;
                                    }
                                    varVal = line.substring(line.indexOf("=") + i);
                                } else {
                                    varVal = line.substring(line.indexOf("=") + 1);
                                }
                                if (!(boolVars.containsKey(varName) || stringVars.containsKey(varName) || intVars.containsKey(varName) || floatVars.containsKey(varName))) {
                                    floatVars.put(varName, Float.parseFloat(varVal));
                                } else {
                                    //TODO Implement errors
                                    Lang.print("repeated variable name");
                                }
                            }
                            if (line.contains("}")) {
                                initVars = false;
                                break;
                            }
                            line = scanner.nextLine();
                        }
                        Lang.print(boolVars.toString());
                        Lang.print(stringVars.toString());
                        Lang.print(intVars.toString());
                        Lang.print(floatVars.toString());
                    }
                }
            }
            /*//TODO: Implement interpreter working with edit
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
            }*/
        }
        scanner.close();
    }

}
