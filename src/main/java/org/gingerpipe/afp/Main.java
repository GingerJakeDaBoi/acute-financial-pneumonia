package org.gingerpipe.afp;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Interpreter.run();
        while (!Interpreter.isRunning) {
            Interpreter.run();
        }
    }
}

