package org.gingerpipe.afp;
import java.util.Scanner;
import org.gingerpipe.afp.Base;

public class Shell {

    public Shell() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("basic > ");
            Base.run(scanner.nextLine());
        }
    }
}
