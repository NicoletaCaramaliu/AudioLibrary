package readFileData;

import java.io.File;
import java.io.IOException;

public class CreateFile {
    public static void createFile(String filename) {
        File file = new File(filename);
        try {
            if (file.createNewFile()) {
                System.out.println("Fișierul " + filename + " a fost creat.");
            } else {
                System.out.println("Fișierul " + filename + " există deja.");
            }
        } catch (IOException e) {
            System.out.println("Eroare la crearea fișierului " + filename + ": " + e.getMessage());
        }
    }
}
