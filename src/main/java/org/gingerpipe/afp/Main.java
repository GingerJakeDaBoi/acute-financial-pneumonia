package org.gingerpipe.afp;

import java.io.IOException;

public class Main {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        while (true) {
            while (!Interpreter.isRunning) {
                Interpreter.run();
            }

        }
    }
}

