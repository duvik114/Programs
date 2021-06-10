package My programs.ForCompilation;

import java.io.IOException;
import java.io.Reader;

public class Scan {
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