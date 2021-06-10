import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class WordStatLineIndex {

    public static void main(String[] args) {

        Path path = Paths.get(args[0]/*"C:\\Users\\admiN\\Desktop\\IdeaProjects\\MyPrograms\\WordStatPlus\\input.txt"*/);
        try {
            int currentStringNumber = 0;
            Map<String, StringBuilder> wordStatPlaces = new HashMap<>();
            Map<String, Integer> wordStatCount = new LinkedHashMap<>();
            Scan fileScan = new Scan(path, StandardCharsets.UTF_8);
            try {
                while (fileScan.hasnextLine()) {
                    int currentWordNumber = 0;
                    currentStringNumber++;
                    StringBuilder words = new StringBuilder(fileScan.nextLine().toLowerCase());
                    for(int i = 0; i < words.length(); i++) {
                        if (!((Character.isLetter(words.charAt(i)))||(words.charAt(i) == '\'')||(Character.getType(words.charAt(i)) == Character.DASH_PUNCTUATION))) {
                            words.setCharAt(i,' ');
                        }
                    }
                    Scan wordScan = new Scan(new StringReader(words.toString()));
                    while (wordScan.hasNext()) {
                        currentWordNumber++;
                        String word = wordScan.next();
                        if(wordStatCount.containsKey(word)) {
                            wordStatCount.put(word, wordStatCount.get(word) + 1);
                            wordStatPlaces.get(word).append(" ").append(currentStringNumber).append(":").append(currentWordNumber);
                        } else {
                            wordStatCount.put(word, 1);
                            wordStatPlaces.put(word, new StringBuilder(" " + currentStringNumber + ":" + currentWordNumber));
                        }
                    }
                    wordScan.close();
                }
            } finally {
                fileScan.close();
            }
            File outFile = new File(args[1]/*"C:\\Users\\admiN\\Desktop\\IdeaProjects\\MyPrograms\\WordStatPlus\\output.txt"*/);
            outFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile, true), StandardCharsets.UTF_8));
            try {
                for (String s : wordStatCount.keySet()) {
                    writer.write(s + " " + wordStatCount.get(s) + wordStatPlaces.get(s).toString());
                    writer.newLine();
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.print("I/O error " + e.getMessage());
        }
    }
    /*public static void main(String[] args) {
        try {
            Scan scan = new Scan(System.in);
            try {
                String[] mas = new String[1000000];
                int max_i = 0;
                for (int i = 0; scan.hasnextLine(); i++) {
                    String str = scan.nextLine();
                    if(str.equals("end")) {
                        break;
                    } else {
                        Scan sc = new Scan(str);
                        StringBuilder inverseString = new StringBuilder();
                        while (sc.hasNext()) {
                            inverseString.append(new StringBuilder(sc.next()).reverse()).append(" ");
                        }
                        int l = inverseString.length();
                        if(l > 0) {
                            mas[i] = inverseString.substring(0, l - 1);
                        } else {
                            mas[i] = "";
                        }
                        max_i++;
                    }
                }
                for (int i = max_i - 1; i >= 0; i--) {
                    for (int j = mas[i].length() - 1; j >= 0; j--) {
                        System.out.print(mas[i].charAt(j));
                    }
                    System.out.println();
                }
            } finally {
                scan.close();
            }
        } catch (Exception e) {
            System.out.print("I/O error " + e.getMessage());
        }
    }*/
}