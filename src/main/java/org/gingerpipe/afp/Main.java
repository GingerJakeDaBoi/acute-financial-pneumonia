package org.gingerpipe.afp;

public class Main {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        while (true) {
            while (!Interpreter.isRunning) {
                Interpreter.run();
            }

        }
    }
}

