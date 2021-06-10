
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;
import java.io.Reader;


public class WordStatInputPrefix {

    public static void main(String[] args) {
        try {
            Path path = Paths.get(args[0]);
            Scan scan = new Scan(new FileReader(args[0], StandardCharsets.UTF_8));
            ArrayList<String> masWord = new ArrayList<>();
            ArrayList<Integer> masInt = new ArrayList<>();
            StringBuilder words;
            try {
                while (scan.hasnextLine()) {
                    words = new StringBuilder(scan.nextLine().toLowerCase());
                    for (int i = 0; i < words.length(); i++) {
                        if (!((Character.isLetter(words.charAt(i))) || (words.charAt(i) == '\'') || (Character.getType(words.charAt(i)) == Character.DASH_PUNCTUATION))) {
                            words.setCharAt(i, ' ');
                        }
                    }
                    Scan sc = new Scan(new StringReader(words.toString()));
                    while (sc.hasNext()) {
                        StringBuilder word = new StringBuilder(sc.next());
                        if (word.length() > 2) {
                            String sword = word.delete(3, word.length()).toString();
                            for (int j = 0; j < masWord.size(); j++) {
                                if (sword.equals(masWord.get(j))) {
                                    masInt.set(j, masInt.get(j) + 1);
                                    sword = null;
                                    break;
                                }
                            }
                            if (sword != null) {
                                masWord.add(sword);
                                masInt.add(1);
                            }
                        }
                    }
                }
            } finally {
                scan.close();
            }
            File outFile = new File(args[1]);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile, true), StandardCharsets.UTF_8));
            try {
                for (int i = 0; i < masWord.size(); i++) {
                    writer.write(masWord.get(i) + " " + masInt.get(i));
                    writer.newLine();
                }

            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.print("I/O error: " + e.getMessage());
        }
    }
}