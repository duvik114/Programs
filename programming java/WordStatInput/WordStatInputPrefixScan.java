//package ForCompilation;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;
import java.io.Reader;

class Scan {
    private final Reader in;
    private StringBuilder strBuild = null;
    private int markPosition = 0;
    private StringBuilder word = new StringBuilder();
    private Integer number = null;

    public String nextLine() {
        if(strBuild != null) {
            String s = strBuild.toString();
            strBuild = null;
            return s;
            //System.out.println(s);
        } else {
            if(hasnextLine()) {
                String s = strBuild.toString();
                strBuild = null;
                return s;
            } else { //нахрена????
                return null;
            }
        }
    }
    public boolean hasnextLine() {
        try {
            if(strBuild != null) {
                return true;
            } else {
                int i = in.read();
                if(i != -1) {
                    strBuild = new StringBuilder();
                    while((i != -1)&&((char) i != '\n')) { ///
                        //System.out.println(" fuck ");
                        strBuild.append((char) i);
                        i = in.read();
                    }
                    return true;//красава
                } else {
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.print("I/O error: " + e.getMessage());
            return false;
        }
    }
    public boolean hasNext() { //пиздэц
        if(word.length() != 0) {
            return true;
        } else {
            if(strBuild != null) {
                //StringBuilder str = new StringBuilder();
                if(markPosition == strBuild.length()) {
                    markPosition = 0;
                    strBuild = null;
                    hasnextLine();
                    return hasNext();
                }
                if(Character.isWhitespace(strBuild.charAt(markPosition))) {
                    markPosition++;
                    return hasNext();
                } else {
                    while((markPosition != strBuild.length())&&(!Character.isWhitespace(strBuild.charAt(markPosition)))) {
                        word.append(strBuild.charAt(markPosition));
                        markPosition++;
                    }
                    if(markPosition == strBuild.length()) {
                        markPosition = 0;
                        strBuild = null;
                        //hasnextLine();
                    }
                    return word.length() != 0; //ещё раз красава)
                }
            } else {
                if((hasnextLine())/*&&(strBuild.length() != 0)*/) {    //)
                    return  hasNext(); // ) это можно убрать
                } else {               //)
                    return  false;
                }                      //) и это
            }
        }
    }
    public  String next() {
        if(word.length() != 0) {
            String s = word.toString();
            word = new StringBuilder();
            number = null;
            return  s;
        } else {
            if(hasNext()) {
                return  next();
            } else {
                return null;
            }
        }
    }
    public boolean hasNextInt() {
        if(number != null) {
            return true;
        } else {
            if(word.length() != 0) {
                try {
                    number = Integer.parseInt(word.toString());
                } catch (Exception e) {
                    word = new StringBuilder();
                    return hasNextInt();
                }
                return true;
            } else {
                if(hasNext()) {
                    return hasNextInt();
                } else {
                    return false;
                }
            }
        }
    }
    public Integer nextInt() {
        if(number != null) {
            word = new StringBuilder();
            Integer n = number;
            number = null;
            return n;
        } else {
            if(hasNextInt()) {
                return nextInt();
            } else {
                return null;
            }
        }
    }
    /*public int ReadNext() {
        try {
            return in.read();
        } catch (IOException e) {
            System.out.println("Sorry, you lost");
            return 0;
        }
    }*/
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.out.println("I/O error " + e.getMessage());
        }

    }
    public Scan(Reader in) {
        this.in = in;
        if(!this.hasnextLine()) {
            strBuild = new StringBuilder(" ");
        }
    }
}


public class WordStatInputPrefix {

    public static void main(String[] args) {
        try {
            //BufferedReader bufReader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8));
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
                        } /*else if (word.toString().length() > 2) {
                            String strWord = word.delete(3, word.length()).toString().toLowerCase();
                            word = new StringBuilder();
                            for (int j = 0; j < masWord.size(); j++) {
                                if (strWord.equals(masWord.get(j))) {
                                    masInt.set(j, masInt.get(j) + 1);
                                    strWord = null;
                                    break;
                                }
                            }
                            if (strWord != null) {
                                masWord.add(strWord);
                                masInt.add(1);
                            }
                        } else {
                            word = new StringBuilder();
                        }*/
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
                //bufReader.close();
                scan.close();
            }
            //bufReader.close();
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
            //writer.close();
        } catch (IOException e) {
            System.out.print("I/O error: " + e.getMessage());
        }
    }
}